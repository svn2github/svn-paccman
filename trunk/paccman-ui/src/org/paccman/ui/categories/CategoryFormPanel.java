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

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.paccman.controller.CategoryController;
import org.paccman.controller.SubCategoryController;
import org.paccman.paccman.Category;
import org.paccman.paccman.SubCategory;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;/**
 *
 * @author  joao
 */
import org.paccman.ui.main.Main;
public class CategoryFormPanel extends PaccmanForm implements ListSelectionListener {
    
    /**
     * Creates new form CategoryFormPanel
     */
    public CategoryFormPanel() {
        initComponents();
        setEditMode(false);
        subCategoryList.addListSelectionListener(this);
    }
    
    public void setForm(org.paccman.controller.Controller controller) {
        Category category = ((CategoryController)controller).getCategory();
        nameEdt.setText(category.getName());
        incomeRb.setSelected(category.isIncome());
        expenseRb.setSelected(!category.isIncome());
        descriptionEdt.setText(category.getDescription());
        Object o = subCategoryList.getModel(); //:DEBUG:
        DefaultListModel dlm = (DefaultListModel)(subCategoryList.getModel());
        dlm.clear();
        for (SubCategory sc: category.getSubCategories()) {
            dlm.addElement(sc.getName());
        }
    }
    
    public void setEditMode(boolean editing) {
        nameEdt.setEnabled(editing);
        expenseRb.setEnabled(editing);
        incomeRb.setEnabled(editing);
        descriptionEdt.setEnabled(editing);
        subCategoryList.setEnabled(editing);
        newBtn.setEnabled(editing);
    }
    
    private DefaultListModel getSubCategoriesListModel() {
        return (DefaultListModel)(subCategoryList.getModel());
    }
    
    public void registerToDocumentCtrl() {
    }
    
    public void clearForm() {
        nameEdt.setText("");
        expenseRb.setSelected(true);
        descriptionEdt.setText("");
        getSubCategoriesListModel().clear();
    }
    
    public void getForm(org.paccman.controller.Controller controller) throws BadInputException {
        CategoryController categoryCtrl = (CategoryController)controller;
        
        String name = nameEdt.getText();
        
        // In case of a new category, check that the name is not already used for another category
        if (editingNew && (Main.getDocumentCtrl().getDocument().getCategory(name) != null)) {
            throw new BadInputException("A category with the same name already exists", nameEdt);
        }
        
        boolean isExpense = expenseRb.isSelected();
        boolean isIncome = incomeRb.isSelected();
        String description = descriptionEdt.getText();
        int nbSubCategories = getSubCategoriesListModel().size();
        SubCategoryController[] subCategoriesArray = new SubCategoryController[nbSubCategories];
        for (int i = 0; i < getSubCategoriesListModel().size() ; i++) {
            subCategoriesArray[i] = new SubCategoryController(categoryCtrl.getCategory());
            subCategoriesArray[i].getSubCategory().setName((String) getSubCategoriesListModel().get(i));
        }
        
        if (! isExpense && ! isIncome) {
            throw new BadInputException("Select 'Expense' or 'Income' type", expenseRb);
        } else {
            categoryCtrl.getCategory().setName(name);
            categoryCtrl.getCategory().setIncome(isIncome);
            categoryCtrl.getCategory().setDescription(description);
            
            categoryCtrl.getCategory().getSubCategories().clear();
            for (int i = 0 ; i < nbSubCategories ; i++) {
                categoryCtrl.getCategory().addSubCategory(subCategoriesArray[i].getSubCategory());
            }
            
        }
        
    }
    
    public org.paccman.controller.Controller getNewController() {
        return new CategoryController();
    }
    
    public void valueChanged(ListSelectionEvent e) {
        //Ignore extra messages.
        if (e.getValueIsAdjusting()) return;
        
        JList list = (JList)e.getSource();
        
        boolean selectionEmpty = (list.getSelectedIndex() == -1);
        
        deleteBtn.setEnabled(! selectionEmpty);
        renameBtn.setEnabled(! selectionEmpty);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        categoryTypeGrp = new javax.swing.ButtonGroup();
        nameLbl = new javax.swing.JLabel();
        nameEdt = new javax.swing.JTextField();
        expenseRb = new javax.swing.JRadioButton();
        incomeRb = new javax.swing.JRadioButton();
        descriptionLbl = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionEdt = new javax.swing.JTextArea();
        subCategoriesPanel = new javax.swing.JPanel();
        subCategoryListScrollPane = new javax.swing.JScrollPane();
        subCategoryList = new javax.swing.JList();
        newBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        renameBtn = new javax.swing.JButton();

        nameLbl.setText("Name");

        categoryTypeGrp.add(expenseRb);
        expenseRb.setText("Expense");
        expenseRb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        expenseRb.setMargin(new java.awt.Insets(0, 0, 0, 0));

        categoryTypeGrp.add(incomeRb);
        incomeRb.setText("Income");
        incomeRb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        incomeRb.setMargin(new java.awt.Insets(0, 0, 0, 0));

        descriptionLbl.setText("Desription");

        descriptionEdt.setColumns(20);
        descriptionEdt.setRows(5);
        descriptionScrollPane.setViewportView(descriptionEdt);

        subCategoriesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Sub Categories"));
        subCategoryList.setModel(new DefaultListModel());
        subCategoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        subCategoryListScrollPane.setViewportView(subCategoryList);

        newBtn.setText("New");
        newBtn.setEnabled(false);
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.setEnabled(false);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        renameBtn.setText("Rename");
        renameBtn.setEnabled(false);
        renameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout subCategoriesPanelLayout = new org.jdesktop.layout.GroupLayout(subCategoriesPanel);
        subCategoriesPanel.setLayout(subCategoriesPanelLayout);
        subCategoriesPanelLayout.setHorizontalGroup(
            subCategoriesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, subCategoriesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(subCategoryListScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(subCategoriesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, renameBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(newBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, deleteBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        subCategoriesPanelLayout.setVerticalGroup(
            subCategoriesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, subCategoriesPanelLayout.createSequentialGroup()
                .add(subCategoriesPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, subCategoriesPanelLayout.createSequentialGroup()
                        .add(newBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deleteBtn)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(renameBtn))
                    .add(subCategoryListScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, subCategoriesPanel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(descriptionLbl)
                            .add(nameLbl))
                        .add(31, 31, 31)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(expenseRb)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(incomeRb))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, descriptionScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, nameEdt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(nameEdt)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, nameLbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(expenseRb)
                    .add(incomeRb))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(descriptionScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 104, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(descriptionLbl))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(subCategoriesPanel)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {descriptionLbl, expenseRb, incomeRb, nameEdt, nameLbl}, org.jdesktop.layout.GroupLayout.VERTICAL);

    }// </editor-fold>//GEN-END:initComponents
    
    private void renameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameBtnActionPerformed
        assert subCategoryList.getSelectedIndex() != -1: "Disbale button should be disable in no subcategory is selected";
        String subCategoryName = (String)JOptionPane.showInputDialog(this, "Enter the new sub-category name", "Sub-category",
                JOptionPane.QUESTION_MESSAGE, null, null, subCategoryList.getSelectedValue()); //:TODO:
        if (subCategoryName != null) {
            getSubCategoriesListModel().set(subCategoryList.getSelectedIndex(), subCategoryName);
        }
    }//GEN-LAST:event_renameBtnActionPerformed
    
    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        assert subCategoryList.getSelectedIndex() != -1: "Disable button should be disable in no subcategory is selected";
        getSubCategoriesListModel().removeElementAt(subCategoryList.getSelectedIndex());
        //:TODO: CLEAR transaction with this category
    }//GEN-LAST:event_deleteBtnActionPerformed
    
    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        String subCategoryName = (String)JOptionPane.showInputDialog(this, "Enter the new sub-category name", "Sub-category",
                JOptionPane.QUESTION_MESSAGE, null, null, "NewCategory" ); //:TODO:
        if (subCategoryName != null) {
            getSubCategoriesListModel().addElement(subCategoryName);
        }
    }//GEN-LAST:event_newBtnActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup categoryTypeGrp;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextArea descriptionEdt;
    private javax.swing.JLabel descriptionLbl;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JRadioButton expenseRb;
    private javax.swing.JRadioButton incomeRb;
    private javax.swing.JTextField nameEdt;
    private javax.swing.JLabel nameLbl;
    private javax.swing.JButton newBtn;
    private javax.swing.JButton renameBtn;
    private javax.swing.JPanel subCategoriesPanel;
    private javax.swing.JList subCategoryList;
    private javax.swing.JScrollPane subCategoryListScrollPane;
    // End of variables declaration//GEN-END:variables
    
}
