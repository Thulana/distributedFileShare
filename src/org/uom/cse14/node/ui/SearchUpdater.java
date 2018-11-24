/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.ui;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JComboBox;
import javax.swing.SwingWorker;

/**
 *
 * @author thulana
 */
public class SearchUpdater extends SwingWorker<Integer, String> {

    private JComboBox resultBox;
    private ConcurrentHashMap<String, String> searchResult;
    private int waitTime;

    public SearchUpdater(JComboBox resultBox, ConcurrentHashMap<String, String> searchResult, int waitTime) {
        this.resultBox = resultBox;
        this.searchResult = searchResult;
        this.waitTime = waitTime;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        System.out.println("updater initiated");
        Thread.currentThread().sleep(waitTime);
        System.out.println("waiting time completed");
        return 1;
    }

    @Override
    protected void done() {
//        super.done(); //To change body of generated methods, choose Tools | Templates.
        resultBox.removeAllItems();
        if (searchResult.size() > 0) {
            
            for (String key : searchResult.keySet()) {
                resultBox.addItem(key+" "+searchResult.get(key));
            }
            searchResult.clear();
        }
    }

}
