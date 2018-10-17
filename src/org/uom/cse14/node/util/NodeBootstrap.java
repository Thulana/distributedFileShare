/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import java.io.IOException;
import java.net.InetAddress;
import org.uom.cse14.node.BasicNode;
import org.uom.cse14.node.Node;

/**
 *
 * @author thulana
 */
public class NodeBootstrap {
    private Node client;
    private InetAddress BootstrapAddr;
    private int BootstrapPort;

    public NodeBootstrap(Node client) {
        this.client = client;
    }

    public NodeBootstrap(Node client, InetAddress BootstrapAddr) {
        this.client = client;
        this.BootstrapAddr = BootstrapAddr;
    }

    public NodeBootstrap(Node client, InetAddress BootstrapAddr, int BootstrapPort) {
        this.client = client;
        this.BootstrapAddr = BootstrapAddr;
        this.BootstrapPort = BootstrapPort;
    }
    
    
    public String registerClient(String address,int port) throws IOException{
        String msg = "REG "+address+" "+port+" "+client.getUserName();
        msg = msg.length()+" "+msg;
        String response = client.sendMsg(msg, BootstrapAddr, BootstrapPort);
        String[] commandList = response.split(" ");
        int responsetype = Integer.parseInt(response.split(" ")[2].trim());
        if (responsetype == 9998){
            System.out.println("bootstrap says same user");
        }else if(responsetype == 0){
            System.out.println("bootstrap says no user");
        }else if(responsetype == 1){
            BasicNode client = new BasicNode(InetAddress.getByName(commandList[-2]), Integer.parseInt(commandList[-1]));
            this.client.addNeighbour(client);
        }else if(responsetype == 2){
            BasicNode client = new BasicNode(InetAddress.getByName(commandList[-2]), Integer.parseInt(commandList[-1]));
            this.client.addNeighbour(client);
            client = new BasicNode(InetAddress.getByName(commandList[-4]), Integer.parseInt(commandList[-3]));
            this.client.addNeighbour(client);
        }
        return response;
    }
    
}
