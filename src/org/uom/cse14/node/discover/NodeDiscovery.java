/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.discover;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.uom.cse14.node.BasicNode;
import org.uom.cse14.node.Node;

/**
 *
 * @author thulana
 */
public class NodeDiscovery implements Runnable {
    private Node node;

    public NodeDiscovery() {
    }

    public NodeDiscovery(Node node) {
        this.node = node;
    }
    
    @Override
    public void run() {
        while (true) {            
            System.out.println("discovering");
            try {
                Thread.sleep(100000);

                for(Object neighbour :node.getClientList() ){
                    BasicNode neighbourNode = (BasicNode)neighbour;
                    if(neighbourNode.getRetryCount() >= 3){
                        node.removeNeighbour(neighbourNode);
                    }
                }

                List neighbourList = node.getClientList();

                if (neighbourList.size() <6){
                    int randNum = 3;
                    BasicNode selectedNeighbour = (BasicNode)neighbourList.get(randNum);
                    this.discover(selectedNeighbour.getAddress(),selectedNeighbour.getPort());

                    randNum = 1;
                    selectedNeighbour = (BasicNode)neighbourList.get(randNum);
                    this.discover(selectedNeighbour.getAddress(),selectedNeighbour.getPort());
                }


            } catch (InterruptedException ex) {
                Logger.getLogger(NodeDiscovery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void discover(InetAddress ipAddress, int port){

    }
    // thread for node discovery ( target - keep active node count > 3 )
    
}
