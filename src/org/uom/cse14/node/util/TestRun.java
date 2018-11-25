/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 *
 * @author pasindu
 */
public class TestRun {
     /**
     * @param args the command line arguments
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws SocketException, UnknownHostException {

        Date nowDate = new Date();  
        nowDate.setTime(nowDate.getTime() - 2000);
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(formatter.format(nowDate));
       // you can change format of date
        System.out.println(TimestampUtill.TimeComparator(formatter.format(nowDate),5000)); 

        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            InetAddress ip = socket.getLocalAddress();
            System.out.println(ip);
         }
    }
    
}
