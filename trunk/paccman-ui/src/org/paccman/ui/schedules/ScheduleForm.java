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
    
    private static final String[] schedulePeriodUnits = {
        "Day",
        "Week",
        "Month",
        "Year"
    };
    
    ScheduledTransaction.PeriodUnit getPeriodUnit(String schedulePeriodUnit) {
        if (schedulePeriodUnit.equals(schedulePeriodUnits[0])) {
            return ScheduledTransaction.PeriodUnit.DAY;
        } else if (schedulePeriodUnit.equals(schedulePeriodUnits[1])) {
            return ScheduledTransaction.PeriodUnit.WEEK;
        } else if (schedulePeriodUnit.equals(schedulePeriodUnits[2])) {
            return ScheduledTransaction.PeriodUnit.MONTH;
        } else if (schedulePeriodUnit.equals(schedulePeriodUnits[3])) {
            return ScheduledTransaction.PeriodUnit.YEAR;
        } else {
            throw new AssertionError("Unhandled peridoUnit: " + schedulePeriodUnit);
        }
    }
    
    String getPeriodUnit(ScheduledTransaction.PeriodUnit periodUnid) {
        switch (periodUnid) {
            case DAY:
                return schedulePeriodUnits[0];
            case WEEK:
                return schedulePeriodUnits[1];
            case MONTH:
                return schedulePeriodUnits[2];
            case YEAR:
                return schedulePeriodUnits[3];
            default:
                throw new AssertionError("Unhandled periodUnit: " + periodUnid);
        }
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionEdt, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionLbl)
                    .addComponent(descriptionEdt, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        scheduleDays.setText("Schedule Days");

        scheduleDaysSpin.setEnabled(false);

        fixedAmountChb.setText("Fixed Amount");
        fixedAmountChb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fixedAmountChb.setEnabled(false);
        fixedAmountChb.addItemListener(this);

        javax.swing.GroupLayout schedulingFormPanelLayout = new javax.swing.GroupLayout(schedulingFormPanel);
        schedulingFormPanel.setLayout(schedulingFormPanelLayout);
        schedulingFormPanelLayout.setHorizontalGroup(
            schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schedulingFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scheduleDays, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(periodLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(periodSpin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(scheduleDaysSpin, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(periodUnitCmb, 0, 159, Short.MAX_VALUE)
                    .addComponent(automaticChb, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fixedAmountChb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(nextDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addContainerGap())
        );
        schedulingFormPanelLayout.setVerticalGroup(
            schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(schedulingFormPanelLayout.createSequentialGroup()
                .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(periodLbl)
                        .addComponent(periodSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(periodUnitCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nextDateLbl))
                    .addComponent(nextDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(schedulingFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scheduleDays)
                    .addComponent(fixedAmountChb)
                    .addComponent(scheduleDaysSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(automaticChb))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        schedulingFormPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {automaticChb, fixedAmountChb, nextDateChooser, nextDateLbl, periodLbl, periodSpin, periodUnitCmb, scheduleDays, scheduleDaysSpin});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(transactionFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
            .addComponent(schedulingFormPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(transactionFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(schedulingFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
