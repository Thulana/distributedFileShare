/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.uom.cse14.node.util.MsgParser;
import org.uom.cse14.node.util.NetworkConstants;

/**
 *
 * @author thulana
 */
public class BaseNode extends BasicNode {

    private DatagramSocket socket;

    private List fileList;

    private ConcurrentHashMap<String,NeighbourNode> clientList;

    private byte[] buf;
    private DatagramChannel channel;

    public BaseNode(String userName, int port) throws UnknownHostException, SocketException, IOException {
        socket = new DatagramSocket(NetworkConstants.SEND_PORT_OFFSET + port);
        this.port = port;
        this.address = InetAddress.getByName("localhost");
        clientList = new ConcurrentHashMap<>();
        fileList = new ArrayList<String>();
        fileList.add("Adventures of Tintin");
        fileList.add("Jack and Jill Glee");
        fileList.add("The Vampire Diarie");
        fileList.add("King Arthur");    //temporary added here
        fileList.add("Windows XP");
        fileList.add("Harry Potter");
        fileList.add("Kung Fu Panda");
        fileList.add("Lady Gaga");
        fileList.add("Twilight");
        fileList.add("Windows 8");
        fileList.add("Mission Impossible");
        fileList.add("Turn Up The Music");
        fileList.add("Super Mario");
        fileList.add("American Pickers");
        fileList.add("Lord of the rings");
        this.userName = userName;

    }

    public BaseNode(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
    
/**
 *Used to communicate with {@link org.uom.cse14.server.BootstrapServer}
 * */
    public String sendMsg(String msg, InetAddress ip, int port) throws IOException {
        DatagramSocket clientSocket = socket;
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        sendData = msg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String response = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + response);
        return response;
    }

    public void send(InetAddress IPAddress, int port, String data ) throws IOException {
        byte[] out = data.toUpperCase().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
        socket.send(sendPacket);
    }

    public String search(String fileQuery,int hops,InetAddress originatorIp , int originatorPort){
        String fileName;
        String fileNameList = "";
        String originatorHashKey = originatorIp.getHostAddress()+port;

        System.out.println("Searching file......");
        for (Object obj: fileList) {
            fileName =  (String)obj;
           if (fileName.contains(fileQuery)){
               fileNameList = fileNameList + fileName + " ";
               System.out.println(fileName);
            }
        }

        System.out.println("Forward Search to Neighbors");
        if (hops == NetworkConstants.NETWORK_HOPS){
            //forward msg to all the neighbors
            clientList.forEach((neighborKey,neighbor)->{
                try {
                    if (!neighborKey.equals(originatorHashKey)){
                        String msg = originatorIp.getHostAddress() + " " + originatorPort + " " + fileQuery + " " + hops ;
                        msg = MsgParser.sendMessageParser(msg, "SER");
                        System.out.println("send msg " + msg);
                        send(neighbor.getAddress(), neighbor.getPort(),msg);
                    }
                } catch (IOException e) {
                    System.out.println("Error search forward"+ e);
                }
            });
        }else if (hops > 0){
            //forward msg to randomly selected two neighbor nodes
            //chance of bouncing back the search query
        }
    //9999 – failure due to node unreachable
        System.out.println("searching at node " + address.getHostAddress() + " "+ port );
        return fileNameList;
    }

    public void close() {
        socket.close();
    }

    public ConcurrentHashMap<String, NeighbourNode> getClientList() { return clientList; }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public void addNeighbour(NeighbourNode neighbour) {
        String hashKey = neighbour.address.getHostAddress()+Integer.toString(neighbour.port);
        clientList.put(hashKey,neighbour);
    }

    public void removeNeighbour(NeighbourNode neighbour) {
        String hashKey = neighbour.address.getHostAddress()+Integer.toString(neighbour.port);
        clientList.remove(hashKey);
    }
    public void leave(){
        String msg = MsgParser.sendMessageParser(this, "LEAVE");
        System.out.println(msg);


    }

}
