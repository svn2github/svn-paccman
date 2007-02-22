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

package org.paccman.ui.accountselector;

import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;
import org.paccman.controller.AccountController;
import org.paccman.controller.Controller;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Account;
import org.paccman.ui.main.Main;
import org.paccman.ui.selector.ControllerSelectionButton;
import org.paccman.ui.selector.ControllerSelectionListener;

/**
 *
 * @author  joao
 */
public class AccountSelectorPanel extends javax.swing.JPanel implements PaccmanView, ActionListener {
    
    AccountController  accountCtrl ; // label of selected account
    
    Vector<ControllerSelectionButton> selButtons = new Vector<ControllerSelectionButton>();
    
    Vector<ControllerSelectionListener> accountSelectionListener = new Vector<ControllerSelectionListener>();
    
    public void setAccountCtrl(AccountController accountCtrl) {
        if (this.accountCtrl == accountCtrl) {
            return;
        }
        if (this.accountCtrl != null) {
            this.accountCtrl.unregisterView(this);
        }
        this.accountCtrl = accountCtrl;
        accountCtrl.registerView(this);
        selectedAccountLbl.setText(accountCtrl.getAccount().getName());
    }
    
    public void addListener(ControllerSelectionListener listener) {
        accountSelectionListener.add(listener);
    }
    
    /** Creates new form AccountSelectorPanel */
    public AccountSelectorPanel() {
        initComponents();
    }
    
    public void registerToDocumentCtrl() {
        Main.getDocumentCtrl().registerView(this);
    }
    
    public void onChange(Controller controller) {
        if (controller == Main.getDocumentCtrl()) {
            
            // Remove all buttons
            for (ControllerSelectionButton acb: selButtons) {
                acb.getController().unregisterView(acb);
                accountButtonsPanel.remove(acb);
            }
            
            // Clear button list
            selButtons.clear();
            
            // Add the new buttons
            Collection<Account> accounts = Main.getDocumentCtrl().getDocument().getAccounts();
            for (Account acc: accounts) {
                ControllerSelectionButton asb = new ControllerSelectionButton((AccountController)ControllerManager.getController(acc));
                asb.addActionListener(this);
                accountButtonsPanel.add(asb);
                selButtons.add(asb);
            }
            
            validate();
        } else if (controller == accountCtrl) {
            selectedAccountLbl.setText(accountCtrl.getAccount().getName());
        }
        
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        ControllerSelectionButton asb = (ControllerSelectionButton)e.getSource();
        if (e != null) {
            if (selectAccount((AccountController)asb.getController())) {
                setAccountCtrl((AccountController)asb.getController());
            }
        }
    }
    
    private boolean selectionEnabled() {
        // Check if all listeners allow the selection
        for (ControllerSelectionListener asl: accountSelectionListener) {
            if (! asl.selectionEnabled()) {
                // if not return
                return false;
            }
        }
        return true;
    }
    
    public boolean selectAccount(AccountController account) {
        if (selectionEnabled()) {
            // Notify all seletino listeners
            for (ControllerSelectionListener asl: accountSelectionListener) {
                asl.controllerSelected(account);
            }
            setAccountCtrl(account);
            return true;
        }
        else {
            return false;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        accountButtonsPanel = new javax.swing.JPanel();
        selectedAccountLbl = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        accountButtonsPanel.setLayout(new java.awt.GridLayout(0, 1));

        selectedAccountLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedAccountLbl.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null), new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        accountButtonsPanel.add(selectedAccountLbl);

        add(accountButtonsPanel, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accountButtonsPanel;
    private javax.swing.JLabel selectedAccountLbl;
    // End of variables declaration//GEN-END:variables
    
}
