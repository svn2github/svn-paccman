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

package org.paccman.ui.schedules;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.paccman.controller.AccountController;
import org.paccman.controller.CategoryBaseController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PayeeController;
import org.paccman.controller.PaymentMethodController;
import org.paccman.controller.SimplePaymentController;
import org.paccman.controller.SplitPaymentController;
import org.paccman.controller.TransactionBaseController;
import org.paccman.controller.TransferController;
import org.paccman.paccman.CategoryBase;
import org.paccman.paccman.SplitTransaction;
import org.paccman.paccman.TransactionBase;
import org.paccman.ui.main.Main;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;
import org.paccman.ui.selector.ControllerSelectionListener;
import org.paccman.ui.transactions.AccountSelectorComboModel;
import org.paccman.ui.transactions.CategorySelectorComboModel;
import org.paccman.ui.transactions.PayeeSelectorComboModel;
import org.paccman.ui.transactions.PaymentMethodSelectorComboModel;
import org.paccman.ui.transactions.TransactionFormTab;
import org.paccman.ui.transactions.split.SplitForm;

/**
 *
 * @author  joao
 */
public class TransactionFormPanel extends PaccmanForm implements ItemListener, ControllerSelectionListener {
    
    SplitForm splitForm;
    
    void setSplitForm(SplitForm splitForm) {
        this.splitForm = splitForm;
//:TODO:        splitForm.setTransactionFormPanel(this);
    }
    
    enum TransactionType { NONE, SIMPLE_PAYMENT, SPLIT_PAYMENT, TRANSFER };
    
    TransactionType transactionType = TransactionType.NONE;
    
    private TransactionType getTransactionType(TransactionBaseController tbc) {
        if (tbc instanceof TransferController) {
            return TransactionType.TRANSFER;
        } else if (tbc instanceof SplitPaymentController) {
            return TransactionType.SPLIT_PAYMENT;
        } else {
            assert tbc instanceof SimplePaymentController;
            return TransactionType.SIMPLE_PAYMENT;
        }
    }
    private boolean isTransferForm() {
        return transactionType == TransactionType.TRANSFER;
    }
    
    private boolean isSplitPaymentForm() {
        return transactionType == TransactionType.SPLIT_PAYMENT;
    }
    
    private boolean isSimplePaymentForm() {
        return transactionType == TransactionType.SIMPLE_PAYMENT;
    }
    
    /** Creates new form TransactionFormPanel */
    public TransactionFormPanel() {
        initComponents();
        categorySelectorCmb.addItemListener(this);
        showEmptyForm();
    }
    
    private void setForm(org.paccman.controller.SimplePaymentController payment) {
        CategoryBase category = payment.getPayment().getCategory();
        categorySelectorCmb.setSelectedItem(category != null ? ControllerManager.getController(category) : CategorySelectorComboModel.getNullElement());
        payeeCmb.setSelectedItem(ControllerManager.getController(payment.getPayment().getPayee()));
        paymentMethodCmb.setSelectedItem(ControllerManager.getController(payment.getPayment().getPaymentMethod()));
        toAccountCmb.setSelectedItem(null);
        
    }
    
    ArrayList<SplitTransaction> inputSplitTransactions = new ArrayList<SplitTransaction>();
    
    private void setForm(org.paccman.controller.SplitPaymentController payment) {
        categorySelectorCmb.setSelectedItem(categorySelectorCmb.getModel().getSplitPaymentElement());
        paymentMethodCmb.setSelectedItem(ControllerManager.getController(payment.getPayment().getPaymentMethod()));
        payeeCmb.setSelectedItem(ControllerManager.getController(payment.getPayment().getPayee()));
        toAccountCmb.setSelectedItem(null);
        
        // Copy payment split transactions to inputSplitTransactions
        inputSplitTransactions.clear();
        for (SplitTransaction st: payment.getPayment().getSplitTransactions()) {
            SplitTransaction lst = new SplitTransaction();
            lst.setCategory(st.getCategory());
            lst.setNote(st.getNote());
            lst.setAmount(st.getAmount());
            inputSplitTransactions.add(lst);
        }
        splitForm.setData(payment.getPayment().getAmount(), inputSplitTransactions);
    }
    
    private void setForm(org.paccman.controller.TransferController transfer) {
        categorySelectorCmb.setSelectedItem(categorySelectorCmb.getModel().getTransferElement());
        toAccountCmb.setSelectedItem(ControllerManager.getController(transfer.getTransfer().getToFromAccount()));
        payeeCmb.setSelectedItem(null);
        paymentMethodCmb.setSelectedItem(null);
    }
    
    public void setEmptyForm() {
        clearForm();
    }
    
    public void setForm(org.paccman.controller.Controller controller) {
        TransactionBase tb = ((TransactionBaseController)controller).getTransactionBase();
        amountEdt.setValue(tb.getAmount());
        labelEdt.setText(tb.getLabel());
        noteEdt.setText(tb.getNote());
        if (controller instanceof SimplePaymentController) {
            setForm((SimplePaymentController)controller);
        } else if (controller instanceof SplitPaymentController) {
            setForm((SplitPaymentController)controller);
        } else if (controller instanceof TransferController) {
            setForm((TransferController)controller);
        } else {
            setEmptyForm();
        }
    }
    
    public void getForm(org.paccman.controller.Controller controller) throws BadInputException {
        
        // Check category has been selected
        if (categorySelectorCmb.getSelectedItem() == null) {
            throw new BadInputException("You must choose a category", categorySelectorCmb);
        }
        
        // Amount
        try {
            amountEdt.commitEdit();
        } catch (ParseException ex) {
            throw new BadInputException("Bad amount value", amountEdt);
        }
        BigDecimal amount = (BigDecimal)amountEdt.getValue();
        
        // Label and note
        String label = labelEdt.getText();
        String note = noteEdt.getText();
        
        CategoryBaseController category = (categorySelectorCmb.getSelectedItem() instanceof CategoryBaseController) ?
            (CategoryBaseController) categorySelectorCmb.getSelectedItem() : null;
        
        PayeeController payee = (PayeeController)payeeCmb.getSelectedItem();
        PaymentMethodController paymentMethod = (PaymentMethodController)paymentMethodCmb.getSelectedItem();
        AccountController toAccount = (AccountController) toAccountCmb.getSelectedItem();
        
        // Extra data validation according to the type of transaction
        switch (transactionType) {
            
            case SIMPLE_PAYMENT:
                // nothing to validate
                break;
                
            case SPLIT_PAYMENT:
                // nothing to validate
                break;
                
            case TRANSFER:
                if (toAccount == null) {
                    throw new BadInputException("Select an account", toAccountCmb);
                }
                break;
        }
        
        //
        // Update data
        //
        
        // TransactionBase data
        TransactionBaseController tbc = (TransactionBaseController)controller;
        tbc.getTransactionBase().setLabel(label);
        tbc.getTransactionBase().setNote(note);
        
        // Specific transaction data
        switch (transactionType) {
            
            // -----------------------------------------------------------------
            case SIMPLE_PAYMENT:
            {
                SimplePaymentController spc = (SimplePaymentController)controller;
                if (payee != null) {
                    spc.getPayment().setPayee(payee.getPayee());
                }
                if (paymentMethod != null) {
                    spc.getPayment().setPaymentMethod(paymentMethod.getPaymentMethod());
                }
                spc.getPayment().setCategory(category.getCategoryBase());
                spc.getTransactionBase().setAmount(amount);
            }
            break;
            
            // -----------------------------------------------------------------
            case SPLIT_PAYMENT:
            {
                SplitPaymentController spc = (SplitPaymentController)controller;
                if (payee != null) {
                    spc.getPayment().setPayee(payee.getPayee());
                }
                if (paymentMethod != null) {
                    spc.getPayment().setPaymentMethod(paymentMethod.getPaymentMethod());
                }
                spc.getTransactionBase().setAmount(amount);
                
                
                spc.getPayment().getSplitTransactions().clear();
                for (SplitTransaction st: inputSplitTransactions) {
                    spc.getPayment().getSplitTransactions().add(st);
                }
            }
            break;
            
            // -----------------------------------------------------------------
            case TRANSFER:
            {
                TransferController tc = (TransferController)controller;
                tc.getTransfer().setToFromAccount(toAccount.getAccount());
                tbc.getTransactionBase().setAmount(amount);
            }
            break;
            
            // -----------------------------------------------------------------
            default:
                assert false: "Invalid transaction type";
                
        }
        
    }
    
    public void setEditMode(boolean editing) {

        this.editing = editing;
        amountEdt.setEnabled(editing);
        noteEdt.setEnabled(editing);
        categorySelectorCmb.setEnabled(editing);
        labelEdt.setEnabled(editing);
        
        toAccountCmb.setEnabled(editing && isTransferForm());
        payeeCmb.setEnabled(editing && (isSplitPaymentForm() || isSimplePaymentForm()));
        paymentMethodCmb.setEnabled(editing && (isSplitPaymentForm() || isSimplePaymentForm()));
        splitBtn.setEnabled(isSplitPaymentForm());
        
    }
    
    public void registerToDocumentCtrl() {
        Main.getDocumentCtrl().registerView((CategorySelectorComboModel)categorySelectorCmb.getModel());
        Main.getDocumentCtrl().registerView((AccountSelectorComboModel)toAccountCmb.getModel());
        Main.getDocumentCtrl().registerView((PayeeSelectorComboModel)payeeCmb.getModel());
        Main.getDocumentCtrl().registerView((PaymentMethodSelectorComboModel)paymentMethodCmb.getModel());
    }
    
    public org.paccman.controller.Controller getNewController() {
        switch (transactionType) {
            case TRANSFER      : return new TransferController()     ;
            case SPLIT_PAYMENT : return new SplitPaymentController() ;
            case SIMPLE_PAYMENT: return new SimplePaymentController();
            default: return null;
        }
    }
    
    public void clearForm() {

        amountEdt.setValue(null);
        labelEdt.setText("");
        noteEdt.setText("");
        categorySelectorCmb.setSelectedItem(CategorySelectorComboModel.getNullElement());
        paymentMethodCmb.setSelectedItem(null);
        payeeCmb.setSelectedItem(null);
        
        if (splitForm != null) { //:TODO:remove this test
            splitForm.clearForm();
        } //:TODO:
    }
    
    /*
     * showXXXXForm Called when the "Category" selection changed
     * (only when editing)
     */
    
    private void showSplitPaymentForm() {
        splitBtn.setEnabled(editing);
        toAccountCmb.setEnabled(false);
        payeeCmb.setEnabled(editing);
        paymentMethodCmb.setEnabled(editing);
        
        transactionType = TransactionType.SPLIT_PAYMENT;
        
    }
    
    private void showSimplePaymentForm() {
        splitBtn.setEnabled(false);
        toAccountCmb.setEnabled(false);
        payeeCmb.setEnabled(editing);
        paymentMethodCmb.setEnabled(editing);
        
        transactionType = TransactionType.SIMPLE_PAYMENT;
    }
    
    private void showTransferPaymentForm() {
        splitBtn.setEnabled(false);
        toAccountCmb.setEnabled(editing);
        payeeCmb.setEnabled(false);
        paymentMethodCmb.setEnabled(false);
        
        transactionType = TransactionType.TRANSFER;
    }
    
    private void showEmptyForm() {
        splitBtn.setEnabled(false);
        toAccountCmb.setEnabled(false);
        payeeCmb.setEnabled(false);
        paymentMethodCmb.setEnabled(false);
        
        transactionType = TransactionType.SPLIT_PAYMENT;
    }
    
    private void showForm(Object selectedCategoryItem) {
        CategorySelectorComboModel model = (CategorySelectorComboModel)categorySelectorCmb.getModel();
        if (selectedCategoryItem == model.getTransferElement()) {
            showTransferPaymentForm();
        } else if (selectedCategoryItem == model.getSplitPaymentElement()) {
            showSplitPaymentForm();
        } else if (selectedCategoryItem == model.getNullElement()) {
            showEmptyForm();
            transactionType = TransactionType.NONE;
        } else {
            showSimplePaymentForm();
        }
    }
    
    public void itemStateChanged(java.awt.event.ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            showForm(e.getItem());
        }
    }
    
    public void controllerSelected(org.paccman.controller.Controller ctrl) {
        ((AccountSelectorComboModel)toAccountCmb.getModel()).setExcludedAccount((AccountController)ctrl);
    }
    
    public boolean selectionEnabled() {
        return true; //:TODO:
    }
    
    public void onCancel() {
        
        super.onCancel();
    }
    
    public org.paccman.controller.Controller onValidate() {
        
        org.paccman.controller.Controller retVal;
        
        if (state == org.paccman.ui.form.PaccmanForm.State.EDITING_EXISTING) {
            if (getTransactionType((TransactionBaseController)controller) != transactionType) {
                controller = getNewController();//:TODO:will generate a new trsnaction. dont forget to delete the old one
            }
            retVal = super.onValidate();
        } else {
            retVal = super.onValidate();
        }
        
        return retVal;
    }
    
    public void finishSplitForm() {
        splitForm.getData(inputSplitTransactions);
    }
    
    public void cancelSplitForm() {
        setSplitFormData();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        labelLbl = new javax.swing.JLabel();
        noteLbl = new javax.swing.JLabel();
        noteScrollPane = new javax.swing.JScrollPane();
        noteEdt = new javax.swing.JTextArea();
        amountLbl = new javax.swing.JLabel();
        categoryLbl = new javax.swing.JLabel();
        categorySelectorCmb = new org.paccman.ui.transactions.CategorySelectorComboBox();
        amountEdt = new org.paccman.ui.common.AmountTextField();
        splitBtn = new javax.swing.JButton();
        toAccountLbl = new javax.swing.JLabel();
        payeeLbl = new javax.swing.JLabel();
        toAccountCmb = new org.paccman.ui.transactions.AccountSelectorComboBox();
        payeeCmb = new org.paccman.ui.transactions.PayeeSelectorComboBox();
        paymentMethodLbl = new javax.swing.JLabel();
        paymentMethodCmb = new org.paccman.ui.transactions.PaymentMethodSelectorComboBox();
        labelEdt = new javax.swing.JTextField();

        labelLbl.setText("Label");

        noteLbl.setText("Note");

        noteEdt.setColumns(20);
        noteEdt.setRows(5);
        noteEdt.setEnabled(false);
        noteScrollPane.setViewportView(noteEdt);

        amountLbl.setText("Amount");

        categoryLbl.setText("Category");

        categorySelectorCmb.setEnabled(false);

        amountEdt.setEnabled(false);

        splitBtn.setText("Split");
        splitBtn.setEnabled(false);
        splitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                splitBtnActionPerformed(evt);
            }
        });

        toAccountLbl.setText("Account");

        payeeLbl.setText("Payee");

        toAccountCmb.setEnabled(false);

        payeeCmb.setEnabled(false);

        paymentMethodLbl.setText("Payment");

        paymentMethodCmb.setEnabled(false);

        labelEdt.setEnabled(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, categoryLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, labelLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, noteLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, paymentMethodLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, toAccountLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(categorySelectorCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(amountLbl)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(amountEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, noteScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, labelEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(toAccountCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                            .add(paymentMethodCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(payeeLbl)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(payeeCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                            .add(splitBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(amountEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(categorySelectorCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(amountLbl))
                    .add(categoryLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelLbl)
                    .add(labelEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(noteScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(noteLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(payeeCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paymentMethodCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paymentMethodLbl)
                    .add(payeeLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(toAccountCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(splitBtn)
                    .add(toAccountLbl))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(new java.awt.Component[] {amountEdt, categorySelectorCmb, labelEdt, payeeCmb, paymentMethodCmb, splitBtn, toAccountCmb}, org.jdesktop.layout.GroupLayout.VERTICAL);

    }// </editor-fold>//GEN-END:initComponents
    
    private boolean setSplitFormData() {
        try {
            amountEdt.commitEdit();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ":TODO: error amount", "ERROR", JOptionPane.OK_OPTION);
            return false;
        }
        splitForm.setData(amountEdt.getValue(), inputSplitTransactions);
        return true;
    }
    
    private void splitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_splitBtnActionPerformed
        if (!setSplitFormData()) {
            return;
        }
        TransactionFormTab tft = Main.getMain().getTransactionFormTab();
        tft.showSplitCard();
    }//GEN-LAST:event_splitBtnActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.paccman.ui.common.AmountTextField amountEdt;
    private javax.swing.JLabel amountLbl;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel categoryLbl;
    private org.paccman.ui.transactions.CategorySelectorComboBox categorySelectorCmb;
    private javax.swing.JTextField labelEdt;
    private javax.swing.JLabel labelLbl;
    private javax.swing.JTextArea noteEdt;
    private javax.swing.JLabel noteLbl;
    private javax.swing.JScrollPane noteScrollPane;
    private org.paccman.ui.transactions.PayeeSelectorComboBox payeeCmb;
    private javax.swing.JLabel payeeLbl;
    private org.paccman.ui.transactions.PaymentMethodSelectorComboBox paymentMethodCmb;
    private javax.swing.JLabel paymentMethodLbl;
    private javax.swing.JButton splitBtn;
    private org.paccman.ui.transactions.AccountSelectorComboBox toAccountCmb;
    private javax.swing.JLabel toAccountLbl;
    // End of variables declaration//GEN-END:variables
    
}