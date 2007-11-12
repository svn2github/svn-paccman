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

package org.paccman.ui.welcome.schedules;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import org.paccman.controller.DocumentController;
import org.paccman.controller.PaccmanView;
import org.paccman.ui.common.table.TableModel;
import org.paccman.ui.common.table.TableModel.ColumnDescriptor;
import org.paccman.ui.main.Main;
import org.paccman.ui.scheduling.ExpiredScheduledTransaction;
import org.paccman.ui.scheduling.Scheduler;
import static org.paccman.ui.main.ContextMain.*;

public class ScheduledTableModel extends TableModel implements PaccmanView {
    
    ArrayList<ExpiredScheduledTransaction> expiredSchedTransactions = new ArrayList<ExpiredScheduledTransaction>();
    
    public int getRowCount() {
        return expiredSchedTransactions.size();
    }

    /**
     * Creates a new instance of ScheduledTableModel
     */
    public ScheduledTableModel() {
    }

    private static final int ACCOUNT_INDEX        = 0;
    private static final int NEXT_OCCURENCE_INDEX = 1;
    private static final int DESCRIPTION_INDEX    = 2;
    private static final int AMOUNT_INDEX         = 3;
    private static final int TYPE_INDEX           = 4;
    private static final int STATUS_INDEX         = 5;
    {
        final int NB_DESC = 6;
        columnDescriptors = new ColumnDescriptor[NB_DESC];
        columnDescriptors[0] = new ColumnDescriptor("Account", ACCOUNT_INDEX, new DefaultScheduledTableCellRenderer(), "ACCOUNT_");
        columnDescriptors[1] = new ColumnDescriptor("Next occurence", NEXT_OCCURENCE_INDEX, new DateCellRenderer(), "NEXT_OCCURENCE_");
        columnDescriptors[2] = new ColumnDescriptor("Description"   , DESCRIPTION_INDEX, new DefaultScheduledTableCellRenderer(), "DESCRIPTION_"); 
        columnDescriptors[3] = new ColumnDescriptor("Amount", AMOUNT_INDEX, new AmountCellRenderer(), "AMOUNT_");
        columnDescriptors[4] = new ColumnDescriptor("Type", TYPE_INDEX, new DefaultScheduledTableCellRenderer(), "TYPE_");
        columnDescriptors[5] = new ColumnDescriptor("Status", STATUS_INDEX, new DefaultScheduledTableCellRenderer(), "STATUS_");
    }
    
    public int getStatusDataIndex() {
        return getColumnIndex(STATUS_INDEX);
    }
    
    public Object getDataValue(int row, int column) {
        ExpiredScheduledTransaction est = expiredSchedTransactions.get(row);
        switch (column) {
            case ACCOUNT_INDEX: return est.getAccount().getAccount().getName();
            case NEXT_OCCURENCE_INDEX: return est.getOccurence();
            case DESCRIPTION_INDEX: return est.getScheduledTransaction().getScheduledTransaction().getDescription();
            case AMOUNT_INDEX: return est.getScheduledTransaction().getScheduledTransaction().getTransactionBase().getAmount();
            case TYPE_INDEX: return est.getScheduledTransaction().getScheduledTransaction().isAutomatic() ? "A" : "M";
            case STATUS_INDEX: return new Boolean(est.isRegistered()); //:TODO:
            default: 
                assert false: "";
                return null;
        }
    }
    
    public void registerToDocumentCtrl() {
        getDocumentController().registerView(this);
    }
    
    public ExpiredScheduledTransaction getExpiredScheduledTransaction(int index) {
        return expiredSchedTransactions.get(index);
    }
    
    public void removeExpiredScheduledTransaction(int index) {
        expiredSchedTransactions.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
         if (controller == getDocumentController()) {
             // Unregister current scheduled transaction if any
             for (ExpiredScheduledTransaction est: expiredSchedTransactions) {
                 est.unregisterView(this);
             }
             
             // Get new expired scheduled transactions
            Scheduler.getScheduledTransaction(expiredSchedTransactions, (DocumentController)controller, new GregorianCalendar());
            
            // Register new expired scheduled transactions
             for (ExpiredScheduledTransaction est: expiredSchedTransactions) {
                 est.registerView(this);
             }
             
             fireTableDataChanged();
             
        } else {
             //:TODO:
            fireTableDataChanged(); 
        }
    }
    
}
