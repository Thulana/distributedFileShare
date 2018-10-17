/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.server;

/**
 *
 * @author thulana
 */
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uom.cse14.node.Node;

/**
 *
 */
public class WorkerRunnable extends NodeServer implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;

//    public WorkerRunnable(Socket clientSocket, String serverText) throws SocketException {
//        this.clientSocket = clientSocket;
//        this.serverText   = serverText;
//        super(0, null);
//    }
    public WorkerRunnable(int port, Node client, Socket clientSocket, String msg) throws SocketException {
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
