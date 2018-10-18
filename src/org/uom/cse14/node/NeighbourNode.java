
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node;

        import java.io.IOException;
        import java.net.InetAddress;
/**
 *
 * @author thulana
 */
public class NeighbourNode extends BasicNode {


    private int retryCount;


    public NeighbourNode(String userName,InetAddress address,int port) throws IOException {
        this.port = port;
        this.address = address;
        this.userName = userName;
        this.retryCount = 0;

    }

    public NeighbourNode(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void setRetryCount(int retryCount){
        this.retryCount = retryCount;
    }

    public int getRetryCount(){
        return retryCount;
    }




}

