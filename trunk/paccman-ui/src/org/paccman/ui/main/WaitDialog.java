/*
 *
 *  Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
 *                   http://paccman.sourceforge.net 
 *
 *  This program is free software; you can redistribute it and/or modify      
 *  it under the terms of the GNU General Public License as published by      
 *  the Free Software Foundation; either version 2 of the License, or         
 *  (at your option) any later version.                                       
 *
 *  This program is distributed in the hope that it will be useful,           
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of            
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
 *  GNU General Public License for more details.                              
 *
 *  You should have received a copy of the GNU General Public License         
 *  along with this program; if not, write to the Free Software               
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *
 */

package org.paccman.ui.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author  joao
 */
public class WaitDialog extends javax.swing.JDialog implements PropertyChangeListener {
    
    DialogWaitableWorker worker;
    
    /** Creates new form WaitDialog */
    public WaitDialog(DialogWaitableWorker worker, java.awt.Frame parent, String title) {
        super(parent, true);
        initComponents();
        setTitle(title);
        this.worker = worker;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskDescriptionLbl = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        taskDescriptionLbl.setText("Step description");

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(cancelBtn)
                            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(taskDescriptionLbl)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(taskDescriptionLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelBtn)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-410)/2, (screenSize.height-123)/2, 410, 123);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelBtnActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel taskDescriptionLbl;
    // End of variables declaration//GEN-END:variables

    public void propertyChange(PropertyChangeEvent evt) {
        if ("step".equals(evt.getPropertyName())) {
            DialogWaitableWorker.Step s = (DialogWaitableWorker.Step) evt.getNewValue();
            taskDescriptionLbl.setText(s.getDescription());
            progressBar.setValue(s.getProgress());
        }
    }
    
}
