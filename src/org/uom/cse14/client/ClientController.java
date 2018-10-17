/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uom.cse14.client.server.ClientServer;
import org.uom.cse14.client.util.ClientBootstrap;

/**
 *
 * @author thulana
 */
public class ClientController {
    public static String bootstrapIP = "localhost";
    public static int bootstrapPort = 55555;
    public static void main(String[] args) {
        try {
            Client client = new Client("testClient2",2237);
            ClientServer clientServer = new ClientServer(2236, client);
            ClientBootstrap bootstrap = new ClientBootstrap(client, InetAddress.getByName(ClientController.bootstrapIP),ClientController.bootstrapPort );
            String response = bootstrap.registerClient(bootstrapIP, 2236);
            System.out.println("response : "+response);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
