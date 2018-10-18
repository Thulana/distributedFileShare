/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;


import org.uom.cse14.node.BasicNode;
import org.uom.cse14.node.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author thulana
 */
public class MsgParser {




    public static Object[] receivedMessageParser( String message,  String command){
        int messageLength = Integer.parseInt(message.split(" ")[0]);
        System.out.printf(Integer.toString(messageLength));
        if (messageLength != message.length()){
            return new Object[] {1,"Corrupted Message"};
        }

        String[] messageItems = message.split(" ");
        List<String> messageContent = Arrays.asList(messageItems).subList(2,messageItems.length);



        switch (command){
            case "DISCOVER":
                ArrayList<String> ipList = new ArrayList<String>();
                ArrayList<String> portList = new ArrayList<String>();

                for(String neighbour:messageContent){
                    ipList.add(neighbour.split(":")[0]);
                    portList.add(neighbour.split(":")[1]);
                }
                return new Object[] {0, "error", ipList,portList};

            case "JOIN":
                return new Object[] {0, "error", "response" };


        }




    return null;
    }

    public static String sendMessageParser( Object messageData,  String command){
        String messageText =command+" ";
        switch (command){
            case "DISCOVER":
                messageText = (String)messageData;
                messageText = "DISCOVER " + messageText;
                return Integer.toString(messageText.length()+1+Integer.toString(messageText.length()).length()) +" "+messageText;

            case "SEARCH":
                return messageText;

            case "R_DISCOVER":
                boolean firstElement = true;
                for(Object neighbour :(ArrayList)messageData){
                    BasicNode neighbourNode = (BasicNode)neighbour;
                    if (! firstElement){
                        messageText = messageText + ","+neighbourNode.getAddress()+":"+Integer.toString(neighbourNode.getPort());
                    }else{
                        messageText = messageText + neighbourNode.getAddress()+":"+Integer.toString(neighbourNode.getPort());
                        firstElement = false;
                    }
                }
            case "LEAVE":
                BasicNode leaveNode = (BasicNode)messageData;
                messageText = messageText + leaveNode.getAddress()+":"+Integer.toString(leaveNode.getPort());



        }

        return formatMsgLength(messageText);
    }

    private static String formatMsgLength(String msg){
        String formatted = String.format("%04d", msg.length()+5)+" "+msg;
        return formatted;
    }
    
}
