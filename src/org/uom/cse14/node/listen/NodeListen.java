package org.uom.cse14.node.listen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.NeighbourNode;
import org.uom.cse14.node.util.MsgParser;


public class NodeListen implements Runnable {
//	final static Logger logger = Logger.getLogger(Server.class);

    private DatagramSocket serverSocket;
    private BaseNode client;

    /*
     * Our constructor which instantiates our serverSocket
     */
    public NodeListen(int port, BaseNode client) throws SocketException {
        serverSocket = new DatagramSocket(port);
        this.client = client;
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
                        System.out.println("search");
                        search(msg,receivedPacket.getAddress(),receivedPacket.getPort());
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

    private void search(String msg , InetAddress address , int port) throws IOException {
        String fileQuery = msg.split(" ")[4];
        int hops = Integer.parseInt(msg.split(" ")[5]) - 1;
        this.client.search(fileQuery , hops);
        String reply = "SEROK 0"; //added temporary
        reply = reply.length() + reply;
        //client.send(address,port,reply,this.serverSocket);
    }


}