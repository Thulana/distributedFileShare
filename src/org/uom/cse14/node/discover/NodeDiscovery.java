/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.discover;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uom.cse14.node.BasicNode;
import org.uom.cse14.node.Node;
import org.uom.cse14.node.util.MsgParser;

/**
 *
 * @author thulana
 */
public class NodeDiscovery implements Runnable {
    private Node node;
    private byte[] in;
    private byte[] out;

    public NodeDiscovery() {
    }

    public NodeDiscovery(Node node) throws SocketException {
        this.node = node;
    }
    
    @Override
    public void run() {
        while (true) {            
            System.out.println("discovering");
            try {
                Thread.sleep(10000);

                for(Object neighbour :node.getClientList() ){
                    BasicNode neighbourNode = (BasicNode)neighbour;
                    if(neighbourNode.getRetryCount() >= 3){
                        node.removeNeighbour(neighbourNode);
                    }
                }

                List neighbourList = node.getClientList();

                if ((neighbourList.size() <6) && (neighbourList.size() >1)){
                    int randNum = (int) (Math.random() * (neighbourList.size()- 1)) + 1;
                    BasicNode selectedNeighbour = (BasicNode)neighbourList.get(randNum);
                    this.discover(selectedNeighbour.getAddress(),selectedNeighbour.getPort());
                }


            } catch (InterruptedException ex) {
                Logger.getLogger(NodeDiscovery.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void discover(InetAddress ipAddress, int port) throws IOException {
        String discoverMessage = MsgParser.sendMessageParser(node.getAddress()+":"+Integer.toString(node.getPort()),"Discover");
        node.send(ipAddress,port,discoverMessage);
    }
    // thread for node discovery ( target - keep active node count > 3 )
    
}
