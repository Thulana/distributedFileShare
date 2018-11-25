/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.ui;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.concurrent.ConcurrentHashMap;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.uom.cse14.FileServer.ServerController;
import org.uom.cse14.TaskWorker.TaskWorker;
import org.uom.cse14.node.BaseNode;
import org.uom.cse14.node.FileClient;
import org.uom.cse14.node.NodeController;
import org.uom.cse14.node.listen.NodeListen;
import org.uom.cse14.node.util.NetworkConstants;
import org.uom.cse14.node.util.NodeBootstrap;
import org.uom.cse14.node.discover.NodeDiscovery;

/**
 *
 * @author thulana
 */
public class NodeUI extends javax.swing.JFrame {

    BaseNode client;
    NodeListen clientServer;
    NodeBootstrap bootstrap;
    ServerController fileController;
    FileClient fClient;
    JFileChooser chooser;
    String choosertitle;
    String downFilePath;
    String upFilePath;
    TaskWorker worker;
    ConcurrentHashMap<String, String> results;

    /**
     * Creates new form ClientUI
     */
    public NodeUI() {
        initComponents();
        results = new ConcurrentHashMap<>();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        boostrapIpText = new javax.swing.JTextField();
        boostrapPortText = new javax.swing.JTextField();
        leaveBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        query = new javax.swing.JTextField();
        srchBtn = new javax.swing.JButton();
        joinBtn = new javax.swing.JButton();
        nodeportText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextPane = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dpathText = new javax.swing.JLabel();
        upathText = new javax.swing.JLabel();
        dfPathBtn = new javax.swing.JButton();
        ufPathBtn = new javax.swing.JButton();
        resultBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        downloadBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ipTable = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        outMsgLabel = new javax.swing.JLabel();
        inMsgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Node");

        jLabel2.setText("Boostrap IP :");

        jLabel3.setText("Boostrap Port :");

        boostrapIpText.setText("localhost");
        boostrapIpText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boostrapIpTextActionPerformed(evt);
            }
        });

        boostrapPortText.setText("55555");

        leaveBtn.setText("Leave");
        leaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveBtnActionPerformed(evt);
            }
        });

        jLabel4.setText("Search Query :");

        query.setName("query"); // NOI18N

        srchBtn.setText("Search");
        srchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srchBtnActionPerformed(evt);
            }
        });

        joinBtn.setText("Join");
        joinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinBtnActionPerformed(evt);
            }
        });

        nodeportText.setText("2222");
        nodeportText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodeportTextActionPerformed(evt);
            }
        });

        jLabel5.setText("Node Port :");

        jLabel6.setText("Username :");

        usernameText.setText("user1");
        usernameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextActionPerformed(evt);
            }
        });

        consoleTextPane.setColumns(20);
        consoleTextPane.setRows(5);
        jScrollPane1.setViewportView(consoleTextPane);

        jLabel7.setText("Download Path :");

        jLabel8.setText("Upload Path :");

        dpathText.setText("Undefined");
        dpathText.setName(""); // NOI18N

        upathText.setText("Undefined");

        dfPathBtn.setText("File Path");
        dfPathBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dfPathBtnActionPerformed(evt);
            }
        });

        ufPathBtn.setText("File Path");
        ufPathBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ufPathBtnActionPerformed(evt);
            }
        });

        resultBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultBoxActionPerformed(evt);
            }
        });

        jLabel9.setText("Search Results :");

        downloadBtn.setText("Download");
        downloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadBtnActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(ipTable);

        jLabel10.setText("In :");

        jLabel11.setText("Out :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(query)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(srchBtn)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(resultBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(downloadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(boostrapIpText, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                        .addComponent(boostrapPortText)
                                        .addComponent(nodeportText)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(usernameText)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dpathText, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(upathText, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(joinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(leaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inMsgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ufPathBtn)
                                    .addComponent(dfPathBtn))))
                        .addGap(12, 12, 12)
                        .addComponent(outMsgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(boostrapIpText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(boostrapPortText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nodeportText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dpathText)
                    .addComponent(dfPathBtn)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(upathText)
                        .addComponent(ufPathBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outMsgLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inMsgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(joinBtn)
                            .addComponent(leaveBtn)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(query, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srchBtn)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(downloadBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nodeportTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodeportTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nodeportTextActionPerformed

    private void joinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinBtnActionPerformed
        try {
             final DatagramSocket testSocket = new DatagramSocket();
            testSocket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            InetAddress nodeIP = testSocket.getLocalAddress();
   
            client = new BaseNode(usernameText.getText(), Integer.parseInt(nodeportText.getText()),nodeIP,upFilePath);
            clientServer = new NodeListen(Integer.parseInt(nodeportText.getText()), client,results);
            worker= new TaskWorker(client);
            fClient = new FileClient(downFilePath);
            //fClient.downloadFile("TestFile.txt");
            fileController = new ServerController();
            fileController.createServer(upFilePath,Integer.parseInt(nodeportText.getText()),nodeIP);
            new Thread(clientServer, "nodeServer").start();
            new Thread(worker, "worker").start();
            bootstrap = new NodeBootstrap(client, InetAddress.getByName(boostrapIpText.getText()), Integer.parseInt(boostrapPortText.getText()));
            String response = bootstrap.registerClient(nodeIP.getHostAddress(), Integer.parseInt(nodeportText.getText()));
            NodeDiscovery discovery = new NodeDiscovery(client);
            new Thread(discovery, "nodeDiscovery").start();
            new StatUpdater(ipTable, inMsgLabel, outMsgLabel, client).execute();
            consoleTextPane.append(response + "\n");
            System.out.println("response : " + response);

        } catch (UnknownHostException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_joinBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        System.out.println("Search Initiated at BaseNode");
        String fileQuery = query.getText();
        client.search(fileQuery, NetworkConstants.NETWORK_HOPS, client.getAddress(), client.getPort(),client.getAddress(), client.getPort());
        
    }

    private void usernameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextActionPerformed

    private void boostrapIpTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boostrapIpTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boostrapIpTextActionPerformed

    private void leaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveBtnActionPerformed
        try {
            InetAddress inet = InetAddress.getLocalHost();
            String response = bootstrap.leaveClient(inet.getHostAddress(), Integer.parseInt(nodeportText.getText()));
            consoleTextPane.append(response + "\n");
            client.leave();
            System.exit(0);
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NodeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_leaveBtnActionPerformed

    private void dfPathBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dfPathBtnActionPerformed
        // TODO add your handling code here:

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            downFilePath = chooser.getSelectedFile().toString();
            dpathText.setText(downFilePath);
        } else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_dfPathBtnActionPerformed

    private void ufPathBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ufPathBtnActionPerformed
        // TODO add your handling code here:
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            upFilePath = chooser.getSelectedFile().toString();
            upathText.setText(upFilePath);
        } else {
            System.out.println("No Selection ");
        }
    }//GEN-LAST:event_ufPathBtnActionPerformed

    private void srchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srchBtnActionPerformed
        System.out.println("Search Initiated at BaseNode");
        String fileQuery = query.getText();

        try {
            client.search(fileQuery, NetworkConstants.NETWORK_HOPS, client.getAddress(), client.getPort(),client.getAddress(), client.getPort());
        } catch (IOException ex) {
            Logger.getLogger(NodeUI.class.getName()).log(Level.SEVERE, null, ex);
        };
        new SearchUpdater(resultBox, results, 3000).execute();
    }//GEN-LAST:event_srchBtnActionPerformed

    private void resultBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resultBoxActionPerformed

    private void downloadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadBtnActionPerformed
        String fileData = (String) resultBox.getSelectedItem();
        String filename = fileData.split(" ")[0];
        String ip = fileData.split(" ")[1];
        int port = Integer.parseInt(fileData.split(" ")[2]);
        fClient.downloadFile(filename, ip, port);
    }//GEN-LAST:event_downloadBtnActionPerformed
            
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        BaseNode client;
        NodeListen clientServer;
        NodeBootstrap bootstrap;
        NodeDiscovery discovery;

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NodeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NodeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NodeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NodeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NodeUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField boostrapIpText;
    private javax.swing.JTextField boostrapPortText;
    private javax.swing.JTextArea consoleTextPane;
    private javax.swing.JButton dfPathBtn;
    private javax.swing.JButton downloadBtn;
    private javax.swing.JLabel dpathText;
    private javax.swing.JLabel inMsgLabel;
    private javax.swing.JList<String> ipTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton joinBtn;
    private javax.swing.JButton leaveBtn;
    private javax.swing.JTextField nodeportText;
    private javax.swing.JLabel outMsgLabel;
    private javax.swing.JTextField query;
    private javax.swing.JComboBox<String> resultBox;
    private javax.swing.JButton srchBtn;
    private javax.swing.JButton ufPathBtn;
    private javax.swing.JLabel upathText;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
