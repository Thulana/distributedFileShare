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
    private ConcurrentHashMap<String,String> searchResult;
    public SearchUpdater(JComboBox resultBox, ConcurrentHashMap<String,String> searchResult) {
        
    }

    
    @Override
    protected Integer doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    protected void done() {
        super.done(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
