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

import java.math.BigDecimal;
import java.text.ParseException;
import javax.swing.JOptionPane;
import org.paccman.controller.AccountController;
import org.paccman.controller.BankController;
import org.paccman.controller.Controller;
import org.paccman.controller.ControllerManager;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.ui.main.Main;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;

/**
 *
 * @author  joao
 */
public class AccountFormPanel extends PaccmanForm {
    
    /** Creates new form AccountFormPanel */
    public AccountFormPanel() {
        initComponents();
        setEditMode(false);
    }
    
    public void setForm(org.paccman.controller.Controller controller) {
        Account account = ((AccountController)controller).getAccount();
        accountNameEdt.setText(account.getName());
        jBankCb.setSelectedItem(ControllerManager.getController(account.getBank()));
        initialBalanceEdt.setValue(account.getInitialBalance());
        currentBalanceEdt.setValue(account.getCurrentBalance());
        holderNameEdt.setText(account.getHolderName());
        holderAddressEdt.setText(account.getHolderAddress());
        accountNumberEdt.setText(account.getAccountNumber());
        accountKeyEdt.setText(account.getAccountNumberKey());
    }
    
    public void setEditMode(boolean editing) {
        accountNameEdt.setEnabled(editing);
        jBankCb.setEnabled(editing);
        initialBalanceEdt.setEnabled(editing);
        holderNameEdt.setEnabled(editing);
        holderAddressEdt.setEnabled(editing);
        accountNumberEdt.setEnabled(editing);
        accountKeyEdt.setEnabled(editing);
    }
    
    public void registerToDocumentCtrl() {
        jBankCb.setDocumentController(Main.getDocumentCtrl());
    }
    
    public void clearForm() {
        accountNameEdt.setText(  "");
        jBankCb.setSelectedItem(null);
        initialBalanceEdt.setValue(null);
        currentBalanceEdt.setValue(null);
        holderNameEdt.setText("");
        holderAddressEdt.setText("");
        accountNumberEdt.setText("");
        accountKeyEdt.setText("");
    }
    
    public void getForm(org.paccman.controller.Controller controller) throws BadInputException {
        AccountController accountCtrl = (AccountController)controller;
        
        String name = accountNameEdt.getText();
        
        // In case of a new account, check that the name is not already used for another account
        if (editingNew ) {
            if (Main.getDocumentCtrl().getDocument().getAccount(name) != null) {
                throw new BadInputException("An account with the same name already exists", accountNameEdt);
            }
        } else {
            // If the name has changed, checks that is not already used
            if (! name.equals(accountCtrl.getAccount().getName())) {
                if (Main.getDocumentCtrl().getDocument().getAccount(name) != null) {
                    throw new BadInputException("An account with the same name already exists", accountNameEdt);
                }
            }
        }
        
        try {
            initialBalanceEdt.commitEdit();
        } catch (ParseException exception) {
            throw new BadInputException("Enter a valid amount for the initial balance.", initialBalanceEdt);
        }
        BigDecimal initialBalance = (BigDecimal)initialBalanceEdt.getValue();
        if (jBankCb.getSelectedItem() == null) {
            throw new BadInputException("You must choose a bank for this account.", jBankCb);
        }
        Bank bank = ((BankController)jBankCb.getSelectedItem()).getBank();
        String holderName = holderNameEdt.getText();
        String holderAddress = holderAddressEdt.getText();
        String accountNumber = accountNumberEdt.getText();
        String accountKey = accountKeyEdt.getText();
        
        accountCtrl.getAccount().setName(name);
        accountCtrl.getAccount().setInitialBalance(initialBalance);
        accountCtrl.getAccount().setBank(bank);
        accountCtrl.getAccount().setHolderName(holderName);
        accountCtrl.getAccount().setHolderAddress(holderAddress);
        accountCtrl.getAccount().setAccountNumber(accountNumber);
        accountCtrl.getAccount().setAccountNumberKey(accountKey);

    }
    
    public org.paccman.controller.Controller getNewController() {
        return new AccountController();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jNameLbl = new javax.swing.JLabel();
        accountNameEdt = new javax.swing.JTextField();
        jBankLbl = new javax.swing.JLabel();
        jBankCb = new org.paccman.ui.common.BankComboBox();
        accountNumberLbl = new javax.swing.JLabel();
        accountNumberEdt = new javax.swing.JTextField();
        accountKeyEdt = new javax.swing.JTextField();
        balancesPanel = new javax.swing.JPanel();
        initialBalanceLbl = new javax.swing.JLabel();
        initialBalanceEdt = new org.paccman.ui.common.AmountTextField();
        currentBalanceLbl = new javax.swing.JLabel();
        currentBalanceEdt = new org.paccman.ui.common.AmountTextField();
        accountHolderPanel = new javax.swing.JPanel();
        holderNameLbl = new javax.swing.JLabel();
        holderNameEdt = new javax.swing.JTextField();
        holderAddressLbl = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        holderAddressEdt = new javax.swing.JTextArea();

        jNameLbl.setText("Name");

        jBankLbl.setText("Bank");

        accountNumberLbl.setText("Account number/Key");

        balancesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Balances"));
        initialBalanceLbl.setText("Initial balance");

        initialBalanceEdt.setEnabled(false);

        currentBalanceLbl.setText("Current balance");

        currentBalanceEdt.setEnabled(false);

        org.jdesktop.layout.GroupLayout balancesPanelLayout = new org.jdesktop.layout.GroupLayout(balancesPanel);
        balancesPanel.setLayout(balancesPanelLayout);
        balancesPanelLayout.setHorizontalGroup(
            balancesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, balancesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(balancesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(currentBalanceLbl)
                    .add(initialBalanceLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(balancesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(initialBalanceEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .add(currentBalanceEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
                .addContainerGap())
        );
        balancesPanelLayout.setVerticalGroup(
            balancesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, balancesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(balancesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(initialBalanceLbl)
                    .add(initialBalanceEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(balancesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(currentBalanceLbl)
                    .add(currentBalanceEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        accountHolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Account holder"));
        holderNameLbl.setText("Name");

        holderNameEdt.setEnabled(false);

        holderAddressLbl.setText("Address");

        jScrollPane1.setEnabled(false);
        holderAddressEdt.setColumns(20);
        holderAddressEdt.setRows(5);
        holderAddressEdt.setEnabled(false);
        jScrollPane1.setViewportView(holderAddressEdt);

        org.jdesktop.layout.GroupLayout accountHolderPanelLayout = new org.jdesktop.layout.GroupLayout(accountHolderPanel);
        accountHolderPanel.setLayout(accountHolderPanelLayout);
        accountHolderPanelLayout.setHorizontalGroup(
            accountHolderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, accountHolderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(accountHolderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(holderAddressLbl)
                    .add(holderNameLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(accountHolderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .add(holderNameEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                .addContainerGap())
        );
        accountHolderPanelLayout.setVerticalGroup(
            accountHolderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, accountHolderPanelLayout.createSequentialGroup()
                .add(accountHolderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(holderNameLbl)
                    .add(holderNameEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(accountHolderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(holderAddressLbl)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jBankLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jNameLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(accountNumberLbl))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jBankCb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                            .add(accountNameEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(accountNumberEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(accountKeyEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(accountHolderPanel)
                    .add(balancesPanel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jNameLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jBankLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(accountNameEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jBankCb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(accountNumberLbl)
                    .add(accountKeyEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(accountNumberEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(accountHolderPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(balancesPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14))
        );

        layout.linkSize(new java.awt.Component[] {accountKeyEdt, accountNameEdt, accountNumberEdt, accountNumberLbl, jBankCb, jBankLbl, jNameLbl}, org.jdesktop.layout.GroupLayout.VERTICAL);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accountHolderPanel;
    private javax.swing.JTextField accountKeyEdt;
    private javax.swing.JTextField accountNameEdt;
    private javax.swing.JTextField accountNumberEdt;
    private javax.swing.JLabel accountNumberLbl;
    private javax.swing.JPanel balancesPanel;
    private org.paccman.ui.common.AmountTextField currentBalanceEdt;
    private javax.swing.JLabel currentBalanceLbl;
    private javax.swing.JTextArea holderAddressEdt;
    private javax.swing.JLabel holderAddressLbl;
    private javax.swing.JTextField holderNameEdt;
    private javax.swing.JLabel holderNameLbl;
    private org.paccman.ui.common.AmountTextField initialBalanceEdt;
    private javax.swing.JLabel initialBalanceLbl;
    private org.paccman.ui.common.BankComboBox jBankCb;
    private javax.swing.JLabel jBankLbl;
    private javax.swing.JLabel jNameLbl;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
