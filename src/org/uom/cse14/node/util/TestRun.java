/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pasindu
 */
public class TestRun {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Date nowDate = new Date();  
        nowDate.setTime(nowDate.getTime() - 2000);
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(formatter.format(nowDate));
       // you can change format of date
        System.out.println(TimestampUtill.TimeComparator(formatter.format(nowDate),5000)); 
    }
    
}
