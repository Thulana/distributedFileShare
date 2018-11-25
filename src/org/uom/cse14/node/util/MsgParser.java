/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;


import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.BasicNode;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thulana
 */
public class MsgParser {




    public static Object[] receivedMessageParser( String message,  String command, BasicNode node){

        System.out.printf(message);
        System.out.printf(Integer.toString(node.getPort()));
        return new Object[] {1,"Corrupted Message"};

    }

    public static Object[] receivedMessageParser( String message,  String command){
        int messageLength = Integer.parseInt(message.split(" ")[0]);

        if (messageLength != message.length()){
            return new Object[] {1,"Corrupted Message"};
        }

        String[] messageItems = message.split(" ");
        List<String> messageContent = Arrays.asList(messageItems).subList(2,messageItems.length);



        switch (command){
            case "R_DISCOVER":
                ArrayList<InetAddress> ipList = new ArrayList<InetAddress>();
                ArrayList<Integer> portList = new ArrayList<Integer>();
                String[] messageSubContent = messageContent.get(0).split(",");
                for(String neighbour:messageSubContent){
                    try {
                        ipList.add(InetAddress.getByName(neighbour.split(":")[0]));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    portList.add(Integer.parseInt(neighbour.split(":")[1]));
                }
                return new Object[] {0, "error", ipList,portList};

            case "DISCOVER":
                try {
                    return new Object[] {0, "error", InetAddress.getByName(messageContent.get(0).split(":")[0]),Integer.parseInt(messageContent.get(0).split(":")[1])};

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return new Object[] {1,e.getMessage(), "response" };

                }

            case "JOIN":
                return new Object[] {0, "error", "response" };



        }

    return new Object[] {1, "Unknown Error" };
    }

    public static String sendMessageParser( Object messageData,  String command){
        String messageText =command+" ";
        switch (command){
            case "DISCOVER":
                BasicNode discoverNode = (BasicNode)messageData;
                messageText = messageText+discoverNode.getAddress().getHostAddress()+":"+Integer.toString(discoverNode.getPort());
                break;

            case "SER":
                messageText = messageText + (String)messageData;
                break;

            case "R_DISCOVER":
                BaseNode client = (BaseNode)messageData;
                List<String> nodeList = new ArrayList<>();
                client.getClientList().forEach((neighborKey, neighbor)->{
                    nodeList.add(neighbor.getAddress().getHostAddress()+":"+Integer.toString(neighbor.getPort()));
                });

                messageText = messageText +String.join(",", nodeList);
                break;

            case "LEAVE":
                BasicNode leaveNode = (BasicNode)messageData;
                messageText = messageText + leaveNode.getAddress().getHostAddress()+":"+Integer.toString(leaveNode.getPort());
                break;

            case "SER_R":
                messageText = messageText + (String)messageData;
                break;

            case "SEROK":
                messageText = messageText + (String)messageData;
                break;
                
            case "JOIN":
                messageText = messageText + (String)messageData;
                break;
        }

        return formatMsgLength(messageText);
    }

    private static String formatMsgLength(String msg){
        String formatted = String.format("%04d", msg.length()+5)+" "+msg;
        return formatted;
    }
    
}
