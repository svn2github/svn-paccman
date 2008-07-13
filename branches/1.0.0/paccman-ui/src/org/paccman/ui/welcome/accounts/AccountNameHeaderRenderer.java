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
import java.awt.Point;
import javax.swing.table.TableCellRenderer;
import org.paccman.controller.AccountController;

/**
 *
 * @author  joao
 */
public class AccountNameHeaderRenderer extends javax.swing.JPanel implements TableCellRenderer {
    
    /** Creates new form AccountNameCellRenderer */
    public AccountNameHeaderRenderer() {
        initComponents();

        accountNameLbl.setText("Account");
        accountNumberLbl.setText("Number");
        
        // Background color
        setBackground(AccountTable.getHeaderColorBkg());
        
    }
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        return this;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        accountNameLbl = new javax.swing.JLabel();
        accountNumberLbl = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(0, 1));

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        accountNameLbl.setFont(new java.awt.Font("Tahoma", 1, 14));
        add(accountNameLbl);

        accountNumberLbl.setFont(new java.awt.Font("Tahoma", 0, 10));
        add(accountNumberLbl);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accountNameLbl;
    private javax.swing.JLabel accountNumberLbl;
    // End of variables declaration//GEN-END:variables
    
}
