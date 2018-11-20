/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
IMPORTANT

Task Queue Format =>  SR::[searchName]::neighbourhash || [Timestamp]||[AlternativeQuery]
*/
package org.uom.cse14.TaskWorker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.util.TimestampUtill;

/**
 *
 * @author pasindu
 */
public class TaskWorker implements Runnable {
    private BaseNode client;
    
     public TaskWorker(BaseNode client) {
        this.client = client;
    }

    @Override
    public void run() {
          while (true) {            

            try {
                Thread.sleep(10000);
                List<String> removeList = new ArrayList<>();
                client.getTaskList().forEach((id,task)->{
                    String[] idMain = id.split("&");
                    switch(idMain[0]){
                        case "SR":
                            String[] msgData = task.split("&");
                            System.out.println(msgData[0]);
                            if(TimestampUtill.TimeComparator(msgData[0],5000)){
                   
                                client.updateRetryCount(idMain[2],"incre");
                                if (!msgData[1].equals("None")){
                                    try {
                                        client.send(InetAddress.getByName(msgData[1]), Integer.parseInt(msgData[2]),msgData[3]);
                                    } catch (UnknownHostException ex) {
                                        Logger.getLogger(TaskWorker.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(TaskWorker.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                 removeList.add(id);
                             }
                            
                         break;
                     }
                    client.getTaskList().keySet().removeAll(removeList);
                });
                
            } catch (InterruptedException ex) {
                  Logger.getLogger(TaskWorker.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
    }
    
}
