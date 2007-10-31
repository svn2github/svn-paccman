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
import java.awt.Component;
import java.awt.ComponentOrientation;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;/**
 *
 * @author joao
 */
public class AccountTableHeaderRenderer extends JLabel implements TableCellRenderer {
    public AccountTableHeaderRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        
        // This call is needed because DefaultTableCellRenderer calls setBorder()
        // in its constructor, which is executed after updateUI()
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }
    
    public void updateUI() {
        super.updateUI();
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean selected, boolean focused, int row, int column) {
        JTableHeader h = table != null ? table.getTableHeader() : null;
        
        if (h != null) {
            setEnabled(h.isEnabled());
            setComponentOrientation(h.getComponentOrientation());
            
            setForeground(Color.GREEN); //h.getForeground());
            setBackground(Color.YELLOW); //h.getBackground());
            setFont(h.getFont());
        } else {
            /* Use sensible values instead of random leftover values from the last call */
            setEnabled(true);
            setComponentOrientation(ComponentOrientation.UNKNOWN);
            
            setForeground(UIManager.getColor(Color.RED)); // :TODO:"TableHeader.foreground"));
            setBackground(UIManager.getColor(Color.BLUE)); //"TableHeader.background"));
            setFont(UIManager.getFont("TableHeader.font"));
        }
        
        setText(value.toString());
        return this;
    }
}

