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

import java.awt.BorderLayout;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author  joao
 */
public class AccountsPanel extends javax.swing.JPanel {
    
    /** Creates new form AccountsPanel */
    public AccountsPanel() {
        initComponents();
    }

    public void registerToDocumentCtrl() {
        getDocumentController().registerView(accountTable.getModel());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        accountTable = new org.paccman.ui.welcome.accounts.AccountTable();

        setLayout(new java.awt.BorderLayout());

        add(accountTable.getTableHeader(), BorderLayout.NORTH);
        add(accountTable, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public org.paccman.ui.welcome.accounts.AccountTable accountTable;
    // End of variables declaration//GEN-END:variables
    
}
