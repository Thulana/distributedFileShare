/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentHashMap;
import org.uom.cse14.node.util.MsgParser;
import org.uom.cse14.node.util.NetworkConstants;

/**
 *
 * @author thulana
 */
public class BaseNode extends BasicNode {

    private DatagramSocket socket;

    private String upFilePath;

    private ConcurrentHashMap<String,NeighbourNode> clientList;

    private byte[] buf;
    private DatagramChannel channel;

    public BaseNode(String userName, int port,String upFilePath) throws UnknownHostException, SocketException, IOException {
        socket = new DatagramSocket(NetworkConstants.SEND_PORT_OFFSET + port);
        this.port = port;
        this.address = InetAddress.getByName("localhost");
        this.upFilePath = upFilePath;
        this.userName = userName;
        clientList = new ConcurrentHashMap();
    
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

    public void search(String fileQuery,int hops,InetAddress originatorIp , int originatorPort,InetAddress senderIp , int senderPort) throws IOException{
        String fileName;
        String fileNameList = "";
        String originatorHashKey = originatorIp.getHostAddress()+originatorPort;
        String searchParentHashkey = senderIp.getHostAddress()+senderPort;

        System.out.println("Searching file......");
        boolean isFileThere = false;
        if (hops < NetworkConstants.NETWORK_HOPS){
            File file = new File(this.upFilePath);
            String[] fileList = file.list();
            System.out.println(fileQuery);
            String tmpFileQuery = fileQuery.toLowerCase();
            for (Object obj: fileList) {
                fileName =  (String)obj;
                fileName = fileName.toLowerCase();
                if (fileName.contains(tmpFileQuery)){
                    fileNameList = fileNameList + fileName + " ";
                    System.out.println("Found"+fileName);
                    isFileThere = true;
                    }
            }
            
            if(!isFileThere & hops > 0 ){
            int newHops = hops-1;
            System.out.println("Forward Search to Neighbors");
                //forward msg to all the neighbors

                clientList.forEach((neighborKey,neighbor)->{
                    try {
                        System.out.println(originatorHashKey);
                        if (!neighborKey.equals(originatorHashKey)){
                            if (!neighborKey.equals(searchParentHashkey)){
                                String msg = originatorIp.getHostAddress() + " " + originatorPort + " " + fileQuery + " " + newHops ;
                                msg = MsgParser.sendMessageParser(msg, "SER");
                                System.out.println("send msg " + msg);
                                send(neighbor.getAddress(), neighbor.getPort(),msg);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error search forward"+ e);
                    }
                });
            }
        }
        else if(hops== NetworkConstants.NETWORK_HOPS){
        int newHops = hops-1;
        System.out.println("Forward Search to Neighbors");
            //forward msg to all the neighbors
            
            clientList.forEach((neighborKey,neighbor)->{
                try {
                    System.out.println(originatorHashKey);
                    if (!neighborKey.equals(originatorHashKey)){
                        String msg = originatorIp.getHostAddress() + " " + originatorPort + " " + fileQuery + " " + newHops ;
                        msg = MsgParser.sendMessageParser(msg, "SER");
                        System.out.println("send msg " + msg);
                        send(neighbor.getAddress(), neighbor.getPort(),msg);
                    }
                } catch (IOException e) {
                    System.out.println("Error search forward"+ e);
                }
            });
        }
        if(isFileThere){
            int no_files = fileNameList.split(" ").length;
            String response = no_files + " " + this.address + " " + this.port + " " + hops + " " + fileNameList;
            response = MsgParser.sendMessageParser(response, "SEROK");
            System.out.println("client response  " + response);
            this.send(originatorIp, originatorPort,response );
        }
      
    //9999 – failure due to node unreachable
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
