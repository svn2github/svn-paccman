/*
 
    Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
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

package org.paccman.calc.parser;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author joao
 */
public class OperatorToken {

    /**
     *
     */
    public static int PLUS_PRIORITY = 0;
    /**
     *
     */
    public static int MULT_PRIORITY = 1;

    /**
     *
     * @param c
     * @return
     */
    public static int getOperatorPriority(char c) {
        switch (c) {
            case LexToken.PLUS_CHAR:
            case LexToken.MINUS_CHAR:
                return PLUS_PRIORITY;
            case LexToken.MULT_CHAR:
            case LexToken.DIV_CHAR:
                return MULT_PRIORITY;
        }
        throw new IllegalStateException("Invalid operator: " + Character.toString(c));
    }
    private int priority;
    private char operator;

    /**
     *
     * @return
     */
    public int getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     *
     * @param operator
     * @param parenPrioAdjust 
     *
     */
    public OperatorToken(char operator, int parenPrioAdjust) {
        this.priority = getOperatorPriority(operator) + parenPrioAdjust;
        this.operator = operator;
    }
    
    /**
     *
     * @param oper1
     * @param oper2
     * @param mathContext 
     * @return
     */
    public BigDecimal evaluate(BigDecimal oper1, BigDecimal oper2, MathContext mathContext) {
        switch (operator) {
            case LexToken.PLUS_CHAR:
                return oper1.add(oper2);
            case LexToken.MINUS_CHAR:
                return oper1.subtract(oper2);
            case LexToken.MULT_CHAR:
                return oper1.multiply(oper2);
            case LexToken.DIV_CHAR:
                return oper1.divide(oper2, mathContext);
            default:
                throw new IllegalStateException("Unknown operator: " + Character.toString(operator));
        }
    }
}