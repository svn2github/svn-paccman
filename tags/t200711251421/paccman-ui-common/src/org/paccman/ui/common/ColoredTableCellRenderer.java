/*
 * ColoredTableCellRenderer.java
 *
 * Created on 4 décembre 2005, 11:15
 */

package org.paccman.ui.common;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import org.paccman.ui.common.*;

/**
 *
 * @author  joao
 */
public abstract class ColoredTableCellRenderer extends DefaultTableCellRenderer {
    
    abstract public Color getColor(int line, int column, boolean isLineSelected);
    
    /** Creates new form BeanForm */
    public ColoredTableCellRenderer() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel retValue;
        Color color = getColor(row, column, isSelected);
        retValue = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        retValue.setBackground(color);
        return retValue;
    }
    
    
}
