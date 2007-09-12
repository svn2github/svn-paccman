/*
 * DigitCalcButton.java
 *
 * Created on 30 janvier 2006, 20:20
 */

package org.paccman.calc;

/**
 *
 * @author  joao
 */
public class SignButton extends CalcButton {
    static final String SIGN_CHAR = "±";
    
    /** Creates new form BeanForm */
    public SignButton() {
        initComponents();
    }
    
    public SignButton(Calculator calc) {
        super(calc, SIGN_CHAR);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        
    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
