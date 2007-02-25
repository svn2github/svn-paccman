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

import java.util.prefs.Preferences;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import org.paccman.ui.common.table.Table;

/**
 *
 * @author  joao
 */
public class ScheduledTransactionTable extends Table implements TableColumnModelListener {
    
    boolean isAdjustingColumnWidth = false;
    
    public ScheduledTableModel getScheduledTableModel() {
        return (ScheduledTableModel)super.getModel();
    }
    
    /** Creates new form BeanForm */
    public ScheduledTransactionTable() {
        initComponents();
        
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        setModel(new ScheduledTableModel());
//        ScheduledTransactionTableCellRenderer renderer;
//        rowHeight = -1;
//        
//        TableColumn nextOccurenceColumn = getColumnModel().getColumn(ScheduledTransactionTableModel_.NEXT_OCCURENCE_COL);
//        nextOccurenceColumn.setCellRenderer(renderer = new DateCellRenderer());
//        adjustRowHeight(renderer);
//        adjustColumnWidth(nextOccurenceColumn, SCH_NEXT_OCCURENCE_COL_SIZE);
//        
//        TableColumn descriptionColumn = getColumnModel().getColumn(ScheduledTransactionTableModel_.DESCRIPTION_COL);
//        descriptionColumn.setCellRenderer(renderer = new ScheduledTransactionTableCellRenderer());
//        adjustRowHeight(renderer);
//        adjustColumnWidth(descriptionColumn, SCH_DESCRIPTION_COL_SIZE);
//        
//        setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
//        
//        TableColumn amountColumn = getColumnModel().getColumn(ScheduledTransactionTableModel_.AMOUNT_COL);
//        amountColumn.setCellRenderer(renderer = new AmountCellRenderer(false));
//        adjustRowHeight(renderer);
//        adjustColumnWidth(amountColumn, SCH_AMOUNT_COL_SIZE);
//        
//        getColumnModel().addColumnModelListener(this);
//        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        setFocusable(false);
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    //
    // Preferences
    //
    
    // Columns size
    private static final String SCH_DESCRIPTION_COL_SIZE    = "DescriptionColSize"  ;
    private static final String SCH_NEXT_OCCURENCE_COL_SIZE = "NextOccurenceColSize";
    private static final String SCH_AMOUNT_COL_SIZE         = "AmountColSize"       ;
    
    private void adjustColumnWidth(TableColumn column, String name) {

        isAdjustingColumnWidth = true;
        
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        
        int defaultWidth = getWidth()/getColumnModel().getColumnCount();
        int width = prefs.getInt(name, defaultWidth);
        column.setPreferredWidth(width);
        
        isAdjustingColumnWidth = false;
    }

    public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
        if (isAdjustingColumnWidth) {
            return;
        }
        int colWidth;
        
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        
//:TODO:
//        colWidth = getColumnModel().getColumn(ScheduledTransactionTableModel_.DESCRIPTION_COL).getWidth();
//        prefs.putInt(SCH_DESCRIPTION_COL_SIZE, colWidth);
//        
//        colWidth = getColumnModel().getColumn(ScheduledTransactionTableModel_.NEXT_OCCURENCE_COL).getWidth();
//        prefs.putInt(SCH_NEXT_OCCURENCE_COL_SIZE, colWidth);
//        
//        colWidth = getColumnModel().getColumn(ScheduledTransactionTableModel_.AMOUNT_COL).getWidth();
//        prefs.putInt(SCH_AMOUNT_COL_SIZE, colWidth);

        super.columnMarginChanged(e);

    }
    
}