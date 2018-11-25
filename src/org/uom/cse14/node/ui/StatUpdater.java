/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.ui;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingWorker;
import org.uom.cse14.node.BaseNode;

/**
 *
 * @author thulana
 */
public class StatUpdater extends SwingWorker<Integer, String> {

    private JList ipTable;
    private JLabel inLabel;
    private JLabel outLabel;
    private BaseNode client;

//    public StatUpdater(JComboBox resultBox, ConcurrentHashMap<String, String> searchResult, int inCounter) {
//        this.resultBox = resultBox;
//        this.clientList = searchResult;
//        this.inCounter = inCounter;
//    }
    public StatUpdater(JList ipTable, JLabel inLabel, JLabel outLabel, BaseNode client) {
        this.ipTable = ipTable;
        this.inLabel = inLabel;
        this.outLabel = outLabel;
        this.client = client;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        while (true) {
            Thread.currentThread().sleep(1000);
            publish("update");
        }
    }

    @Override
    protected void process(List<String> chunks) {
        inLabel.setText(Integer.toString(client.getInCounter()));
        outLabel.setText(Integer.toString(client.getOutCounter()));
        ipTable.removeAll();
        DefaultListModel demoList = new DefaultListModel();
        client.getClientList().forEach((neighborKey, neighbor) -> {   
            demoList.addElement(neighbor.getAddress().getHostAddress()+":"+neighbor.getPort());    
        });
        ipTable.setModel(demoList);
//        System.out.println("in out count"+client.getInCounter()+" "+client.getOutCounter());
    }

}
