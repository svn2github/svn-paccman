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

package org.paccman.ui.transactions.table;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import org.paccman.controller.AccountController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.controller.TransactionBaseController;
import org.paccman.paccman.Category;
import org.paccman.paccman.CategoryBase;
import org.paccman.paccman.SubCategory;
import org.paccman.paccman.TransactionBase;
import org.paccman.ui.common.table.TableModel;
import org.paccman.ui.common.table.TableModel.ColumnDescriptor;
import org.paccman.ui.common.table.renderer.TableCellRenderer;

public class TransactionTableModel extends TableModel implements PaccmanView {
    
    public enum Mode { VIEW_MODE, STATUS_EDIT_MODE };
    
    Mode mode = Mode.VIEW_MODE;
    
    public void setTransactionStatus(TransactionBase transaction, int rowIndex, 
            TransactionBase.ReconciliationState status) {
        statuses.put(transaction, status);
        int row = transactionIndexToTransactionRow(rowIndex);
        fireTableRowsUpdated(row, row);
    }

    public TransactionBase.ReconciliationState getStatus(TransactionBase transaction) {
        return statuses.get(transaction);
    }
    
    // Set the reconciliation statuses of marked transaction to reconciled
    public void validateReconciliation() {
        Iterator<Map.Entry<TransactionBase, TransactionBase.ReconciliationState>> iter = statuses.entrySet().iterator();
        while (iter.hasNext()) {
            TransactionBase tb = iter.next().getKey();
            if (getStatus(tb) == TransactionBase.ReconciliationState.MARKED) {
                tb.setReconciliationState(TransactionBase.ReconciliationState.RECONCILED);
            }
        }
        fireTableDataChanged();
    }
    
    // Set the reconciliation statuses of marked transaction to marked (transaction will be saved as pending)
    public void setPendingReconciliation() {
        Iterator<Map.Entry<TransactionBase, TransactionBase.ReconciliationState>> iter = statuses.entrySet().iterator();
        while (iter.hasNext()) {
            TransactionBase tb = iter.next().getKey();
            tb.setReconciliationState(getStatus(tb));
        }
        fireTableDataChanged();
    }
    
    // map with reconciliation statuses for reconciliation
    public Map<TransactionBase, TransactionBase.ReconciliationState> statuses =
            new HashMap<TransactionBase, TransactionBase.ReconciliationState>();
    
    public void setMode(Mode mode) {
        if (this.mode != mode) {
            this.mode = mode;
            if (mode == Mode.STATUS_EDIT_MODE) {
                // Switch to edit mode
                statuses.clear();
                for (TransactionBase tb: accountCtrl.getAccount().getTransactions()) {
                    statuses.put(tb, tb.getReconciliationState());
                }
            } else {
                // Switch to view
                fireTableDataChanged();
            }
        }
    }
    
    public int transactionIndexToTransactionRow(int transactionIndex) {
        return accountCtrl.getAccount().getTransactions().size() - transactionIndex - 1;
    }
    
    public int transactionRowToTransactionIndex(int transactionRow) {
        return accountCtrl.getAccount().getTransactions().size() - transactionRow - 1;
    }
    
    public TransactionBaseController getTransactionAtRow(int rowIndex) {
        int index = transactionRowToTransactionIndex(rowIndex);
        TransactionBase tb = accountCtrl.getAccount().getTransaction(index);
        return (TransactionBaseController)ControllerManager.getController(tb);
    }
    
    AccountController accountCtrl;
    
    /**
     * Creates a new instance of TransactionTableModel
     */
    public TransactionTableModel() {
    }

    private String getCategoryBaseName(CategoryBase category) {
        if (category instanceof Category) {
            return ((Category)category).getName();
        } else {
            assert category instanceof SubCategory;
            SubCategory sc = (SubCategory)category;
            return sc.getParentCategory().getName() + ":" + sc.getName();
        }
    }

    public int getRowCount() {
        return accountCtrl != null ? accountCtrl.getAccount().getTransactions().size() : 0;
    }
    
    private static final int TRANSACTION_DATE_DATA_INDEX = 0;
    private static final int VALUE_DATE_DATA_INDEX       = 1;
    private static final int STATUS_DATA_INDEX           = 2;
    private static final int LABEL_DATA_INDEX            = 3;
    private static final int DEBIT_DATA_INDEX            = 4;
    private static final int CREDIT_DATA_INDEX           = 5;
    private static final int BALANCE_DATA_INDEX          = 6;
    {
        final int NB_DESC = 7;
        columnDescriptors = new ColumnDescriptor[NB_DESC];
        columnDescriptors[0] = new ColumnDescriptor("Transaction Date", TRANSACTION_DATE_DATA_INDEX, new DateCellRenderer(), "TRANSACTION_DATE_");
        columnDescriptors[1] = new ColumnDescriptor("Value Date", VALUE_DATE_DATA_INDEX, new DateCellRenderer(), "VALUE_DATE_");
        columnDescriptors[2] = new ColumnDescriptor("Status"   , STATUS_DATA_INDEX, new StatusCellRenderer(), "STATUS_");
        columnDescriptors[3] = new ColumnDescriptor("Label", LABEL_DATA_INDEX, new DefaultTransactionTableCellRenderer(), "LABEL_");
        AmountCellRenderer amountCellRenderer = new AmountCellRenderer();
        amountCellRenderer.setShowSign(false);
        columnDescriptors[4] = new ColumnDescriptor("Debit", DEBIT_DATA_INDEX, amountCellRenderer, "DEBIT_");
        columnDescriptors[5] = new ColumnDescriptor("Credit", CREDIT_DATA_INDEX, amountCellRenderer, "CREDIT_");
        amountCellRenderer = new AmountCellRenderer();
        amountCellRenderer.setShowSign(true);
        amountCellRenderer.setColorWhenNegative(Color.RED);
        columnDescriptors[6] = new ColumnDescriptor("Balance", BALANCE_DATA_INDEX, amountCellRenderer, "BALANCE_");
    }
    
    public static int getStatusDataIndex() {
        return STATUS_DATA_INDEX;
    }
    
    public Object getDataValue(int row, int column) {
        TransactionBaseController tbc = getTransactionAtRow(row);
        TransactionBase tb = getTransactionAtRow(row).getTransactionBase();
        switch (column) {
            case TRANSACTION_DATE_DATA_INDEX: return tb.getTransactionDate();
            case VALUE_DATE_DATA_INDEX      : return tb.getValueDate();
            case STATUS_DATA_INDEX          : return mode == Mode.VIEW_MODE ? tb.getReconciliationState() : statuses.get(tb);
            case LABEL_DATA_INDEX           : return tb.getLabel();
            case CREDIT_DATA_INDEX          : return tb.isDeposit()    ? tb.getAmount() : null;
            case DEBIT_DATA_INDEX           : return tb.isWithdrawal() ? tb.getAmount() : null;
            case BALANCE_DATA_INDEX         : return tb.getBalance();
            default : return null;
        }
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
        fireTableChanged(new TableModelEvent(this)); //:TODO:optimize this
    }

    public void setAccountController(AccountController accountCtrl) {
        if (this.accountCtrl != null) {
            this.accountCtrl.unregisterView(this);
        }
        this.accountCtrl = accountCtrl;
        accountCtrl.registerView(this);
        fireTableChanged(new TableModelEvent(this)); //:TODO:optimize this
    }
    
}
