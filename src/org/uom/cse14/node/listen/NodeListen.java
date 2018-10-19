package org.uom.cse14.node.listen;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.uom.cse14.node.BasicNode;
import org.uom.cse14.node.Node;
import org.uom.cse14.node.util.MsgParser;

//import org.apache.log4j.Logger;
public class NodeListen implements Runnable {
//	final static Logger logger = Logger.getLogger(Server.class);

    private DatagramSocket serverSocket;
    private byte[] in;
    private byte[] out;
    private Node client;

    /*
	 * Our constructor which instantiates our serverSocket
     */
    public NodeListen(int port, Node client) throws SocketException {
        serverSocket = new DatagramSocket(port);
        this.client = client;
    }

    public void run() {
        while (true) {
            try {
                in = new byte[1024];
                out = new byte[1024];

                /*
				 * Create our inbound datagram packet
                 */
                DatagramPacket receivedPacket = new DatagramPacket(in, in.length);
                serverSocket.receive(receivedPacket);

                /*
				 * Get the data from the packet we've just received
				 * and transform it to uppercase.
                 */
                String msg = new String(receivedPacket.getData());
                String command = msg.split(" ")[1];
                switch (command) {
                    case "JOIN" :
                        join(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        break;
                    case "DISCOVERY" :
                        discovery(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                        break;
                    case "SER" :
                        System.out.println("search");
                        //search(msg);
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



    private void join(String msg,InetAddress address,int port ) throws UnknownHostException, IOException {
        BasicNode newClient = new BasicNode(InetAddress.getByName(msg.split(" ")[2]), msg.split(" ")[4], Integer.parseInt(msg.split(" ")[3]));
        this.getClient().addNeighbour(newClient);
        String reply = "JOINOK 0";
        reply = reply.length()+" "+reply;
        client.send(address, port, reply, this.serverSocket);
    }

    private void discovery(String msg,InetAddress address,int port) {
       Object[] response = MsgParser.messageParser(msg,"DISCOVERY");
       if ((int)response[0] == 0){
           //update Client List
           System.out.printf("updated clients and added new clients");

       }else{
           System.out.printf("Message error");
       }

    }

    private void search(String msg , InetAddress address , int port) throws IOException {
        String fileQuery = msg.split(" ")[4];
        int hops = Integer.parseInt(msg.split(" ")[5]) - 1;
        this.client.search(fileQuery , hops);
        String reply = "SEROK 0"; //added temporary
        reply = reply.length() + reply;
        client.send(address,port,reply,this.serverSocket);
    }

    public Node getClient() {
        return client;
    }

    public void setClient(Node client) {
        this.client = client;
    }

}
