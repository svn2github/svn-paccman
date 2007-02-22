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
    public StringBuffer compute(StringBuffer operand_1, StringBuffer operand_2) {
        BigDecimal result = compute(new BigDecimal(new String(operand_1)), new BigDecimal(new String(operand_2)));
        return new StringBuffer(result.stripTrailingZeros().toPlainString());
    }
    protected abstract BigDecimal compute(BigDecimal operand_1, BigDecimal operand_2);
}
