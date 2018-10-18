/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uom.cse14.node.ui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uom.cse14.node.Node;
import org.uom.cse14.node.NodeController;
import org.uom.cse14.node.listen.NodeListen;
import org.uom.cse14.node.util.NodeBootstrap;
import org.uom.cse14.node.discover.NodeDiscovery;

/**
 *
 * @author thulana
 */
public class NodeUI extends javax.swing.JFrame {
    Node client;
    NodeListen clientServer;
    NodeBootstrap bootstrap;
    /**
     * Creates new form ClientUI
     */
    public NodeUI() {
        initComponents();

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
        searchBtn = new javax.swing.JButton();
        joinBtn = new javax.swing.JButton();
        nodeportText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextPane = new javax.swing.JTextArea();

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

        searchBtn.setText("Search");

        joinBtn.setText("Join");
        joinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinBtnActionPerformed(evt);
            }
        });
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchBtn)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(query)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(boostrapIpText, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(joinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(leaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(boostrapPortText)
                                    .addComponent(nodeportText)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameText)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(joinBtn)
                    .addComponent(leaveBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(query, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nodeportTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodeportTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nodeportTextActionPerformed

    private void joinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinBtnActionPerformed
         try {
            client = new Node(usernameText.getText(),Integer.parseInt(nodeportText.getText()));
            clientServer = new NodeListen(Integer.parseInt(nodeportText.getText()), client);
            new Thread(clientServer,"nodeServer").start();
            bootstrap = new NodeBootstrap(client, InetAddress.getByName(boostrapIpText.getText()),Integer.parseInt(boostrapPortText.getText()));
            String response = bootstrap.registerClient(bootstrap.getBootstrapAddr().getHostAddress(), Integer.parseInt(nodeportText.getText()));
            NodeDiscovery discovery = new NodeDiscovery(client,Integer.parseInt(nodeportText.getText()));
            new Thread(discovery,"nodeDiscovery").start();
            consoleTextPane.append(response+"\n");
            System.out.println("response : "+response);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NodeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_joinBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt){

        String fileQuery = query.getText();
        client.search(fileQuery);



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
            consoleTextPane.append(response+"\n");
        } catch (UnknownHostException ex) {
            Logger.getLogger(NodeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NodeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_leaveBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Node client ;
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
    private javax.swing.JButton searchBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField query;
    private javax.swing.JButton joinBtn;
    private javax.swing.JButton leaveBtn;
    private javax.swing.JTextField nodeportText;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
