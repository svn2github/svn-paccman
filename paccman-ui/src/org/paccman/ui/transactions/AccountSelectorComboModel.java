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

package org.paccman.ui.transactions;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import org.paccman.controller.AccountController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Account;
import org.paccman.paccman.Category;
import org.paccman.paccman.SubCategory;
import org.paccman.ui.main.Main;

/**
 *
 * @author joao
 */
public class AccountSelectorComboModel extends DefaultComboBoxModel implements PaccmanView {
    
    // When using this for selected a transfer, we need to exclude an account
    // (the source account)
    AccountController excludedAccount; 
    
    /** Creates a new instance of BankComboModel */
    public AccountSelectorComboModel() {
    }
    
    public void removeElement(Object anObject) {
        if (anObject != null) {
            AccountController accountCtrl = (AccountController)anObject;
            accountCtrl.unregisterView(this);
        }
        
        super.removeElement(anObject);
    }
    
    public void addElement(Object anObject) {
        if (anObject != null) {
            AccountController accountCtrl = (AccountController)anObject;
            accountCtrl.registerView(this);
        }
        
        super.addElement(anObject);
    }
    
    // PAccMan change notification
    public void onChange(org.paccman.controller.Controller controller) {
        // Reset category list :TODO:optimize this
        removeAllElements();
        
        addElement(null);
//:TODO:        if (controller == Main.getDocumentCtrl()) {
            Vector<Account> accounts = Main.getDocumentCtrl().getDocument().getAccounts();
            
            // Categories and subcategories
            for (Account account: accounts) {
                if (ControllerManager.getController(account) != excludedAccount) {
                    addElement(ControllerManager.getController(account));
                }
            }
//:TODO:START://        } else {
//            //:DEBUG:START:
//            assert false; //not implemented yet
//            //:DEBUG:END:
//            //:TODO:
//:TODO:END:        }
    }

    public void setExcludedAccount(AccountController excludedAccount) {
        this.excludedAccount = excludedAccount;
        onChange(Main.getDocumentCtrl()); //:TODO: try to do this more "nicely"
    }
    
}
