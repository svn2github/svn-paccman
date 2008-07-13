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

package org.paccman.ui.welcome.accounts;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.DocumentController;
import org.paccman.controller.PaccmanView;
import static org.paccman.ui.main.ContextMain.*;

class AccountTableModel extends AbstractTableModel implements PaccmanView {
    
    public AccountTableModel() {
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        DocumentController documentCtrl = getDocumentController();
        
        switch (columnIndex) {
            
            case AccountTable.ACCOUNT_NAME_COL:
                return isTotalRow(rowIndex) ? "TOTAL1": ControllerManager.getController(documentCtrl.getDocument().getAccount(rowIndex));
                
            case AccountTable.CURRENT_BALANCE_COL:
                Calendar today = new GregorianCalendar();
                return isTotalRow(rowIndex) ? documentCtrl.getDocument().getTotalBalance(today) : documentCtrl.getDocument().getAccount(rowIndex).getBalance(today);
                
            case AccountTable.AVAILABLE_BALANCE_COL:
                return isTotalRow(rowIndex) ? documentCtrl.getDocument().getTotalBalance() : documentCtrl.getDocument().getAccount(rowIndex).getCurrentBalance();
                
            default:
                return null;
        }
    }
    
    public boolean isTotalRow(int row) {
        return row == getDocumentController().getDocument().getAccounts().size();
    }
    
    public int getRowCount() {
        DocumentController documentCtrl = getDocumentController();
        if (documentCtrl != null) {
            return documentCtrl.getDocument().getAccounts().size() + 1; // +1 for the total line
        } else {
            return 0;
        }
    }
    
    public int getColumnCount()    {
        return AccountTable.columns.length;
    }
    
    public String getColumnName(int column) {
        return AccountTable.columns[column];
    }

    public void onChange(org.paccman.controller.Controller controller) {
        if (controller == getDocumentController()) {
            fireTableDataChanged();
        } else {
            //:TODO:account controlelr of one row changed
        }
    }
    
}
