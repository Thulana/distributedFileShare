/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.FileServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import static org.uom.cse14.node.util.NetworkConstants.TCP_PORT_OFFSET;

/**
 *
 * @author pasindu
 */
public class ServerController {
    FileServer fs;
        
    public void createServer(String upPath,int portNum){
        try{
        fs = new FileServer(upPath);
        System.setProperty("java.rmi.server.hostname","192.168.8.100");
        FileInterface stub = (FileInterface)UnicastRemoteObject.exportObject(fs, 0);
        Registry registry = LocateRegistry.createRegistry(portNum+TCP_PORT_OFFSET);
        registry.bind("Hello", stub);


    } catch (AlreadyBoundException | RemoteException e) {
        System.err.println("Server exception: " + e.toString());
    }
    }
    
    
}
