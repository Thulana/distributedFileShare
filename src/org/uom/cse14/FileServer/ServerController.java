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

/**
 *
 * @author pasindu
 */
public class ServerController {
    FileServer fs;
        
    public void createServer(String upPath){
        try{
        fs = new FileServer(upPath);
        System.setProperty("java.rmi.server.hostname","192.168.8.100");
        FileInterface stub = (FileInterface)UnicastRemoteObject.exportObject(fs, 0);
        Registry registry = LocateRegistry.createRegistry(3045);
        registry.bind("Hello", stub);


    } catch (AlreadyBoundException | RemoteException e) {
        System.err.println("Server exception: " + e.toString());
    }
    }
    
    
}
