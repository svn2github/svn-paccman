/*
 * ThirdPartySelectorPanel.java
 *
 * Created on 5 juin 2005, 01:35
 */

package org.paccman.ui.payees;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;
import org.paccman.controller.PayeeController;
import org.paccman.controller.Controller;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Payee;
import org.paccman.ui.main.Main;
import org.paccman.ui.selector.ControllerSelectionButton;
import org.paccman.ui.selector.ControllerSelectionListener;

/**
 *
 * @author  joao
 */
public class PayeeSelectorPanel extends javax.swing.JPanel implements PaccmanView, ActionListener {
    
    PayeeController  thirdPartyCtrl ; // label of selected thirdParty
    
    Vector<ControllerSelectionButton> selButtons = new Vector<ControllerSelectionButton>();
    
    Vector<ControllerSelectionListener> thirdPartySelectionListener = new Vector<ControllerSelectionListener>();
    
    public void setThirdPartyCtrl(PayeeController thirdPartyCtrl) {
        if (this.thirdPartyCtrl == thirdPartyCtrl) {
            return;
        }
        if (this.thirdPartyCtrl != null) {
            this.thirdPartyCtrl.unregisterView(this);
        }
        this.thirdPartyCtrl = thirdPartyCtrl;
        thirdPartyCtrl.registerView(this);
        selectedThirdPartyLbl.setText(thirdPartyCtrl.getPayee().getName());
    }
    
    public void addListener(ControllerSelectionListener listener) {
        thirdPartySelectionListener.add(listener);
    }
    
    /**
     * Creates new form ThirdPartySelectorPanel
     */
    public PayeeSelectorPanel() {
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
                thirdPartyButtonsPanel.remove(acb);
            }
            
            // Clear button list
            selButtons.clear();
            
            // Add the new buttons
            Collection<Payee> thirdPartys = Main.getDocumentCtrl().getDocument().getPayees();
            for (Payee thirdParty: thirdPartys) {
                ControllerSelectionButton asb = new ControllerSelectionButton((PayeeController)ControllerManager.getController(thirdParty));
                asb.addActionListener(this);
                thirdPartyButtonsPanel.add(asb);
                selButtons.add(asb);
            }
            
            validate();
        } else if (controller == thirdPartyCtrl) {
            selectedThirdPartyLbl.setText(thirdPartyCtrl.getPayee().getName());
        }
        
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        ControllerSelectionButton asb = (ControllerSelectionButton)e.getSource();
        if (e != null) {
            if (selectThirdParty((PayeeController)asb.getController())) {
                setThirdPartyCtrl((PayeeController)asb.getController());
            }
        }
    }
    
    private boolean selectionEnabled() {
        // Check if all listeners allow the selection
        for (ControllerSelectionListener asl: thirdPartySelectionListener) {
            if (! asl.selectionEnabled()) {
                // if not return
                return false;
            }
        }
        return true;
    }
    
    public boolean selectThirdParty(PayeeController thirdParty) {
        if (selectionEnabled()) {
            // Notify all seletino listeners
            for (ControllerSelectionListener asl: thirdPartySelectionListener) {
                asl.controllerSelected(thirdParty);
            }
            setThirdPartyCtrl(thirdParty);
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
        thirdPartyButtonsPanel = new javax.swing.JPanel();
        selectedThirdPartyLbl = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        thirdPartyButtonsPanel.setLayout(new java.awt.GridLayout(0, 1));

        selectedThirdPartyLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedThirdPartyLbl.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null), new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        thirdPartyButtonsPanel.add(selectedThirdPartyLbl);

        add(thirdPartyButtonsPanel, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel selectedThirdPartyLbl;
    private javax.swing.JPanel thirdPartyButtonsPanel;
    // End of variables declaration//GEN-END:variables
    
}