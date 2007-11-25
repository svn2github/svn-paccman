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

import java.util.ArrayList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Category;
import org.paccman.paccman.SubCategory;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author jfer
 */
public class CategoryTreeModel implements TreeModel, PaccmanView {
    
    ArrayList<Category> categories;
    
    public void registerToDocumentCtrl() {
        getDocumentController().registerView(this);
    }
    
    /** Creates a new instance of CategoryTreeModel */
    public CategoryTreeModel() {
    }
    
    public boolean isLeaf(Object node) {
        return node instanceof SubCategory;
    }
    
    public int getChildCount(Object parent) {
        if (parent == categories) {
            return categories.size();
        } else {
            Category category = (Category)parent;
            return category.getSubCategories().size();
        }
    }
    
    public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
        //:TODO:
    }
    
    public Object getChild(Object parent, int index) {
        if (parent == categories) {
            return categories.get(index);
        } else {
            Category category= (Category)parent;
            return (SubCategory)(category.getSubCategories().get(index));
        }
    }
    
    public Object getRoot() {
        return categories;
    }
    
    public int getIndexOfChild(Object parent, Object child) {
        return categories.indexOf(child);
    }
    
    public void onChange(org.paccman.controller.Controller controller) {
        categories = getDocumentController().getDocument().getCategories();
        fireTreeStructureChanged();
    }
    
    
    // ------------------------------------------------------------------------
    // --  Tree model listener function implementation                       --
    // ------------------------------------------------------------------------
    
    private ArrayList<TreeModelListener> listenerList = new ArrayList<TreeModelListener>();
    
    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
        listenerList.remove(l);
    }
    
    public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
        listenerList.add(l);
    }
    
    protected void fireTreeStructureChanged() {
        int len = listenerList.size();
        TreeModelEvent e = new TreeModelEvent(this, new Object[] {categories});
        for (int i = 0; i < len; i++) {
            ((TreeModelListener)listenerList.get(i)).
                    treeStructureChanged(e);
        }
    }
    
}
