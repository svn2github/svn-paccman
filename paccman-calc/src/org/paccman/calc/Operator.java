/*
 * Operator.java
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
public abstract class Operator {
    /**
     * :TODO:do javadoc
     * @param operand_1 
     * @param operand_2 
     * @return 
     */
    protected abstract BigDecimal compute(BigDecimal operand_1, BigDecimal operand_2);
}
