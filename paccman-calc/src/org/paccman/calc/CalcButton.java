/*
 * CalcButton.java
 *
 * Created on 20 janvier 2006, 07:54
 */

package org.paccman.calc;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author  joao
 */
public class CalcButton extends javax.swing.JButton {
    
    /** Creates new form BeanForm */
    public CalcButton() {
        initComponents();
    }
    
    /**
     * 
     * @param parser 
     * @param key 
     */
    public CalcButton(String key) {
        this.key = key;
        setText(key);
        initComponents();
    }
    
    /**
     * 
     * @param rootPane 
     * @param key 
     */
    public void registerKey(JRootPane rootPane, char key) {
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        StringBuffer sb = new StringBuffer(CalcButton.class.getName());
        sb.append("_KEY_"); sb.append(key);
        String strKey = sb.toString();
        inputMap.put(KeyStroke.getKeyStroke(key), strKey);
        rootPane.getActionMap().put(strKey,new AbstractAction(strKey) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doClick();
            }
        });
    }
    
    /**
     * 
     * @param rootPane 
     * @param keyCode 
     */
    public void registerKey(JRootPane rootPane, int keyCode) {
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        StringBuffer sb = new StringBuffer(CalcButton.class.getName());
        sb.append("_KEYCODE_"); sb.append(keyCode);
        String strKey = sb.toString();
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), strKey);
        rootPane.getActionMap().put(strKey,new AbstractAction(strKey) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                doClick();
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFont(new java.awt.Font("Dialog", 1, 10));
        setFocusable(false);
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    /**
     * Holds value of property key.
     */
    private String key;

    /**
     * Getter for property key.
     * @return Value of property key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Setter for property key.
     * @param key New value of property key.
     */
    public void setKey(String key) {
        this.key = key;
    }

}
