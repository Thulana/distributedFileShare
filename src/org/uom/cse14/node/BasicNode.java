/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node;

import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author thulana
 */
public class BasicNode {

    private InetAddress address;
    private String userName;
    private int port;

    public BasicNode() {
    }

    public BasicNode(InetAddress address, String userName, int port) {
        this.address = address;
        this.userName = userName;
        this.port = port;
    }

    public BasicNode(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
    
}
