/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.client;

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

/**
 *
 * @author thulana
 */
public class Client {

    private DatagramSocket socket;
    private InetAddress address;
    private String userName;
    private ArrayList<Client> clientList;
    private byte[] buf;
    private int port;
    private DatagramChannel channel;

    public Client(String userName, int port) throws UnknownHostException, SocketException, IOException {
//        socket = new DatagramSocket();
        this.channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(port));
        address = InetAddress.getByName("localhost");
        clientList = new ArrayList<>();
        this.userName = userName;
    }

    public Client(InetAddress address, String userName, int port) {
        this.address = address;
        this.userName = userName;
        this.port = port;
    }

    public String sendMsg(String msg, InetAddress ip, int port) throws IOException {
        buf = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put(msg.getBytes());
        buffer.flip();
        int bytesSent = channel.send(buffer, new InetSocketAddress(ip, port));
//        DatagramPacket packet
//                = new DatagramPacket(buf, buf.length, ip, port);
        System.out.println("before send");
//        socket.send(packet);
        System.out.println("after send");

//        packet = new DatagramPacket(buf, buf.length);
//
//        socket.receive(packet);
        System.out.println("before receive");
        buffer.clear();
        channel.receive(buffer);
        System.out.println("after receive");
        return buffer.toString();
//        String received = new String(
//                packet.getData(), 0, packet.getLength());
//        return received;
    }
    public int sendMsg1(String msg, InetAddress ip, int port) throws IOException {
        buf = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put(msg.getBytes());
        buffer.flip();
        int bytesSent = channel.send(buffer, new InetSocketAddress(ip, port));
//        DatagramPacket packet
//                = new DatagramPacket(buf, buf.length, ip, port);
        return bytesSent;
//        String received = new String(
//                packet.getData(), 0, packet.getLength());
//        return received;
    }

    public void close() {
        socket.close();
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getUserName() {
        return userName;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setClientList(ArrayList<Client> clientList) {
        this.clientList = clientList;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public void addNeighbour(Client client) {
        clientList.add(client);
    }

    public void removeNeighbour(Client client) {
        clientList.remove(client);
    }

}
