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
     *
     * @param operand_1
     * @param operand_2
     * @return The result of <code>operand_1 <b>op<b> operand_2<code> where <code><b>op<b><code> is this operator.
     */
    protected abstract BigDecimal compute(BigDecimal operand_1, BigDecimal operand_2);
}