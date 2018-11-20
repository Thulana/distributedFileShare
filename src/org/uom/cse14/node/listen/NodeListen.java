package org.uom.cse14.node.listen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.NeighbourNode;
import org.uom.cse14.node.util.MsgParser;
import org.uom.cse14.node.util.NetworkConstants;


public class NodeListen implements Runnable {
//	final static Logger logger = Logger.getLogger(Server.class);

    private DatagramSocket serverSocket;
    private BaseNode client;
    private ConcurrentHashMap<String,String> searchResults;

    /*
     * Our constructor which instantiates our serverSocket
     */
//    public NodeListen(int port, BaseNode client) throws SocketException {
//        serverSocket = new DatagramSocket(port);
//        this.client = client;
//    }
//
    public NodeListen(int port, BaseNode client, ConcurrentHashMap<String, String> searchResults) throws SocketException {
        this.serverSocket = new DatagramSocket(port);
        this.client = client;
        this.searchResults = searchResults;
    }
    

    public void run() {
        while (true) {
            try {
                byte[]  in = new byte[1024];
                //byte[] out = new byte[1024];

                /*
                 * Create our inbound datagram packet
                 */
                DatagramPacket receivedPacket = new DatagramPacket(in, in.length);
                serverSocket.receive(receivedPacket);

                /*
                 * Get the data from the packet we've just received
                 * and transform it to uppercase.
                 */
                String msg = new String(receivedPacket.getData()).trim();
                String command = msg.split(" ")[1];
                //System.out.println(msg);
                switch (command) {
                    case "JOIN" :
                        join(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        break;
                    case "DISCOVER" :
                        discover(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        break;
                    case "R_DISCOVER" :
                        discoverResponse(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        break;
                    case "SER" :
                        searchResponse(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        break;
                    case "SEROK":
                        System.out.println("comes to the query originator");

                        getSearchResults(msg);

                        //lets begin the download

                        break;
                    case "SER_R":
                        System.out.println("response to search query received");
                        searchResponseHandle(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        //Retry count down or add new request
                        break;

                }
            } catch (IOException e) {
                /*
                 * Handle our servers exception
                 */
//				logger.info();
                System.out.println("Exception thrown: " + e.getLocalizedMessage());
            }

        }
    }
    
    private void getSearchResults(String msg){
        String[] msg_list = msg.trim().split(" ");
        String[] files = Arrays.copyOfRange(msg_list, 6, msg_list.length);
        String ip = msg.split(" ")[3];
        for (String file : files) {
            System.out.println(file);
            searchResults.put(file, ip);
        }
    }


    private void join(String msg,InetAddress address,int port ) throws IOException {
        NeighbourNode newClient = new NeighbourNode( msg.split(" ")[4],InetAddress.getByName(msg.split(" ")[2]), Integer.parseInt(msg.split(" ")[3]));
        client.addNeighbour(newClient);

        //Use MsgParser For this
        String reply = "JOINOK 0";
        reply = reply.length()+" "+reply;
        //Use MsgParser For this
        client.send(address, port, reply);
    }

    private void discover(String msg,InetAddress address,int port) throws IOException {
        Object[] response = MsgParser.receivedMessageParser(msg,"DISCOVER");
        if ((int)response[0] == 0){
            appendNeighbour(client,(InetAddress) response[2],(int)response[3]);
            String discoverResponse = MsgParser.sendMessageParser(this.client,"R_DISCOVER");
            this.client.send((InetAddress) response[2],(int)response[3],discoverResponse);
        }else{
            System.out.printf("Message error");
        }

    }

    private void discoverResponse(String msg,InetAddress address,int port) {
        Object[] response = MsgParser.receivedMessageParser(msg,"R_DISCOVER");
        String myHash = client.getAddress().getHostAddress()+Integer.toString(client.getPort());
        if ((int)response[0] == 0){
            ArrayList<?> addresses = (ArrayList<?>)response[2];
            ArrayList<?> ports = (ArrayList<?>)response[3];
            for (int i =0; i<addresses.size(); i++){
                addNeighbour(client,(InetAddress) addresses.get(i),(Integer)(ports.get(i)),myHash);
            }

        }else{
            System.out.printf("Message error");
        }

    }

    private void appendNeighbour(BaseNode baseNode, InetAddress ipAddress, int port){
        String hashKey = ipAddress.getHostAddress()+Integer.toString(port);
        if(baseNode.getClientList().containsKey(hashKey)){
            baseNode.getClientList().get(hashKey).setRetryCount(0);
        }else{
            NeighbourNode neighbour = new NeighbourNode(ipAddress,port);
            baseNode.addNeighbour(neighbour);
        }

    }

    private void addNeighbour(BaseNode baseNode, InetAddress ipAddress, int port,String myHash){
        String hashKey = ipAddress.getHostAddress()+Integer.toString(port);

        if(!myHash.equals(hashKey)) {
            if (!baseNode.getClientList().containsKey(hashKey)) {
                NeighbourNode neighbour = new NeighbourNode(ipAddress, port);
                baseNode.addNeighbour(neighbour);
            }
        }

    }

    /**
     *sends a response to the search originator after receiving search request
     */
    private  void searchResponse(String msg , InetAddress address , int port) throws IOException {
        System.out.println("piyu");
        String[] searchMessage = msg.split(" ");
        String searchReceived = client.getAddress().getHostAddress() + " " + client.getPort()+" "+searchMessage[4];
        searchReceived = MsgParser.sendMessageParser(searchReceived,"SER_R");
        System.out.println(searchReceived + " send to " + (port - NetworkConstants.SEND_PORT_OFFSET));
        client.send(address,port - NetworkConstants.SEND_PORT_OFFSET,searchReceived);//send reply to the search originator
        this.client.search(searchMessage[4],Integer.parseInt(searchMessage[5]),InetAddress.getByName(searchMessage[2] ),Integer.parseInt(searchMessage[3]),address,port - NetworkConstants.SEND_PORT_OFFSET);
    }

    private void searchResponseHandle(String msg, InetAddress address, int port) {
        String[] searchRMessage = msg.split(" ");
        String portS = Integer.toString(port- NetworkConstants.SEND_PORT_OFFSET);
        String hash = "SR&"+searchRMessage[4]+"&"+address.getHostAddress()+portS;
        client.updateRetryCount(address.getHostAddress()+portS, "decre");
        client.removeTask(hash);       
    }

/**
 * propagate the search request to {@link org.uom.cse14.node.NeighbourNode}
 * send response to query originator
 * */
//    private void search(String msg) throws IOException {
//        String[] searchMessage = msg.split(" ");
//        String fileQuery = searchMessage[4];
//        int hops = Integer.parseInt(searchMessage[5]) - 1;
//        String queriedList = this.client.search(fileQuery,hops,InetAddress.getByName(searchMessage[2] ), Integer.parseInt(searchMessage[3]));
//        System.out.println("Search end at neighbor node");
//        int no_files = 0;
//        int no_hops = NetworkConstants.NETWORK_HOPS - hops;
//
//        //send a response to the query originator when a file is found
//        if (queriedList.length() != 0) {
//            no_files = queriedList.split(" ").length;
//            String response = no_files + " " + client.getAddress().getHostAddress() + " " + client.getPort() + " " + no_hops + " " + queriedList;
//            response = MsgParser.sendMessageParser(response, "SEROK");
//            System.out.println("client response  " + response);
//            client.send(InetAddress.getByName(searchMessage[2]), Integer.parseInt(searchMessage[3]),response );
//        }
//    }

}