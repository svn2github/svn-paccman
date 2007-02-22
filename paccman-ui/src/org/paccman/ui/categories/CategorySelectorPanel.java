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

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import org.paccman.controller.CategoryController;
import org.paccman.controller.Controller;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.PaccmanView;
import org.paccman.paccman.Category;
import org.paccman.paccman.CategoryBase;
import org.paccman.ui.main.Main;
import org.paccman.ui.selector.ControllerSelectionButton;
import org.paccman.ui.selector.ControllerSelectionListener;

/**
 *
 * @author  joao
 */
public class CategorySelectorPanel extends javax.swing.JPanel implements PaccmanView, ActionListener {
    
    CategoryController  categoryCtrl ; // label of selected category
    
    Vector<ControllerSelectionButton> selButtons = new Vector<ControllerSelectionButton>();
    
    Vector<ControllerSelectionListener> categorySelectionListener = new Vector<ControllerSelectionListener>();
    
    public void setCategoryCtrl(CategoryController categoryCtrl) {
        if (this.categoryCtrl == categoryCtrl) {
            return;
        }
        if (this.categoryCtrl != null) {
            this.categoryCtrl.unregisterView(this);
        }
        this.categoryCtrl = categoryCtrl;
        categoryCtrl.registerView(this);
        selectedCategoryLbl.setText(categoryCtrl.getCategory().getName());
    }
    
    public void addListener(ControllerSelectionListener listener) {
        categorySelectionListener.add(listener);
    }
    
    /**
     * Creates new form CategorySelectorPanel
     */
    public CategorySelectorPanel() {
        initComponents();
    }
    
    public void registerToDocumentCtrl() {
        Main.getDocumentCtrl().registerView(this);
    }
    
    public void onChange(Controller controller) {
        if (controller == Main.getDocumentCtrl()) {
            
            // Remove all buttons
            for (ControllerSelectionButton acb: selButtons) {
                acb.getController().unregisterView(acb);
                categoryButtonsPanel.remove(acb);
            }
            
            // Clear button list
            selButtons.clear();
            
            // Add the new buttons
            ArrayList<Category> categorys = Main.getDocumentCtrl().getDocument().getCategories();
            for (CategoryBase category: categorys) {
                ControllerSelectionButton asb = new ControllerSelectionButton((CategoryController)ControllerManager.getController(category));
                asb.addActionListener(this);
                categoryButtonsPanel.add(asb);
                selButtons.add(asb);
            }
            
            validate();
        } else if (controller == categoryCtrl) {
            selectedCategoryLbl.setText(categoryCtrl.getCategory().getName());
        }
        
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        ControllerSelectionButton asb = (ControllerSelectionButton)e.getSource();
        if (e != null) {
            if (selectCategory((CategoryController)asb.getController())) {
                setCategoryCtrl((CategoryController)asb.getController());
            }
        }
    }
    
    private boolean selectionEnabled() {
        // Check if all listeners allow the selection
        for (ControllerSelectionListener asl: categorySelectionListener) {
            if (! asl.selectionEnabled()) {
                // if not return
                return false;
            }
        }
        return true;
    }
    
    public boolean selectCategory(CategoryController category) {
        if (selectionEnabled()) {
            // Notify all seletino listeners
            for (ControllerSelectionListener asl: categorySelectionListener) {
                asl.controllerSelected(category);
            }
            setCategoryCtrl(category);
            return true;
        }
        else {
            return false;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        categoryButtonsPanel = new javax.swing.JPanel();
        selectedCategoryLbl = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        categoryButtonsPanel.setLayout(new java.awt.GridLayout(0, 1));

        selectedCategoryLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectedCategoryLbl.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null), new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        categoryButtonsPanel.add(selectedCategoryLbl);

        add(categoryButtonsPanel, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel categoryButtonsPanel;
    private javax.swing.JLabel selectedCategoryLbl;
    // End of variables declaration//GEN-END:variables
    
}
