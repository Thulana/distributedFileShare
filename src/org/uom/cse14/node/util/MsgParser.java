/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 *
 * @author thulana
 */
public class MsgParser {



    public static Object[] messageParser(@NotNull String message, @NotNull String command){
        int messageLength = Integer.parseInt(message.split(" ")[0]);
        if (messageLength != message.length()){
            return new Object[] {1,"Corrupted Message"};
        }
        String messageContent = message.split(" ")[1];
        switch (command){
            case "DISCOVERY":
                ArrayList<String> ipList = new ArrayList<String>();
                ArrayList<String> portList = new ArrayList<String>();
                String[] neighbours =  messageContent.split(",");
                for(String neighbour:neighbours){
                    ipList.add(neighbour.split(":")[0]);
                    portList.add(neighbour.split(":")[1]);
                }
                return new Object[] {0, "error", ipList,portList};

            case "JOIN":
                return new Object[] {0, "error", "response" };
        }


    return null;
    }


    
}
