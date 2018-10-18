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
import java.util.concurrent.CopyOnWriteArrayList;
import org.uom.cse14.node.util.MsgParser;

/**
 *
 * @author thulana
 */
public class Node extends BasicNode {

    private DatagramSocket socket;
    private CopyOnWriteArrayList clientList;
    private List fileList;

    private byte[] buf;
    private DatagramChannel channel;

    public Node(String userName, int port) throws UnknownHostException, SocketException, IOException {
        socket = new DatagramSocket();
        this.port = port;
        this.address = InetAddress.getByName("localhost");
        clientList = new CopyOnWriteArrayList<BasicNode>();
        fileList = new ArrayList<String>();
        this.userName = userName;

    }

    public Node(InetAddress address, int port) {
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
        Node clientNode;
        String msg = "length SER IP port file_name hops";
        int clientNodePort;
        InetAddress clientNodeAddress;

        for (Object obj: fileList) {
            fileName=  (String)obj;
           if (fileName.contains(fileQuery)){
               System.out.println(fileName);
            }
        }
        //forward msg to clientList
        for(Object neighbor:clientList){
        clientNode = (Node)neighbor;
        clientNodePort = clientNode.getPort();
        clientNodeAddress = clientNode.getAddress();
            try {
                msg = "SER " + clientNodeAddress.getHostAddress() + " " + Integer.toString(clientNodePort)
                        + " " + fileQuery + " 3" ;
                sendMsg("",clientNodeAddress,clientNodePort);
            } catch (IOException e) {
                System.out.println("Error requesting");
            }


        }

    }

    public void close() {
        socket.close();
    }

    public CopyOnWriteArrayList getClientList() { return clientList; }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClientList(CopyOnWriteArrayList clientList) {
        this.clientList = clientList;
    }




    //public void setAddress(InetAddress address) { this.address = address; }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public void addNeighbour(BasicNode client) {
        clientList.add(client);
    }

    public void removeNeighbour(BasicNode client) {
        clientList.remove(client);
    }
    public void leave(){
        String msg = "LEAVE "+this.getAddress().getHostAddress()+" "+this.getPort();
//        msg = MsgParser.formatMsgLength(msg.length()+4)+msg;
        
        
    }
    
}
