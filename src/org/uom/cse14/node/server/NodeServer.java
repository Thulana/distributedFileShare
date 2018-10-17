package org.uom.cse14.node.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.uom.cse14.node.BasicNode;
import org.uom.cse14.node.Node;

//import org.apache.log4j.Logger;
public class NodeServer implements Runnable {
//	final static Logger logger = Logger.getLogger(Server.class);

    private DatagramSocket serverSocket;
    private byte[] in;
    private byte[] out;
    private Node client;

    /*
	 * Our constructor which instantiates our serverSocket
     */
    public NodeServer(int port, Node client) throws SocketException {
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
                if (command.equals("JOIN")) {
                    join(msg,receivedPacket.getAddress(),receivedPacket.getPort());
                }else if (command.equals("SEARCH")){
                    System.out.println("search");
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

    public void send(InetAddress IPAddress, int port, String data) throws IOException {
        out = data.toUpperCase().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
        serverSocket.send(sendPacket);
    }

    public void join(String msg,InetAddress address,int port ) throws UnknownHostException, IOException {
        BasicNode newClient = new BasicNode(InetAddress.getByName(msg.split(" ")[2]), msg.split(" ")[4], Integer.parseInt(msg.split(" ")[3]));
        this.getClient().addNeighbour(newClient);
        String reply = "JOINOK 0";
        reply = reply.length()+" "+reply;
        this.send(address, port, reply);
        

    }

    public Node getClient() {
        return client;
    }

    public void setClient(Node client) {
        this.client = client;
    }

}
