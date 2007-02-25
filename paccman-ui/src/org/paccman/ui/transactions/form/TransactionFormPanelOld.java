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

package org.paccman.ui.transactions.form;

import java.awt.event.ItemListener;
import org.paccman.controller.Controller;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;
import org.paccman.ui.selector.ControllerSelectionListener;

/**
 *
 * @author  joao
 */
public class TransactionFormPanelOld extends PaccmanForm {
    
    /** Creates new form TransactionFormPanel */
    public TransactionFormPanelOld() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        categoryLbl = new javax.swing.JLabel();
        categorySelectorCmb = new org.paccman.ui.transactions.form.CategorySelectorComboBox();
        amountLbl = new javax.swing.JLabel();
        amountEdt = new org.paccman.ui.common.AmountTextField();
        dateLbl = new javax.swing.JLabel();
        dateEdt = new org.paccman.ui.common.MyDateChooser();
        valueDateLbl = new javax.swing.JLabel();
        valueDateChooser = new org.paccman.ui.common.MyDateChooser();
        labelLbl = new javax.swing.JLabel();
        labelEdt = new javax.swing.JTextField();
        noteLbl = new javax.swing.JLabel();
        noteScrollPane = new javax.swing.JScrollPane();
        noteEdt = new javax.swing.JTextArea();
        payeeLbl = new javax.swing.JLabel();
        payeeCmb = new org.paccman.ui.transactions.form.PayeeSelectorComboBox();
        paymentLbl = new javax.swing.JLabel();
        payementCmb = new org.paccman.ui.transactions.form.PaymentMethodSelectorComboBox();
        splitBtn = new javax.swing.JButton();
        toFromAccountLbl = new javax.swing.JLabel();
        toFromAccountCmb = new org.paccman.ui.transactions.form.AccountSelectorComboBox();

        categoryLbl.setText("Category");

        amountLbl.setText("Amount");

        dateLbl.setText("Date");

        valueDateLbl.setText("Value date");

        labelLbl.setText("Label");

        noteLbl.setText("Note");

        noteEdt.setColumns(20);
        noteEdt.setRows(5);
        noteScrollPane.setViewportView(noteEdt);

        payeeLbl.setText("Payee");

        payeeCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        paymentLbl.setText("Payment Method");

        splitBtn.setText("Split");

        toFromAccountLbl.setText("To/From account");

        toFromAccountCmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, categoryLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, dateLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, labelLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, noteLbl)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, payeeLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, labelEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(splitBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .add(dateEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .add(categorySelectorCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                            .add(payeeCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, amountLbl)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, valueDateLbl)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, paymentLbl)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, toFromAccountLbl))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(valueDateChooser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .add(amountEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .add(payementCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .add(toFromAccountCmb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)))
                    .add(noteScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(categorySelectorCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(amountLbl)
                    .add(amountEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(dateLbl)
                    .add(dateEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(valueDateLbl)
                    .add(valueDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(labelLbl)
                    .add(labelEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(noteLbl)
                    .add(noteScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(payeeCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(payeeLbl)
                    .add(paymentLbl)
                    .add(payementCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(splitBtn)
                    .add(toFromAccountLbl)
                    .add(toFromAccountCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.paccman.ui.common.AmountTextField amountEdt;
    private javax.swing.JLabel amountLbl;
    private javax.swing.JLabel categoryLbl;
    private org.paccman.ui.transactions.form.CategorySelectorComboBox categorySelectorCmb;
    private org.paccman.ui.common.MyDateChooser dateEdt;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JTextField labelEdt;
    private javax.swing.JLabel labelLbl;
    private javax.swing.JTextArea noteEdt;
    private javax.swing.JLabel noteLbl;
    private javax.swing.JScrollPane noteScrollPane;
    private org.paccman.ui.transactions.form.PayeeSelectorComboBox payeeCmb;
    private javax.swing.JLabel payeeLbl;
    private org.paccman.ui.transactions.form.PaymentMethodSelectorComboBox payementCmb;
    private javax.swing.JLabel paymentLbl;
    private javax.swing.JButton splitBtn;
    private org.paccman.ui.transactions.form.AccountSelectorComboBox toFromAccountCmb;
    private javax.swing.JLabel toFromAccountLbl;
    private org.paccman.ui.common.MyDateChooser valueDateChooser;
    private javax.swing.JLabel valueDateLbl;
    // End of variables declaration//GEN-END:variables
 
    /**
     * Holds value of property splitFormPanel.
     */
    private SplitFormPanel splitFormPanel;

    /**
     * Setter for property transactionFormPanel.
     * @param transactionFormPanel New value of property transactionFormPanel.
     */
    public void setSplitFormPanel(SplitFormPanel splitFormPanel) {
        this.splitFormPanel = splitFormPanel;
    }

    public void setForm(Controller controller) {
    }

    public void getForm(Controller controller) throws BadInputException {
    }

    public void setEditMode(boolean editing) {
    }

    public void registerToDocumentCtrl() {
    }

    public Controller getNewController() {
        return null; //:TODO:
    }

    public void clearForm() {
    }
    
}