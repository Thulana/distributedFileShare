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

/**
 *
 * @author thulana
 */
public class Node extends BasicNode {

    private DatagramSocket socket;
    private List clientList;

    private byte[] buf;
    private DatagramChannel channel;

    public Node(String userName, int port) throws UnknownHostException, SocketException, IOException {
        socket = new DatagramSocket();
//        this.channel = DatagramChannel.open();
//        channel.socket().bind(new InetSocketAddress(port));
        this.address = InetAddress.getByName("localhost");
        clientList = Collections.synchronizedList(new ArrayList<BasicNode>());
        this.userName = userName;

    }

    public Node(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
    

//    public String sendMsg(String msg, InetAddress ip, int port) throws IOException {
//        buf = msg.getBytes();
//        ByteBuffer buffer = ByteBuffer.allocate(48);
//        buffer.clear();
//        buffer.put(msg.getBytes());
//        buffer.flip();
//        int bytesSent = channel.send(buffer, new InetSocketAddress(ip, port));
//        buffer.clear();
//        channel.receive(buffer);
//        return buffer.array().;
////        String received = new String(
////                packet.getData(), 0, packet.getLength());
////        return received;
//    }

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

    public void send(InetAddress IPAddress, int port, String data ,DatagramSocket sendSocket) throws IOException {
        byte[] out = data.toUpperCase().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
        sendSocket.send(sendPacket);
    }

    public void close() {
        socket.close();
    }

    public List getClientList() { return clientList; }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClientList(ArrayList<Node> clientList) {
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

}
