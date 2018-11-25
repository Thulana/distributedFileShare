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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
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
    private ConcurrentHashMap<String,String> taskList;
    private byte[] buf;
    private DatagramChannel channel;
    private volatile int inCounter;
    private volatile int outCounter;
    
    public BaseNode(String userName, int port,InetAddress nodeIP,String upFilePath) throws UnknownHostException, SocketException, IOException {
        socket = new DatagramSocket(NetworkConstants.SEND_PORT_OFFSET + port);
        this.port = port;
        this.address = nodeIP;
        this.upFilePath = upFilePath;
        this.userName = userName;
        clientList = new ConcurrentHashMap();
        taskList = new ConcurrentHashMap();
        inCounter = 0;
        outCounter = 0;
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
        outIncrement();
        byte[] out = data.getBytes();
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
                if (fileName.toLowerCase().contains(tmpFileQuery)){
                    fileNameList = fileNameList + fileName + " ";
                    System.out.println("Found"+fileName);
                    isFileThere = true;
                    }
            }
            
            if(!isFileThere & hops > 0 ){
            int newHops = hops-1;
            System.out.println("Forward Search to Neighbors");
                //forward msg to all the neighbors
                ArrayList <NeighbourNode> neighborList = new ArrayList();
                clientList.forEach((neighborKey,neighbor)->{
                   if (!neighborKey.equals(originatorHashKey)){
                        if (!neighborKey.equals(searchParentHashkey)){
                             neighborList.add(neighbor);
                           }
                       }
                    
                });
                String msg = originatorIp.getHostAddress() + " " + originatorPort + " " + fileQuery + " " + newHops ;
                msg = MsgParser.sendMessageParser(msg, "SER");
                ArrayList <String> tempName = new ArrayList();
                for (int i = 0; i<2; i++){
                    if (neighborList.size()> 0){
                        NeighbourNode neighbour = neighborList.get(new Random().nextInt(neighborList.size()));
                        System.out.println("send msg " + msg);
                        send(neighbour.getAddress(), neighbour.getPort(),msg);
                        tempName.add("SR&"+ fileQuery+"&"+neighbour.getAddress().toString().substring(1)+neighbour.getPort());
                        neighborList.remove(neighbour);
                    }
                }
                Date nowDate = new Date();  
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                System.out.println(tempName.size());
                for (int i = 0; i<tempName.size(); i++){
                    if (neighborList.size()> 0){
                        NeighbourNode neighbour = neighborList.get(new Random().nextInt(neighborList.size()));
                        addTask(tempName.get(i),formatter.format(nowDate)+"&"+neighbour.getAddress().toString().substring(1)+"&"+neighbour.getPort()+"&"+msg);
                        neighborList.remove(neighbour);
                    }
                   
                    addTask(tempName.get(i),formatter.format(nowDate)+"&None&None&None");
                }
            }
        }
        else if(hops== NetworkConstants.NETWORK_HOPS){
        int newHops = hops-1;
        System.out.println("Forward Search to Neighbors");
            //forward msg to all the neighbors
            Date nowDate = new Date();  
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            clientList.forEach((neighborKey,neighbor)->{
                try {
                    System.out.println(originatorHashKey);
                    if (!neighborKey.equals(originatorHashKey)){
                        String msg = originatorIp.getHostAddress() + " " + originatorPort + " " + fileQuery + " " + newHops ;
                        msg = MsgParser.sendMessageParser(msg, "SER");
                        System.out.println("send msg " + msg);
                        send(neighbor.getAddress(), neighbor.getPort(),msg);
                        addTask("SR&"+ fileQuery+"&"+neighbor.getAddress().toString().substring(1)+neighbor.getPort(),formatter.format(nowDate)+"&None&None&None");
                    }
                } catch (IOException e) {
                    System.out.println("Error search forward"+ e);
                }
            });
        }
        if(isFileThere){
            int no_files = fileNameList.split(" ").length;
            String response = no_files + " " + this.address.getHostAddress() + " " + this.port + " " + hops + " " + fileNameList;
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

    public ConcurrentHashMap<String, String> getTaskList() { return taskList; }
    
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
    public void removeNeighbourByKey(String hashKey) {
        clientList.remove(hashKey);
    }
    
    public void addTask(String id,String task) {
        taskList.put(id,task);
    }

    public void removeTask(String id) {
        taskList.remove(id);
    }
    
    public void leave(){
        String msg = MsgParser.sendMessageParser(this, "LEAVE");
        clientList.forEach((neighborKey,neighbor)->{
            try {
                    
                send(neighbor.getAddress(), neighbor.getPort(),msg);
                    
            } catch (IOException e) {
                System.out.println("Error leave"+ e);
            }
        });
//        System.out.println(msg);
    }
    
    public void updateRetryCount(String hashkey, String type){
        if(clientList.contains(hashkey)){
            int count = clientList.get(hashkey).getRetryCount();
            switch(type){
                case "incre":
                   clientList.get(hashkey).setRetryCount(count+1);
                break;
                case "decre":
                    if (count > 0){
                         clientList.get(hashkey).setRetryCount(count-1);
                    }
                break;
             }
        }
    }

    public synchronized void inIncrement() {
        inCounter++;
    }
    public synchronized void outIncrement() {
        outCounter++;
    }

    public synchronized int getInCounter() {
        return inCounter;
    }

    public synchronized int getOutCounter() {
        return outCounter;
    }
    
    
}
