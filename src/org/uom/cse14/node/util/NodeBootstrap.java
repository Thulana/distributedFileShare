/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import java.io.IOException;
import java.net.InetAddress;

import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.NeighbourNode;


/**
 *
 * @author thulana
 */
public class NodeBootstrap {
    private BaseNode client;
    private InetAddress BootstrapAddr;
    private int BootstrapPort;

    public NodeBootstrap(BaseNode client) {
        this.client = client;
    }

    public NodeBootstrap(BaseNode client, InetAddress BootstrapAddr) {
        this.client = client;
        this.BootstrapAddr = BootstrapAddr;
    }

    public NodeBootstrap(BaseNode client, InetAddress BootstrapAddr, int BootstrapPort) {
        this.client = client;
        this.BootstrapAddr = BootstrapAddr;
        this.BootstrapPort = BootstrapPort;
    }
    
    
    public String registerClient(String address,int port) throws IOException{
        String msg = "REG "+address+" "+port+" "+getClient().getUserName();
        msg = msg.length()+" "+msg;
        String response = getClient().sendMsg(msg, getBootstrapAddr(), getBootstrapPort());
        String[] commandList = response.split(" ");
        System.out.println(response);
        int responsetype = Integer.parseInt(response.split(" ")[2].trim());
        if (responsetype == 9998){
//            System.out.println("bootstrap says same user");
            return "bootstrap says same user";
        }else if(responsetype == 0){
//            System.out.println("bootstrap says no user");
            return "bootstrap says no user";
        }else if(responsetype == 1){
            NeighbourNode client = new NeighbourNode(InetAddress.getByName(commandList[commandList.length-2].trim()), Integer.parseInt(commandList[commandList.length-1].trim()));
            this.getClient().addNeighbour(client);
        }else if(responsetype == 2){
            NeighbourNode client = new NeighbourNode(InetAddress.getByName(commandList[commandList.length-2].trim()), Integer.parseInt(commandList[commandList.length-1].trim()));
            this.getClient().addNeighbour(client);
            client = new NeighbourNode(InetAddress.getByName(commandList[commandList.length-4].trim()), Integer.parseInt(commandList[commandList.length-3].trim()));
            this.getClient().addNeighbour(client);
        }
        return response;
    }
    
    public String leaveClient(String address,int port) throws IOException{
        String msg = "UNREG "+address+" "+port+" "+getClient().getUserName();
        msg = msg.length()+" "+msg;
        String response = getClient().sendMsg(msg, getBootstrapAddr(), getBootstrapPort());
        String[] commandList = response.split(" ");
        System.out.println(response);
        int responsetype = Integer.parseInt(response.split(" ")[2].trim());
        if (responsetype == 0){
             client.leave();
//            System.out.println("bootstrap says same user");
            return "Successfully Leaved";
            
        }else if(responsetype == 9999){
//            System.out.println("bootstrap says no user");
            return "Error occured";
        }

        return response;
    }

    /**
     * @return the client
     */
    public BaseNode getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(BaseNode client) {
        this.client = client;
    }

    /**
     * @return the BootstrapAddr
     */
    public InetAddress getBootstrapAddr() {
        return BootstrapAddr;
    }

    /**
     * @return the BootstrapPort
     */
    public int getBootstrapPort() {
        return BootstrapPort;
    }
    
}
