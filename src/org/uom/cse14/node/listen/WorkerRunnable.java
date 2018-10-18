/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.listen;

/**
 *
 * @author thulana
 */
import java.net.Socket;
import java.net.SocketException;

import org.uom.cse14.node.BaseNode;


/**
 *
 */
public class WorkerRunnable extends NodeListen implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;

//    public WorkerRunnable(Socket clientSocket, String serverText) throws SocketException {
//        this.clientSocket = clientSocket;
//        this.serverText   = serverText;
//        super(0, null);
//    }
    public WorkerRunnable(int port, BaseNode client, Socket clientSocket, String msg) throws SocketException {
        super(port, client);
        this.clientSocket = clientSocket;
        this.serverText = msg;

    }

    public void run() {
        String command = serverText.split(" ")[1];
        if (command.equals("JOIN")) {
        }
    }

    

}
