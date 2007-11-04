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

package org.paccman.ui.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.paccman.ui.common.*;

/**
 *
 * @author  joao
 */
public class AmountTextField extends javax.swing.JFormattedTextField {
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
    }// </editor-fold>//GEN-END:initComponents
    
    static DefaultFormatterFactory formatterFactory;
    static AmountVerifier verifier;
    static DecimalFormat amountEditFormat;
    
    public BigDecimal getValue() {
        return (BigDecimal)super.getValue();
    }
    
    // Static initialisation
    static {
        // Verifier
        verifier = new AmountVerifier();
        
        // Display format
        DecimalFormat amountDisplayFormat = (DecimalFormat)NumberFormat.getCurrencyInstance();
        amountDisplayFormat.setMinimumFractionDigits(2);
        amountDisplayFormat.setParseBigDecimal(true);
        
        // Edit format
        amountEditFormat = (DecimalFormat)NumberFormat.getNumberInstance();
        amountEditFormat.setParseBigDecimal(true);
        
        formatterFactory = new DefaultFormatterFactory(
                new NumberFormatter(AmountFormat.getDisplayFormat()),
                new NumberFormatter(AmountFormat.getDisplayFormat()),
                new NumberFormatter(AmountFormat.getEditFormat()));
        
    }
    
    static class AmountVerifier extends InputVerifier {
        public boolean verify(javax.swing.JComponent input) {
            try {
                JFormattedTextField tf = (JFormattedTextField)input;
                ParsePosition pos = new ParsePosition(0);
                String text = tf.getText();
                amountEditFormat.parse(text, pos);
                return pos.getIndex() == text.length();
            }
            catch (Exception e) {
                return false;
            }
        }
        
        public boolean shouldYieldFocus(JComponent input) {
            return verify(input);
        }
    }
    
    /**
     * Creates a new instance of AmountTextField_
     */
    public AmountTextField() {
        initComponents();
        setFormatterFactory(formatterFactory);
        setInputVerifier(verifier);
    }

    public void setText(String t) {
        super.setText(t);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}