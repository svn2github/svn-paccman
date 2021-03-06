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

package org.paccman.ui.accounts;

import java.awt.BorderLayout;
import org.paccman.controller.AccountController;
import org.paccman.ui.accountselector.AccountSelectorPanel;
import org.paccman.ui.selector.ControllerSelectionListener;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author  joao
 */
public class AccountFormTab extends javax.swing.JPanel implements ControllerSelectionListener {
    
    /** Creates new form AccountFormTab */
    public AccountFormTab() {
        initComponents();
        initMyComponents();
        setEditMode(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jAccountSelectorPane = new javax.swing.JPanel();
        jEditNewPanel = new javax.swing.JPanel();
        jEditBtn = new javax.swing.JButton();
        jNewBtn = new javax.swing.JButton();
        jAccountFormPane = new javax.swing.JPanel();
        jValidateCancelPanel = new javax.swing.JPanel();
        jValidateBtn = new javax.swing.JButton();
        jCancelBtn = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jAccountSelectorPane.setLayout(new java.awt.BorderLayout());

        jEditNewPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jEditBtn.setText("Edit");
        jEditBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditBtnActionPerformed(evt);
            }
        });
        jEditNewPanel.add(jEditBtn);

        jNewBtn.setText("New");
        jNewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewBtnActionPerformed(evt);
            }
        });
        jEditNewPanel.add(jNewBtn);

        jAccountSelectorPane.add(jEditNewPanel, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(jAccountSelectorPane);

        jAccountFormPane.setLayout(new java.awt.BorderLayout());

        jValidateCancelPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jValidateBtn.setText("Validate");
        jValidateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jValidateBtnActionPerformed(evt);
            }
        });
        jValidateCancelPanel.add(jValidateBtn);

        jCancelBtn.setText("Cancel");
        jCancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCancelBtnActionPerformed(evt);
            }
        });
        jValidateCancelPanel.add(jCancelBtn);

        jAccountFormPane.add(jValidateCancelPanel, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jAccountFormPane);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jValidateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jValidateBtnActionPerformed
        AccountController validatedAccount = null;
        validatedAccount = (AccountController) jAccountFormPanel.onValidate();
        if ( validatedAccount != null) {
            setEditMode(false);
            if (validatedAccount == selectedAccount) {
                // Account updated
                selectedAccount.notifyChange();
            } else {
                // New account added
                getDocumentController().getDocument().addAccount(validatedAccount.getAccount());
                getDocumentController().notifyChange();
                jAccountSelectorPanel.selectAccount(validatedAccount);
            }
        }
    }//GEN-LAST:event_jValidateBtnActionPerformed

    private void jEditBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditBtnActionPerformed
        jAccountFormPanel.onEdit();
        setEditMode(true);
    }//GEN-LAST:event_jEditBtnActionPerformed

    private void jCancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCancelBtnActionPerformed
        jAccountFormPanel.onCancel();
        setEditMode(false);
    }//GEN-LAST:event_jCancelBtnActionPerformed

    private void jNewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewBtnActionPerformed
        jAccountFormPanel.onNew();
        setEditMode(true);
    }//GEN-LAST:event_jNewBtnActionPerformed
    
    private boolean editing        ;
    private AccountController selectedAccount;
    
    public void setEditMode(boolean editing) {
        this.editing = editing;
        jEditBtn.setEnabled(!editing && (selectedAccount != null)); 
        jNewBtn.setEnabled(!editing);
        jValidateBtn.setEnabled(editing);
        jCancelBtn.setEnabled(editing); 
    }
    
    public void registerToDocumentCtrl() {
        jAccountSelectorPanel.registerToDocumentCtrl();
        jAccountFormPanel.registerToDocumentCtrl();
    }
    
    private void initMyComponents() {
        jAccountSelectorPanel = new AccountSelectorPanel();
        jAccountSelectorPane.add(jAccountSelectorPanel, BorderLayout.NORTH);
        
        jAccountFormPanel    = new AccountFormPanel();
        jAccountFormPane.add(jAccountFormPanel, BorderLayout.NORTH);
        
        jAccountSelectorPanel.addListener(this);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jAccountFormPane;
    private javax.swing.JPanel jAccountSelectorPane;
    private javax.swing.JButton jCancelBtn;
    private javax.swing.JButton jEditBtn;
    private javax.swing.JPanel jEditNewPanel;
    private javax.swing.JButton jNewBtn;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton jValidateBtn;
    private javax.swing.JPanel jValidateCancelPanel;
    // End of variables declaration//GEN-END:variables
    
    private AccountSelectorPanel jAccountSelectorPanel;
    private AccountFormPanel     jAccountFormPanel    ;
    
    public void controllerSelected(org.paccman.controller.Controller accountCtrl) {
        assert( ! editing );
        jAccountFormPanel.onSelect((AccountController)accountCtrl);
        selectedAccount = (AccountController)accountCtrl;
        jEditBtn.setEnabled(true);
    }

    public boolean selectionEnabled() {
        return ! editing;
    }

}
