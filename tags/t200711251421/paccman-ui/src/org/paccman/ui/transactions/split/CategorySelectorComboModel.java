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

package org.paccman.ui.transactions.split;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import org.paccman.controller.CategoryBaseController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Category;
import org.paccman.paccman.SubCategory;
import static org.paccman.ui.main.ContextMain.*;

/**
 *
 * @author joao
 */
public class CategorySelectorComboModel extends DefaultComboBoxModel implements PaccmanView {
    
    /** Creates a new instance of BankComboModel */
    public CategorySelectorComboModel() {
    }
    
    public void removeElement(Object anObject) {
        if ((anObject != null) && (anObject instanceof CategoryBaseController)) {
            CategoryBaseController categoryCtrl = (CategoryBaseController)anObject;
            categoryCtrl.unregisterView(this);
        }
        
        super.removeElement(anObject);
    }
    
    public void addElement(Object anObject) {
        if (anObject != NULL_LIST_ELEM) {
            CategoryBaseController categoryBaseCtrl = (CategoryBaseController)anObject;
            categoryBaseCtrl.registerView(this);
        }
        
        super.addElement(anObject);
    }
    
    private static final String NULL_LIST_ELEM         = " "            ;
    
    public static Object getNullElement() {
        return NULL_LIST_ELEM;
    }
    
    // PAccMan change notification
    public void onChange(org.paccman.controller.Controller controller) {
        // Reset category list :TODO:optimize this
        removeAllElements();
        
        // Non actual "categories"
        addElement(NULL_LIST_ELEM          );
        
        ArrayList<Category> categories = getDocumentController().getDocument().getCategories();
        
        // Categories and subcategories
        for (Category category: categories) {
            addElement(ControllerManager.getController(category));
            for (SubCategory subCategory: category.getSubCategories()) {
                addElement(ControllerManager.getController(subCategory));
            }
        }
        
    }
    
}
