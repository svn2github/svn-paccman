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

import java.awt.event.ItemListener;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.ScheduledTransactionController;
import org.paccman.controller.TransactionBaseController;
import org.paccman.paccman.ScheduledTransaction;
import org.paccman.paccman.TransactionBase;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;

/**
 *
 * @author  jfer
 */
public class ScheduleForm extends PaccmanForm implements ItemListener {
    
    /** Creates new form ScheduleForm */
    public ScheduleForm() {
        initComponents();
    }
    
    private static final String[] schedulePeriodUnits =
    {
        "Day",
                "Week",
                "Month",
                "Year"
    };
    
    ScheduledTransaction.PeriodUnit getPeriodUnit(String schedulePeriodUnit) {
        //:TODO:try and do this better
        if (schedulePeriodUnit == schedulePeriodUnits[0]) return ScheduledTransaction.PeriodUnit.DAY;
        if (schedulePeriodUnit == schedulePeriodUnits[1]) return ScheduledTransaction.PeriodUnit.WEEK;
        if (schedulePeriodUnit == schedulePeriodUnits[2]) return ScheduledTransaction.PeriodUnit.MONTH;
        if (schedulePeriodUnit == schedulePeriodUnits[3]) return ScheduledTransaction.PeriodUnit.YEAR;
        return null;
    }
    
    String getPeriodUnit(ScheduledTransaction.PeriodUnit periodUnid) {
        //:TODO:try and do this better
        if (periodUnid == ScheduledTransaction.PeriodUnit.DAY) return schedulePeriodUnits[0];
        if (periodUnid == ScheduledTransaction.PeriodUnit.WEEK) return schedulePeriodUnits[1];
        if (periodUnid == ScheduledTransaction.PeriodUnit.MONTH) return schedulePeriodUnits[2];
        if (periodUnid == ScheduledTransaction.PeriodUnit.YEAR) return schedulePeriodUnits[3];
        return null;
    }
    
    public void setForm(org.paccman.controller.Controller controller) {
        ScheduledTransactionController stc = (ScheduledTransactionController)controller;

        // Transaction part
        TransactionBaseController      tbc = (TransactionBaseController) ControllerManager.getController(stc.getScheduledTransaction().getTransactionBase());
        ScheduledTransaction scheduledTransaction = stc.getScheduledTransaction();
        transactionFormPanel.setForm(tbc);
        
        // Scheduled part
        automaticChb.setSelected(scheduledTransaction.isAutomatic());
        descriptionEdt.setText(scheduledTransaction.getDescription());
        fixedAmountChb.setSelected(scheduledTransaction.isFixedAmount());
        nextDateChooser.setDate(scheduledTransaction.getNextOccurence().getTime());
        periodUnitCmb.setSelectedItem(getPeriodUnit(stc.getScheduledTransaction().getPeriodUnit()));
        periodSpin.setValue(scheduledTransaction.getPeriod());
        scheduleDaysSpin.setValue(scheduledTransaction.getScheduleDays());
        
    }
    
    public void getForm(org.paccman.controller.Controller controller) throws org.paccman.ui.form.BadInputException {
        
        ScheduledTransactionController stc = (ScheduledTransactionController)controller;
        
        // Scheduled transaction part
        ScheduledTransaction st = stc.getScheduledTransaction();
        st.setAutomatic(automaticChb.isSelected());
        st.setDescription(descriptionEdt.getText());
        st.setFixedAmount(fixedAmountChb.isSelected());
        Calendar nextOccurrenceDate = nextDateChooser.getCalendarDate();
        st.setNextOccurence(nextOccurrenceDate);
        Integer period = (Integer)periodSpin.getValue();
        if (period.intValue() <= 0) {
            throw  new BadInputException("Period should be >= 0", periodSpin);
        }
        st.setPeriod((Integer)periodSpin.getValue());
        st.setPeriodUnit(getPeriodUnit((String)periodUnitCmb.getSelectedItem()));
        st.setScheduleDays((Integer)scheduleDaysSpin.getValue());

        // Transaction part
        TransactionBase tb = stc.getScheduledTransaction().getTransactionBase();
        transactionFormPanel.getForm(ControllerManager.getController(tb));
        
    }
    
    public void setEditMode(boolean editing) {
        transactionFormPanel.setEditMode(editing);
        periodSpin.setEnabled(editing);
        periodUnitCmb.setEnabled(editing);
        nextDateChooser.setEnabled(editing);
        scheduleDaysSpin.setEnabled(editing);
        fixedAmountChb.setEnabled(editing);
        automaticChb.setEnabled(editing && fixedAmountChb.isSelected());
        descriptionEdt.setEnabled(editing);
    }
    
    public void registerToDocumentCtrl() {
        transactionFormPanel.registerToDocumentCtrl();
    }
    
    public org.paccman.controller.Controller getNewController() {
        ScheduledTransactionController stc = new ScheduledTransactionController();
        TransactionBaseController tbc = (TransactionBaseController)transactionFormPanel.getNewController();
        stc.getScheduledTransaction().setTransactionBase((TransactionBase)tbc.getObject());
        return stc;
    }
    
    public void clearForm() {
        transactionFormPanel.clearForm();
        descriptionEdt.setText("");
        periodSpin.setValue(0);
        periodUnitCmb.setSelectedItem(schedulePeriodUnits[0]);
        scheduleDaysSpin.setValue(0);
        automaticChb.setSelected(false);
        fixedAmountChb.setSelected(false);
    }
    
    public void itemStateChanged(java.awt.event.ItemEvent e) {
        if (e.getSource() == fixedAmountChb) {
            automaticChb.setEnabled(editing && fixedAmountChb.isSelected());
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        descriptionLbl = new javax.swing.JLabel();
        descriptionEdt = new javax.swing.JTextField();
        transactionFormPanel = new org.paccman.ui.schedules.TransactionFormPanel();
        schedulingFormPanel = new javax.swing.JPanel();
        periodSpin = new javax.swing.JSpinner();
        periodLbl = new javax.swing.JLabel();
        periodUnitCmb = new javax.swing.JComboBox();
        nextDateLbl = new javax.swing.JLabel();
        nextDateChooser = new org.paccman.ui.common.MyDateChooser();
        automaticChb = new javax.swing.JCheckBox();
        scheduleDays = new javax.swing.JLabel();
        scheduleDaysSpin = new javax.swing.JSpinner();
        fixedAmountChb = new javax.swing.JCheckBox();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));
        descriptionLbl.setText("Description");

        descriptionEdt.setEnabled(false);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(descriptionLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(descriptionEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(descriptionLbl)
                    .add(descriptionEdt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transactionFormPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scheduled Transaction"));

        schedulingFormPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scheduling"));
        periodSpin.setEnabled(false);

        periodLbl.setText("Every");

        periodUnitCmb.setEnabled(false);
        for (String s: schedulePeriodUnits) {
            ((DefaultComboBoxModel)periodUnitCmb.getModel()).addElement(s);
        }

        nextDateLbl.setText("Next");

        nextDateChooser.setEnabled(false);

        automaticChb.setText("Automatic");
        automaticChb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        automaticChb.setEnabled(false);
        automaticChb.setMargin(new java.awt.Insets(0, 0, 0, 0));

        scheduleDays.setText("Schedule Days");

        scheduleDaysSpin.setEnabled(false);

        fixedAmountChb.setText("Fixed Amount");
        fixedAmountChb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fixedAmountChb.setEnabled(false);
        fixedAmountChb.setMargin(new java.awt.Insets(0, 0, 0, 0));
        fixedAmountChb.addItemListener(this);

        org.jdesktop.layout.GroupLayout schedulingFormPanelLayout = new org.jdesktop.layout.GroupLayout(schedulingFormPanel);
        schedulingFormPanel.setLayout(schedulingFormPanelLayout);
        schedulingFormPanelLayout.setHorizontalGroup(
            schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(schedulingFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, scheduleDays)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, periodLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, periodSpin, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .add(scheduleDaysSpin, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(periodUnitCmb, 0, 159, Short.MAX_VALUE)
                    .add(automaticChb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(nextDateLbl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, fixedAmountChb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .add(nextDateChooser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addContainerGap())
        );
        schedulingFormPanelLayout.setVerticalGroup(
            schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(schedulingFormPanelLayout.createSequentialGroup()
                .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(periodLbl)
                        .add(periodSpin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(periodUnitCmb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(nextDateLbl))
                    .add(nextDateChooser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(schedulingFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(scheduleDays)
                    .add(fixedAmountChb)
                    .add(scheduleDaysSpin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(automaticChb))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        schedulingFormPanelLayout.linkSize(new java.awt.Component[] {automaticChb, fixedAmountChb, nextDateChooser, nextDateLbl, periodLbl, periodSpin, periodUnitCmb, scheduleDays, scheduleDaysSpin}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(transactionFormPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
            .add(schedulingFormPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(transactionFormPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(schedulingFormPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox automaticChb;
    private javax.swing.JTextField descriptionEdt;
    private javax.swing.JLabel descriptionLbl;
    private javax.swing.JCheckBox fixedAmountChb;
    private javax.swing.JPanel jPanel1;
    private org.paccman.ui.common.MyDateChooser nextDateChooser;
    private javax.swing.JLabel nextDateLbl;
    private javax.swing.JLabel periodLbl;
    private javax.swing.JSpinner periodSpin;
    private javax.swing.JComboBox periodUnitCmb;
    private javax.swing.JLabel scheduleDays;
    private javax.swing.JSpinner scheduleDaysSpin;
    private javax.swing.JPanel schedulingFormPanel;
    private org.paccman.ui.schedules.TransactionFormPanel transactionFormPanel;
    // End of variables declaration//GEN-END:variables
    
}
