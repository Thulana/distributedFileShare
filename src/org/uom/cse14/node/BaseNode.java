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
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.uom.cse14.node.util.MsgParser;

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
        socket = new DatagramSocket();
        this.port = port;
        this.address = InetAddress.getByName("localhost");
        clientList = new ConcurrentHashMap<>();
        fileList = new ArrayList<String>();
        this.userName = userName;

    }

    public BaseNode(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
    

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

    public void search(String fileQuery){
        String fileName;
        String msgtest = "length SER IP port file_name hops";



        for (Object obj: fileList) {
            fileName=  (String)obj;
           if (fileName.contains(fileQuery)){
               System.out.println(fileName);
            }
        }
        //forward msg to clientList
        clientList.forEach((neighborKey,neighbor)->{
            int clientNodePort = neighbor.getPort();
            InetAddress clientNodeAddress = neighbor.getAddress();
            try {
                String msg = "SER " + clientNodeAddress.getHostAddress() + " " + Integer.toString(clientNodePort)
                        + " " + fileQuery + " 3" ;
                send(clientNodeAddress,clientNodePort,msg);
            } catch (IOException e) {
                System.out.println("Error requesting");
            }
        });

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
