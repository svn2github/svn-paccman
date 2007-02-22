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

package org.paccman.ui.categories;

import javax.swing.JTree;
import org.paccman.controller.PaccmanView;
import org.paccman.ui.main.Main;

/**
 *
 * @author  joao
 */
public class CategoryTree extends JTree implements PaccmanView {
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setRootVisible(false);
    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public CategoryTreeModel getModel() {
	return (CategoryTreeModel)super.getModel();
    }
    
    /** Creates a new instance of CategoryTree */
    public CategoryTree() {
        initComponents();
	setModel(new CategoryTreeModel());
    }

    public void registerToDocumentCtrl() {
        Main.getDocumentCtrl().registerView(this);
    }
    public void onChange(org.paccman.controller.Controller controller) {
        repaint();
    }
    
}
