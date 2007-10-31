/*
 * BankComboBox.java
 *
 * Created on 12 juin 2005, 08:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.common;
import org.paccman.controller.PaymentMethodController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.PaymentMethod;
import org.paccman.ui.common.*;
//:TODO: import org.paccman.ui.main.Main;

/**
 *
 * @author joao
 */
public class PaymentMethodComboBox extends PaccmanObjectComboBox implements PaccmanView {
    
    /** Creates a new instance of BankComboBox */
    public PaymentMethodComboBox() {
        setModel(new PaymentMethodComboModel());
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
/*:TODO:        if (controller == Main.getDocumentCtrl()) {
            removeAllItems();
            addItem(null);
            for (PaymentMethod paymentMethod: Main.getDocumentCtrl().getDocument().getPaymentMethods()) {
                PaymentMethodController paymentMethodCtrl = (PaymentMethodController)ControllerManager.getController(paymentMethod);
                addItem(paymentMethodCtrl);
            }
        }*/
    }

}
