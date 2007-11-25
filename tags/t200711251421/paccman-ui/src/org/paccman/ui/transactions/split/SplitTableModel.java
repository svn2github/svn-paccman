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

package org.paccman.ui.transactions.split;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.table.AbstractTableModel;
import org.paccman.controller.CategoryBaseController;
import org.paccman.controller.CategoryController;
import org.paccman.paccman.Category;
import org.paccman.paccman.CategoryBase;
import org.paccman.paccman.SplitTransaction;
import org.paccman.paccman.SubCategory;
import org.paccman.ui.common.table.TableModel;


public class SplitTableModel extends TableModel {
    
    ArrayList<SplitTransaction> splitTransactions = new ArrayList<SplitTransaction>();
    
    /** Creates a new instance of SplitTableModel */
    public SplitTableModel() {
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

    public void addSplitTransaction(CategoryBaseController category, BigDecimal amount, String note, boolean notify) {
        SplitTransaction bt = new SplitTransaction();
        bt.setAmount(amount);
        bt.setCategory(category.getCategoryBase());
        bt.setNote(note);
        int ix = splitTransactions.size();
        splitTransactions.add(ix, bt);
        if (notify) {
            fireTableRowsInserted(ix, ix);
        }
    }
    
    public ArrayList<SplitTransaction> getSplitTransactions() {
        return splitTransactions;
    }
    
    public void addSplitTransaction(CategoryBaseController category, BigDecimal amount, String note) {
        addSplitTransaction(category, amount, note, false);
    }
    
    public void clear() {
        splitTransactions.clear();
    }
    
    public int getRowCount() {
        return splitTransactions.size();
    }

    final int CATEGORY_DATA_INDEX = 0;
    final int NOTE_DATA_INDEX     = 1;
    final int AMOUNT_DATA_INDEX   = 2;
    {
        final int NB_DESC = 3;
        columnDescriptors = new ColumnDescriptor[NB_DESC];
        columnDescriptors[0] = new ColumnDescriptor("Category", CATEGORY_DATA_INDEX, new DefaultSplitTableCellRenderer(), "CATEGORY_");
        columnDescriptors[1] = new ColumnDescriptor("Label"   , NOTE_DATA_INDEX    , new DefaultSplitTableCellRenderer(), "LABEL_"   );
        columnDescriptors[2] = new ColumnDescriptor("Amount  ", AMOUNT_DATA_INDEX  , new AmountCellRenderer()           , "AMOUNT_"  );
    }
    
    public Object getDataValue(int row, int column) {
        switch (column) {
            case CATEGORY_DATA_INDEX: return getCategoryBaseName(splitTransactions.get(row).getCategory());
            case NOTE_DATA_INDEX    : return splitTransactions.get(row).getNote()                         ;
            case AMOUNT_DATA_INDEX  : return splitTransactions.get(row).getAmount()                       ;
            default:
                assert false: "Invalid column identifier";
                return null;
        }
    }
    
}
