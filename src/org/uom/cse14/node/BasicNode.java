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

    protected InetAddress address;
    protected String userName;
    protected int port;
    private int retryCount;

    public BasicNode() {
    }

    public BasicNode(InetAddress address, String userName, int port) {
        this.address = address;
        this.userName = userName;
        this.port = port;
        this.retryCount = 0;
    }

    public BasicNode(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.retryCount = 0;
    }

    public  void setRetryCount (int retryCount){this.retryCount = retryCount; }
    public  int getRetryCount() {return retryCount; }

    public InetAddress getAddress() {
        return address;
    }
    public String getUserName() {
        return userName;
    }
    public int getPort(){return port;}


}
