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
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.controller.PayeeController;
import org.paccman.paccman.Payee;
import org.paccman.ui.common.*;
//:TODO:import org.paccman.ui.main.Main;

/**
 *
 * @author joao
 */
public class PayeeComboBox extends PaccmanObjectComboBox implements PaccmanView {
    
    /** Creates a new instance of PayeeComboBox */
    public PayeeComboBox() {
        setModel(new PayeeComboModel());
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
/*:TODO:        if (controller == Main.getDocumentCtrl()) {
            removeAllItems();
            addItem(null);
            for (Payee payee: Main.getDocumentCtrl().getDocument().getPayees()) {
                PayeeController payeeCtrl = (PayeeController)ControllerManager.getController(payee);
                addItem(payeeCtrl);
            }
        }*/
    }
    
}
