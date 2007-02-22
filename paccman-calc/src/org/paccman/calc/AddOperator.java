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
public class AddOperator extends Operator {
    
    /** Creates a new instance of AddOperator */
    public AddOperator() {
    }
    
    public BigDecimal compute(BigDecimal operand_1, BigDecimal operand_2) {
        return operand_1.add(operand_2);
    }

    public String toString() {
        return "+";
    }
}
