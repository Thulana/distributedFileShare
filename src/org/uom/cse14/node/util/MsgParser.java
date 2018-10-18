/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;


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

    public static String sendMessageParser( Object message,  String command){
        switch (command){
            case "Discover":
                String messageText = (String)message;
                messageText = "DISCOVER " + messageText;
                return Integer.toString(messageText.length()+1+Integer.toString(messageText.length()).length()) +" "+messageText;
            case "search":
                messageText = "SEARCH";
                return messageText;
        }

        return null;
    }

    private static String formatMsgLength(String msg){
        String formatted = String.format("%04d", msg.length()+5)+" "+msg;
        return formatted;
    }
    
}
