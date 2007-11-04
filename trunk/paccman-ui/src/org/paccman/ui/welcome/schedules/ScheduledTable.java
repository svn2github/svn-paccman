/*
 
    Copyright (C)    2005 Joao F. (joaof@sourceforge.net)
                     http://paccman.sourceforge.net
 
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
 
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
 */

package org.paccman.ui.welcome.schedules;

import javax.swing.JOptionPane;
import org.paccman.ui.common.table.Table;
import org.paccman.ui.main.Main;
import org.paccman.ui.scheduling.ExpiredScheduledTransaction;

/**
 *
 * @author  joao
 */
public class ScheduledTable extends Table {
    
    public ScheduledTableModel getScheduledTableModel() {
        return (ScheduledTableModel)dataModel;
    }
    
    /** Creates new form BeanForm */
    public ScheduledTable() {
        initComponents();
        
        setModel(new ScheduledTableModel());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        popupMenu = new javax.swing.JPopupMenu();
        registerMnu = new javax.swing.JMenuItem();
        editAndRegisterMnu = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JSeparator();
        ignoreMnu = new javax.swing.JMenuItem();

        registerMnu.setText("Register");
        registerMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerMnuActionPerformed(evt);
            }
        });

        popupMenu.add(registerMnu);

        editAndRegisterMnu.setText("Edit and Register...");
        editAndRegisterMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editAndRegisterMnuActionPerformed(evt);
            }
        });

        popupMenu.add(editAndRegisterMnu);

        popupMenu.add(separator1);

        ignoreMnu.setText("Ignore");
        ignoreMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ignoreMnuActionPerformed(evt);
            }
        });

        popupMenu.add(ignoreMnu);

        setFocusable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

    }// </editor-fold>//GEN-END:initComponents
    
    private void ignoreMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ignoreMnuActionPerformed
        ignore();
    }//GEN-LAST:event_ignoreMnuActionPerformed
    
    private void editAndRegisterMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAndRegisterMnuActionPerformed
        registerAndEdit();
    }//GEN-LAST:event_editAndRegisterMnuActionPerformed
    
    private void registerMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerMnuActionPerformed
        JOptionPane.showMessageDialog(this, ":TODO:");
    }//GEN-LAST:event_registerMnuActionPerformed
    
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (doProcessPopup(evt)) {
            return;
        }
    }//GEN-LAST:event_formMouseReleased
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (doProcessPopup(evt)) {
            return;
        }
    }//GEN-LAST:event_formMousePressed
    
    private boolean doProcessPopup(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            setRowSelectionInterval(rowAtPoint(evt.getPoint()), rowAtPoint(evt.getPoint()));
            popupMenu.show(this, evt.getX(), evt.getY());
            return true;
        }
        return false;
    }
    
    private void ignore() {
        int row = getSelectedRow();
        if (row == -1) {
            // No scheduled transactin selected
            return;
        }
        ScheduledTableModel schedTransaction = (ScheduledTableModel)getModel();
        ExpiredScheduledTransaction est = schedTransaction.getExpiredScheduledTransaction(row);
        if (est.isRegistered()) {
            // If the expired transaction is already registered,
            // do not register it again.
            return;
        }
        
        schedTransaction.removeExpiredScheduledTransaction(row);
    }
    
    private void registerAndEdit() {
        int row = getSelectedRow();
        if (row == -1) {
            // No scheduled transactin selected
            return;
        }
        ScheduledTableModel schedTransaction = (ScheduledTableModel)getModel();
        ExpiredScheduledTransaction est = schedTransaction.getExpiredScheduledTransaction(row);
        if (est.isRegistered()) {
            // If the expired transaction is already registered,
            // do not register it again.
            return;
        }
        Main.getMain().gotoAccountTransactionTab(est.getAccount());
        Main.getMain().getTransactionFormTab().newTransaction(est.getScheduledTransaction().newInstance());
        Main.getMain().getTransactionFormTab().setScheduledTransaction(est);
    }
    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if (evt.getClickCount() == 2) {
            registerAndEdit();
        }
    }//GEN-LAST:event_formMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem editAndRegisterMnu;
    private javax.swing.JMenuItem ignoreMnu;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem registerMnu;
    private javax.swing.JSeparator separator1;
    // End of variables declaration//GEN-END:variables
    
}