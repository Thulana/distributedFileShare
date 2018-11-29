/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.discover;

import java.io.IOException;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.NeighbourNode;
import org.uom.cse14.node.util.MsgParser;
import org.uom.cse14.node.util.NetworkConstants;

/**
 *
 * @author thulana
 */
public class NodeDiscovery implements Runnable {
    private BaseNode client;
    private byte[] in;
    private byte[] out;

    public NodeDiscovery() {
    }

    public NodeDiscovery(BaseNode client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        while (true) {            

            try {
                Thread.sleep(10000);

                List<String> removeList = new ArrayList<>();
                List<String> possibleList = new ArrayList<>();
                client.getClientList().forEach((neighborKey,neighbor)->{
                    if(neighbor.getRetryCount() >= 3){
                        removeList.add(neighborKey);
                        System.out.println(neighborKey);
                    }
                    else {
                        possibleList.add(neighborKey);
                    }
                });
                        
                client.getClientList().keySet().removeAll(removeList);
                Date nowDate = new Date();  
                DateFormat formatter;
                formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                if ((possibleList.size() < NetworkConstants.NUM_OF_NEIGHBOURS) && (possibleList.size() >1)){
                    Random r = new Random();
                    int randNum = r.nextInt(possibleList.size());
                    NeighbourNode selectedNeighbour = client.getClientList().get( possibleList.get(randNum));
                    this.discover(selectedNeighbour.getAddress(),selectedNeighbour.getPort());
                    client.addTask("DC&"+selectedNeighbour.getAddress().toString().substring(1)+selectedNeighbour.getPort(),formatter.format(nowDate));
                    int secondRandomNum = r.nextInt(possibleList.size());
                    while(randNum == secondRandomNum){
                        secondRandomNum = r.nextInt(possibleList.size());
                    }
                    selectedNeighbour = client.getClientList().get(possibleList.get(randNum));
                    this.discover(selectedNeighbour.getAddress(),selectedNeighbour.getPort());
                    client.addTask("DC&"+selectedNeighbour.getAddress().toString().substring(1)+selectedNeighbour.getPort(),formatter.format(nowDate));
                }
                else if  (possibleList.size() == 1){

                    NeighbourNode selectedNeighbour = client.getClientList().get( possibleList.get(0));
                    this.discover(selectedNeighbour.getAddress(),selectedNeighbour.getPort());
                     client.addTask("DC&"+selectedNeighbour.getAddress().toString().substring(1)+selectedNeighbour.getPort(),formatter.format(nowDate));
                }


            } catch (InterruptedException ex) {
                Logger.getLogger(NodeDiscovery.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException e) {
            }
        }
    }

    private void discover(InetAddress ipAddress, int port) throws IOException {
        String discoverMessage = MsgParser.sendMessageParser(client,"DISCOVER");
        client.send(ipAddress,port,discoverMessage);
    }
    // thread for node discovery ( target - keep active node count > 3 )
    
}
