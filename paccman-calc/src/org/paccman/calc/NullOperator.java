/*
 * AddOperator.java
 *
 * Created on 31 janvier 2006, 15:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc;

import java.math.BigDecimal;

/**
 *
 * @author public
 */
public class NullOperator extends Operator {
    
    /** Creates a new instance of AddOperator */
    public NullOperator() {
    }
    
    public BigDecimal compute(BigDecimal operand_1, BigDecimal operand_2) {
        return BigDecimal.ZERO;
    }
    public String toString() {
        return "(null)";
    }
}
