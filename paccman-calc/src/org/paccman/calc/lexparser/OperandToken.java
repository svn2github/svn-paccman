/*
 * OperandToken.java
 *
 * Created on 26 février 2007, 07:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.lexparser;

import java.math.BigDecimal;

/**
 *
 * @author joao
 */
public class OperandToken extends CalcToken {
    
    /** Creates a new instance of OperandToken */
    public OperandToken() {
    }
    
    private boolean decimalPoint = false;
    /**
     * 
     */
    public enum OperandSign { 
        /**
         * 
         */
        POSITIVE, 
        /**
         * 
         */
        NEGATIVE;
                
        @Override
        public String toString() {
            return this == POSITIVE ? "" : "-";
        }
    };
    
    OperandSign sign = OperandSign.POSITIVE;
    
    /**
     * 
     */
    public void invertSign() {
        sign = (sign == OperandSign.POSITIVE) ? OperandSign.NEGATIVE : OperandSign.POSITIVE;
    }

    /**
     * 
     * @return 
     */
    public boolean hasDecimalPoint() {
        return decimalPoint;
    }
    
    /**
     * :TODO:
     * @param c 
     */
    public void append(char c) {
        // If first character is decimal point, prepend '0'
        if ((c == '.') && (token.length() == 0)) {
            token.append('0');
        }
        token.append(c);
        decimalPoint = decimalPoint || (c == '.');
    }
    
    /**
     * :TODO:
     * @return 
     */
    public BigDecimal getValue() {
        return new BigDecimal(token.toString()).multiply(new BigDecimal(sign == OperandSign.POSITIVE ? 1 : -1));
    }
    
    @Override
    public String toString() {
        return "OPERAND [" + sign.toString() + token.toString() + "]";
    }
    
    @Override
    public String tokenString() {
        return sign.toString() + token.toString();
    }
    
}
