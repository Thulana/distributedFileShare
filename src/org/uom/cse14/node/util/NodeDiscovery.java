/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import java.util.logging.Level;
import java.util.logging.Logger;
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
            } catch (InterruptedException ex) {
                Logger.getLogger(NodeDiscovery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // thread for node discovery ( target - keep active node count > 3 )
    
}
