/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AccountTable.java
 *
 * Created on 14 août 2008, 08:32:56
 */

package org.paccman.ui.welcome.accounts;

import org.jdesktop.swingx.JXTable;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class AccountTable extends JXTable {

    /** Creates new form BeanForm */
    public AccountTable() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accountHeaderRenderer1 = new org.paccman.ui.welcome.accounts.AccountHeaderRenderer();

        accountHeaderRenderer1.setName("accountHeaderRenderer1"); // NOI18N

        setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        setName("Form"); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.paccman.ui.welcome.accounts.AccountHeaderRenderer accountHeaderRenderer1;
    // End of variables declaration//GEN-END:variables

    

}
