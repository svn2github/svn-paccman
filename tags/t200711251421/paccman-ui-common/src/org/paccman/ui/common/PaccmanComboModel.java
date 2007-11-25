/*
 * BankComboModel.java
 *
 * Created on 12 juin 2005, 09:52
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.common;

import javax.swing.DefaultComboBoxModel;
import org.paccman.controller.BankController;
import org.paccman.controller.PaccmanView;

/**
 *
 * @author joao
 */
public class PaccmanComboModel extends DefaultComboBoxModel implements PaccmanView {
    
    /** Creates a new instance of BankComboModel */
    public PaccmanComboModel() {
    }

    public void removeElement(Object anObject) {
        BankController bankCtrl = (BankController)anObject;
        bankCtrl.unregisterView(this);

        super.removeElement(anObject);
    }

    public void addElement(Object anObject) {
        if (anObject != null) {
            BankController bankCtrl = (BankController)anObject;
            bankCtrl.registerView(this);
        }

        super.addElement(anObject);
    }

    public void onChange(org.paccman.controller.Controller controller) {
        int index = getIndexOf(controller);
        fireContentsChanged(this, index, index);
    }
    
}
