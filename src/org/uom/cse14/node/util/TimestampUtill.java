/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pasindu
 */
public class TimestampUtill {
      public static Date convertStringToTime(String str_date)  {
        
          Date dateTime = null;  
          try {
              dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(str_date);
          } catch (ParseException ex) {
              Logger.getLogger(TimestampUtill.class.getName()).log(Level.SEVERE, null, ex);
          }
          return dateTime;
  }
        public static Boolean TimeComparator(String str_date,int delay) {
          Date nowDate = new Date();  
          nowDate.setTime(nowDate.getTime() - delay);
          return convertStringToTime(str_date).before((nowDate));
        
  }
      
      
}
