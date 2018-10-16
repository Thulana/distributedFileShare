/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.client.util;

import java.io.IOException;
import java.net.InetAddress;
import org.uom.cse14.client.Client;

/**
 *
 * @author thulana
 */
public class ClientBootstrap {
    private Client client;
    private InetAddress BootstrapAddr;
    private int BootstrapPort;

    public ClientBootstrap(Client client) {
        this.client = client;
    }

    public ClientBootstrap(Client client, InetAddress BootstrapAddr) {
        this.client = client;
        this.BootstrapAddr = BootstrapAddr;
    }

    public ClientBootstrap(Client client, InetAddress BootstrapAddr, int BootstrapPort) {
        this.client = client;
        this.BootstrapAddr = BootstrapAddr;
        this.BootstrapPort = BootstrapPort;
    }
    
    
    public int registerClient(String address,int port) throws IOException{
        String msg = "REG "+address+" "+port+" "+client.getUserName();
        return client.sendMsg1(msg, BootstrapAddr, BootstrapPort);
    }
    
}
