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
import java.math.RoundingMode;
import org.paccman.calc.Calculator;
import org.paccman.calc.Operator;

/**
 *
 * @author public
 */
public class DivOperator extends Operator {
    
    /** Creates a new instance of AddOperator */
    public DivOperator() {
    }
    
    public BigDecimal compute(BigDecimal operand_1, BigDecimal operand_2) {
        return operand_1.divide(operand_2, Calculator.getDecPrecision(), RoundingMode.HALF_DOWN);
    }

    public String toString() {
        return "/";
    }
}