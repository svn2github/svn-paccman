/*
 * PaymentMethodSelectorPanel.java
 *
 * Created on 5 juin 2005, 01:35
 */

package org.paccman.ui.paymentmethods;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;
import org.paccman.controller.PaymentMethodController;
import org.paccman.controller.Controller;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.PaymentMethod;
import org.paccman.ui.selector.ControllerSelectionButton;
import org.paccman.ui.selector.ControllerSelectionListener;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author  joao
 */
public class PaymentMethodSelectorPanel extends javax.swing.JPanel implements PaccmanView, ActionListener {
    
    PaymentMethodController  paymentMethodCtrl ; // label of selected paymentMethod
    
    Vector<ControllerSelectionButton> selButtons = new Vector<ControllerSelectionButton>();
    
    Vector<ControllerSelectionListener> paymentMethodSelectionListener = new Vector<ControllerSelectionListener>();
    
    public void setPaymentMethodCtrl(PaymentMethodController paymentMethodCtrl) {
        if (this.paymentMethodCtrl == paymentMethodCtrl) {
            return;
        }
        if (this.paymentMethodCtrl != null) {
            this.paymentMethodCtrl.unregisterView(this);
        }
        this.paymentMethodCtrl = paymentMethodCtrl;
        paymentMethodCtrl.registerView(this);
        selectedPaymentMethodLbl.setText(paymentMethodCtrl.getPaymentMethod().getName());
    }
    
    public void addListener(ControllerSelectionListener listener) {
        paymentMethodSelectionListener.add(listener);
    }
    
    /**
     * Creates new form PaymentMethodSelectorPanel
     */
    public PaymentMethodSelectorPanel() {
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
                paimentMethodButtonsPanel.remove(acb);
            }
            
            // Clear button list
            selButtons.clear();
            
            // Add the new buttons
            Collection<PaymentMethod> paymentMethods = getDocumentController().getDocument().getPaymentMethods();
            for (PaymentMethod paymentMethod: paymentMethods) {
                ControllerSelectionButton asb = new ControllerSelectionButton((PaymentMethodController)ControllerManager.getController(paymentMethod));
                asb.addActionListener(this);
                paimentMethodButtonsPanel.add(asb);
                selButtons.add(asb);
            }
            
            validate();
        } else if (controller == paymentMethodCtrl) {
            selectedPaymentMethodLbl.setText(paymentMethodCtrl.getPaymentMethod().getName());
        }
        
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        ControllerSelectionButton asb = (ControllerSelectionButton)e.getSource();
        if (e != null) {
            if (selectPaymentMethod((PaymentMethodController)asb.getController())) {
                setPaymentMethodCtrl((PaymentMethodController)asb.getController());
            }
        }
    }
    
    private boolean selectionEnabled() {
        // Check if all listeners allow the selection
        for (ControllerSelectionListener asl: paymentMethodSelectionListener) {
            if (! asl.selectionEnabled()) {
                // if not return
                return false;
            }
        }
        return true;
    }
    
    public boolean selectPaymentMethod(PaymentMethodController paymentMethod) {
        if (selectionEnabled()) {
            // Notify all seletino listeners
            for (ControllerSelectionListener asl: paymentMethodSelectionListener) {
                asl.controllerSelected(paymentMethod);
            }
            setPaymentMethodCtrl(paymentMethod);
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
        paimentMethodButtonsPanel = new javax.swing.JPanel();
        selectedPaymentMethodLbl = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        paimentMethodButtonsPanel.setLayout(new java.awt.GridLayout(0, 1));

        selectedPaymentMethodLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedPaymentMethodLbl.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        paimentMethodButtonsPanel.add(selectedPaymentMethodLbl);

        add(paimentMethodButtonsPanel, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel paimentMethodButtonsPanel;
    private javax.swing.JLabel selectedPaymentMethodLbl;
    // End of variables declaration//GEN-END:variables
    
}