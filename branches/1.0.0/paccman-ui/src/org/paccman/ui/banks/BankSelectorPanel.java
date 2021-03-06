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

package org.paccman.ui.banks;

import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;
import org.paccman.controller.BankController;
import org.paccman.controller.Controller;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Bank;
import org.paccman.ui.selector.ControllerSelectionButton;
import org.paccman.ui.selector.ControllerSelectionListener;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author  joao
 */
public class BankSelectorPanel extends javax.swing.JPanel implements PaccmanView, ActionListener {
    
    BankController  bankCtrl ; // label of selected bank
    
    Vector<ControllerSelectionButton> selButtons = new Vector<ControllerSelectionButton>();
    
    Vector<ControllerSelectionListener> bankSelectionListener = new Vector<ControllerSelectionListener>();
    
    public void setBankCtrl(BankController bankCtrl) {
        if (this.bankCtrl == bankCtrl) {
            return;
        }
        if (this.bankCtrl != null) {
            this.bankCtrl.unregisterView(this);
        }
        this.bankCtrl = bankCtrl;
        bankCtrl.registerView(this);
        selectedBankLbl.setText(bankCtrl.getBank().getName());
    }
    
    public void addListener(ControllerSelectionListener listener) {
        bankSelectionListener.add(listener);
    }
    
    /**
     * Creates new form BankSelectorPanel
     */
    public BankSelectorPanel() {
        initComponents();
    }
    
    public void registerToDocumentCtrl() {
        getDocumentController().registerView(this);
    }
    
    public void onChange(Controller controller) {
        if (controller == getDocumentController()) {
            
            // Remove all buttons
            for (ControllerSelectionButton acb: selButtons) {
                acb.getController().unregisterView(acb);
                bankButtonsPanel.remove(acb);
            }
            
            // Clear button list
            selButtons.clear();
            
            // Add the new buttons
            Collection<Bank> banks = getDocumentController().getDocument().getBanks();
            for (Bank bank: banks) {
                ControllerSelectionButton asb = new ControllerSelectionButton((BankController)ControllerManager.getController(bank));
                asb.addActionListener(this);
                bankButtonsPanel.add(asb);
                selButtons.add(asb);
            }
            
            validate();
        } else if (controller == bankCtrl) {
            selectedBankLbl.setText(bankCtrl.getBank().getName());
        }
        
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        ControllerSelectionButton asb = (ControllerSelectionButton)e.getSource();
        if (e != null) {
            if (selectBank((BankController)asb.getController())) {
                setBankCtrl((BankController)asb.getController());
            }
        }
    }
    
    private boolean selectionEnabled() {
        // Check if all listeners allow the selection
        for (ControllerSelectionListener asl: bankSelectionListener) {
            if (! asl.selectionEnabled()) {
                // if not return
                return false;
            }
        }
        return true;
    }
    
    public boolean selectBank(BankController bank) {
        if (selectionEnabled()) {
            // Notify all seletino listeners
            for (ControllerSelectionListener asl: bankSelectionListener) {
                asl.controllerSelected(bank);
            }
            setBankCtrl(bank);
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
        bankButtonsPanel = new javax.swing.JPanel();
        selectedBankLbl = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        bankButtonsPanel.setLayout(new java.awt.GridLayout(0, 1));

        selectedBankLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedBankLbl.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null), new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        bankButtonsPanel.add(selectedBankLbl);

        add(bankButtonsPanel, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bankButtonsPanel;
    private javax.swing.JLabel selectedBankLbl;
    // End of variables declaration//GEN-END:variables
    
}
