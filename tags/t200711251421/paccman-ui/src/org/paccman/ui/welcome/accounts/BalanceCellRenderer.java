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

import java.awt.Color;
import org.paccman.ui.common.AmountCellRenderer;

/**
 *
 * @author joao
 */
public class BalanceCellRenderer extends AmountCellRenderer {
    
    /** Creates a new instance of BalanceCellRenderer */
    public BalanceCellRenderer() {
        super(true, Color.RED, true);
        setFont(new java.awt.Font("SansSerif", 1, 36)); //:TODO:
    }
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        java.awt.Component retValue;
        
        // Check if the mouse is on the row
        AccountTable accountTable = (AccountTable)table;
        
        if (accountTable.getModel().isTotalRow(row)) {
            setBackground(accountTable.getTotalRowColor());
        } else {
            setBackground(row == accountTable.getRolloverRow() ? AccountTable.getRolloverRowColor() : AccountTable.getNormalRowColor());
        }
        
        retValue = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return retValue;
    }
    
}
