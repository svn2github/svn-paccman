/*
 * SplitFormPanel.java
 *
 * Created on 15 décembre 2005, 13:05
 */

package org.paccman.ui.transactions.form;

import org.paccman.controller.Controller;
import org.paccman.ui.form.BadInputException;
import org.paccman.ui.form.PaccmanForm;

/**
 *
 * @author  jfer
 */
public class SplitFormPanel extends PaccmanForm {
    
    /** Creates new form SplitFormPanel */
    public SplitFormPanel() {
        initComponents();
    }

    public void setForm(Controller controller) {
        //:TODO:
    }

    public void getForm(Controller controller) throws BadInputException {
        //:TODO:
    }

    public void setEditMode(boolean editing) {
        //:TODO:
    }

    public void registerToDocumentCtrl() {
        //:TODO:
    }

    public Controller getNewController() {
        //:TODO:
        return null;
    }

    public void clearForm() {
        //:TODO:
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
