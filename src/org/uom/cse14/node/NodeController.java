/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uom.cse14.node.server.NodeServer;
import org.uom.cse14.node.util.NodeBootstrap;
import org.uom.cse14.node.util.NodeDiscovery;

/**
 *
 * @author thulana
 */
public class NodeController {
    public static String bootstrapIP = "localhost";
    public static int bootstrapPort = 55555;
    public static void main(String[] args) {
        try {
            Node client = new Node("testClient2",2237);
            NodeServer clientServer = new NodeServer(2236, client);
            new Thread(clientServer,"nodeServer").start();
            NodeBootstrap bootstrap = new NodeBootstrap(client, InetAddress.getByName(NodeController.bootstrapIP),NodeController.bootstrapPort );
            String response = bootstrap.registerClient(bootstrapIP, 2236);
            NodeDiscovery discovery = new NodeDiscovery(client);
            new Thread(discovery,"nodeDiscovery").start();
            
            System.out.println("response : "+response);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}