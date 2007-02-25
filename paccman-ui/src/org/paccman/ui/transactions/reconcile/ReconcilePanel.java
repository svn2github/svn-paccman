/*
 * ReconcilePanel.java
 *
 * Created on 14 novembre 2005, 14:54
 */

package org.paccman.ui.transactions.reconcile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author  jfer
 */
public class ReconcilePanel extends javax.swing.JPanel {
    
    ReconcileContext context = new ReconcileContext();
    
    private void updateForm() {
        newBalanceEdt.setValue(context.newBalance);
        differencePrevNewEdt.setValue(context.diffBalance);
        diffDifferenceMarkedEdt.setValue(context.diffMarkedBalance);
        markedAmountEdt.setValue(context.markedAmount);
    }
    
    private void updateNewBalance(BigDecimal amount) {
        context.newBalance = amount;
        
        context.diffBalance = context.newBalance.subtract(context.prevBalance);
        context.diffMarkedBalance = context.diffBalance.subtract(context.markedAmount);
        
        updateForm();
    }
    
    private void updateMarkedBalance(BigDecimal amount) {
        context.markedAmount = amount;
        
        context.diffMarkedBalance = context.diffBalance.subtract(context.markedAmount);

        updateForm();
    }
    
    private void updateMarkedAndNewBalance(BigDecimal markedAmount, BigDecimal newBalance) {
        context.newBalance   = newBalance;
        context.markedAmount = markedAmount;
        
        context.diffBalance = context.prevBalance.subtract(context.newBalance);
        context.diffMarkedBalance = context.markedAmount.subtract(context.diffBalance);

        updateForm();
    }
    
    /** Creates new form ReconcilePanel */
    public ReconcilePanel() {
        initComponents();
    }
    
    public void setData(Calendar prevReconciliationDate, BigDecimal prevReconcilationBalance,
            Calendar pendingReconciliationDate, BigDecimal pendingReconciliationBalance,
            BigDecimal markedAmount) {
        
        // Previous reconciliation
        context.prevBalance = prevReconcilationBalance;
        context.newDate = pendingReconciliationDate;
        prevBalanceEdt.setValue(prevReconcilationBalance);
        prevDateChooser.setDate(prevReconciliationDate.getTime());
        newDateChooser.setDate(pendingReconciliationDate.getTime());
        
        // Pending or new reconciliation
        updateMarkedAndNewBalance(markedAmount, pendingReconciliationBalance);

    }
    
    public void markTransactionAmount(BigDecimal amount) {
        updateMarkedBalance(context.markedAmount.add(amount));
    }
    
    public void unmarkTransactionAmount(BigDecimal amount) {
        updateMarkedBalance(context.markedAmount.subtract(amount));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        previousPanel = new javax.swing.JPanel();
        prevDateChooser = new org.paccman.ui.common.MyDateChooser();
        prevBalanceEdt = new org.paccman.ui.common.AmountTextField();
        newPanel = new javax.swing.JPanel();
        newDateChooser = new org.paccman.ui.common.MyDateChooser();
        newBalanceEdt = new org.paccman.ui.common.AmountTextField();
        differencePanel = new javax.swing.JPanel();
        differencePrevNewEdt = new org.paccman.ui.common.AmountTextField();
        markedAmountEdt = new org.paccman.ui.common.AmountTextField();
        diffDifferenceMarkedEdt = new org.paccman.ui.common.AmountTextField();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        previousPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Previous"));
        prevDateChooser.setEnabled(false);

        prevBalanceEdt.setEnabled(false);

        org.jdesktop.layout.GroupLayout previousPanelLayout = new org.jdesktop.layout.GroupLayout(previousPanel);
        previousPanel.setLayout(previousPanelLayout);
        previousPanelLayout.setHorizontalGroup(
            previousPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, previousPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(previousPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, prevDateChooser, 0, 251, Short.MAX_VALUE)
                    .add(prevBalanceEdt, 0, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        previousPanelLayout.setVerticalGroup(
            previousPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, previousPanelLayout.createSequentialGroup()
                .add(prevDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(prevBalanceEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(previousPanel);

        newPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("New"));

        newBalanceEdt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newBalanceEdtFocusLost(evt);
            }
        });

        org.jdesktop.layout.GroupLayout newPanelLayout = new org.jdesktop.layout.GroupLayout(newPanel);
        newPanel.setLayout(newPanelLayout);
        newPanelLayout.setHorizontalGroup(
            newPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, newPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(newPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, newBalanceEdt, 0, 251, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, newDateChooser, 0, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        newPanelLayout.setVerticalGroup(
            newPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, newPanelLayout.createSequentialGroup()
                .add(newDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(newBalanceEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(newPanel);

        differencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Difference"));
        differencePrevNewEdt.setEnabled(false);

        markedAmountEdt.setEnabled(false);

        diffDifferenceMarkedEdt.setEnabled(false);

        org.jdesktop.layout.GroupLayout differencePanelLayout = new org.jdesktop.layout.GroupLayout(differencePanel);
        differencePanel.setLayout(differencePanelLayout);
        differencePanelLayout.setHorizontalGroup(
            differencePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, differencePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(differencePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, markedAmountEdt, 0, 251, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, differencePrevNewEdt, 0, 251, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, diffDifferenceMarkedEdt, 0, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        differencePanelLayout.setVerticalGroup(
            differencePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, differencePanelLayout.createSequentialGroup()
                .add(differencePrevNewEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(markedAmountEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(diffDifferenceMarkedEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(differencePanel);

    }// </editor-fold>//GEN-END:initComponents
    
    private void newBalanceEdtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newBalanceEdtFocusLost
        try {
            newBalanceEdt.commitEdit();
        } catch (ParseException ex) {
            return;
        }
        updateNewBalance(newBalanceEdt.getValue());
    }//GEN-LAST:event_newBalanceEdtFocusLost
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.paccman.ui.common.AmountTextField diffDifferenceMarkedEdt;
    private javax.swing.JPanel differencePanel;
    private org.paccman.ui.common.AmountTextField differencePrevNewEdt;
    private org.paccman.ui.common.AmountTextField markedAmountEdt;
    private org.paccman.ui.common.AmountTextField newBalanceEdt;
    private org.paccman.ui.common.MyDateChooser newDateChooser;
    private javax.swing.JPanel newPanel;
    private org.paccman.ui.common.AmountTextField prevBalanceEdt;
    private org.paccman.ui.common.MyDateChooser prevDateChooser;
    private javax.swing.JPanel previousPanel;
    // End of variables declaration//GEN-END:variables
    
    public ReconcileContext getData() {
        context.newDate = newDateChooser.getCalendarDate();
        return context;
    }
}