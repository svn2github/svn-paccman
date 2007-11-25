/*
 * ITableCellRenderer.java
 *
 * Created on 6 décembre 2005, 16:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.common.table.renderer;

import java.awt.Color;

/**
 *
 * @author jfer
 */
public interface ITableCellRenderer {
    public Color getBkColor(int row, int line, boolean isSelected);
}
