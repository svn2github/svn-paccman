/*
 * SplitTableCellRenderer.java
 *
 * Created on 6 décembre 2005, 16:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.transactions.split;

import java.awt.Color;
import org.paccman.ui.common.table.renderer.ITableCellRenderer;
import org.paccman.ui.common.table.renderer.TableCellRenderer;

/**
 *
 * @author jfer
 */
public class SplitTableCellRenderer implements ITableCellRenderer {
    
    private final TableCellRenderer cellRenderer;
    
    /**
     * Creates a new instance of SplitTableCellRenderer
     */
    public SplitTableCellRenderer(TableCellRenderer cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public Color getBkColor(int row, int line, boolean isSelected) {
        if (isSelected) {
            return new Color(255,200,200); 
        } else if (row % 2 == 0) {
            return new Color(240,240,255); 
        } else {
            return new Color(240,255,240);
        }
    }
    
}
