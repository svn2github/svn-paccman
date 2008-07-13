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

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.controller.PayeeController;
import org.paccman.paccman.Payee;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author joao
 */
public class PayeeSelectorComboModel extends DefaultComboBoxModel implements PaccmanView {
    
    /** Creates a new instance of BankComboModel */
    public PayeeSelectorComboModel() {
    }
    
    public void removeElement(Object anObject) {
        if (anObject != null) {
            PayeeController payeeCtrl = (PayeeController)anObject;
            payeeCtrl.unregisterView(this);
        }
        
        super.removeElement(anObject);
    }
    
    public void addElement(Object anObject) {
        if (anObject != null) {
            PayeeController payeeCtrl = (PayeeController)anObject;
            payeeCtrl.registerView(this);
        }
        
        super.addElement(anObject);
    }
    
    // PAccMan change notification
    public void onChange(org.paccman.controller.Controller controller) {
        // Reset category list :TODO:optimize this
        removeAllElements();
        
        addElement(null);
        if (controller == getDocumentController()) {
            Vector<Payee> payees = getDocumentController().getDocument().getPayees();
            
            // Categories and subcategories
            for (Payee payee: payees) {
                addElement(ControllerManager.getController(payee));
            }
        } else {
            //:TODO:
        }
    }
    
}
