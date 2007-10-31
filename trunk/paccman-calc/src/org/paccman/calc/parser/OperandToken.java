/*
 * OperandToken.java
 * 
 * Created on 13 oct. 2007, 20:27:59
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

import java.math.BigDecimal;

/**
 *
 * @author joao
 */
public class OperandToken {
    BigDecimal value;

    public OperandToken(BigDecimal value) {
        this.value = value;
    }
    
}
