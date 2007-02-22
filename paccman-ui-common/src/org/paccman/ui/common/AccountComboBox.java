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

package org.paccman.ui.common;
import org.paccman.controller.PaccmanView;
//:TODO: import org.paccman.ui.main.Main;

/**
 *
 * @author joao
 */
public class AccountComboBox extends PaccmanObjectComboBox implements PaccmanView {
    
    /** Creates a new instance of BankComboBox */
    public AccountComboBox() {
        setModel(new AccountComboModel());
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
/*:TODO:        if (controller == Main.getDocumentCtrl()) {
            removeAllItems();
            addItem(null);
            for (Account account: Main.getDocumentCtrl().getDocument().getAccounts()) {
                AccountController accountCtrl = (AccountController)ControllerManager.getController(account);
                addItem(accountCtrl);
            }
        }
 */
    }
    
}
