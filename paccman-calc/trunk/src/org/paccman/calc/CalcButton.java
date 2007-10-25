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
     * @param token The token to be passed to the calculator parser.
     * @param keyCodes
     * @param keyChars 
     * 
     * 
     */
    public CalcButton(char token, int[] keyCodes, char[] keyChars) {
        this.token = token;
        this.keyChars = keyChars;
        this.keyCodes = keyCodes;
        initComponents();
    }

    /**
     * 
     * @param rootPane 
     * 
     */
    @SuppressWarnings("serial")
    public void registerKey(JRootPane rootPane) {
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Register KeyCodes
        for (int keyCode : keyCodes) {
            StringBuffer sb = new StringBuffer(CalcButton.class.getName());
            sb.append("_KEYCODE_");
            sb.append(keyCode);
            String strKey = sb.toString();
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), strKey);
            rootPane.getActionMap().put(strKey, new AbstractAction(strKey) {

                        @Override
                public void actionPerformed(ActionEvent actionEvent) {
                            doClick();
                        }
                    });
        }

        // Register KeyChars
        for (char keyChar : keyChars) {
            StringBuffer sb = new StringBuffer(CalcButton.class.getName());
            sb.append("_KEYCODE_");
            sb.append(keyChar);
            String strKey = sb.toString();
            inputMap.put(KeyStroke.getKeyStroke(keyChar), strKey);
            rootPane.getActionMap().put(strKey, new AbstractAction(strKey) {

                        @Override
                public void actionPerformed(ActionEvent actionEvent) {
                            doClick();
                        }
                    });
        }
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

    private int[] keyCodes;
    private char[] keyChars;

    private char token;

    public char getToken() {
        return token;
    }
}
