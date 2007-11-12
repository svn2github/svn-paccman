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

package org.paccman.ui.schedules.table;

import javax.swing.event.TableModelEvent;
import org.paccman.controller.AccountController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.controller.ScheduledTransactionController;
import org.paccman.paccman.ScheduledTransaction;
import org.paccman.paccman.TransactionBase;
import org.paccman.ui.common.table.TableModel;
import org.paccman.ui.common.table.TableModel.ColumnDescriptor;
import static org.paccman.ui.main.ContextMain.*;

public class ScheduledTableModel extends TableModel implements PaccmanView {
    
    AccountController accountCtrl;
    
    public void setAccountController(AccountController accountCtrl) {
        this.accountCtrl = accountCtrl;
        fireTableChanged(new TableModelEvent(this));
        accountCtrl.registerView(this);
    }
    
    public int getRowCount() {
        return accountCtrl != null ? accountCtrl.getAccount().getScheduledTransactions().size() : 0;
    }
    
    /**
     * Creates a new instance of ScheduledTableModel
     */
    public ScheduledTableModel() {
    }
    
    private static final int DESCRIPTION_INDEX    = 0;
    private static final int NEXT_OCCURENCE_INDEX = 1;
    private static final int AMOUNT_INDEX         = 2;
    {
        final int NB_DESC = 3;
        columnDescriptors = new ColumnDescriptor[NB_DESC];
        columnDescriptors[0] = new ColumnDescriptor("Description", DESCRIPTION_INDEX, new DefaultScheduledTableCellRenderer(), "DESCRIPTION_");
        columnDescriptors[1] = new ColumnDescriptor("Next occurence", NEXT_OCCURENCE_INDEX, new DateCellRenderer(), "NEXT_OCCURENCE_");
        columnDescriptors[2] = new ColumnDescriptor("Amount", AMOUNT_INDEX, new AmountCellRenderer(), "AMOUNT_");
    }
    
    public Object getDataValue(int row, int dataIndex) {
        ScheduledTransactionController stc = getSchedTransactionAtRow(row);
        ScheduledTransaction st = stc.getScheduledTransaction();
        TransactionBase tb = stc.getScheduledTransaction().getTransactionBase();
        switch (dataIndex) {
            case DESCRIPTION_INDEX: return st.getDescription();
            case NEXT_OCCURENCE_INDEX: return st.getNextOccurence();
            case AMOUNT_INDEX: return st.getTransactionBase().getAmount();
            default : return null;
        }
    }
    
    public void registerToDocumentCtrl() {
        getDocumentController().registerView(this);
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
        if (controller == getDocumentController()) {
            fireTableChanged(new TableModelEvent(this));
        } else if (controller == accountCtrl) {
            fireTableChanged(new TableModelEvent(this));
        }
    }
    
    private ScheduledTransactionController getSchedTransactionAtRow(int row) {
        return (ScheduledTransactionController)ControllerManager.getController(accountCtrl.getAccount().getScheduledTransaction(row));
    }
    
}
