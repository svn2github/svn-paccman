/*
 * OperatorToken.java
 *
 * Created on 13 oct. 2007, 20:28:18
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
   static public int getOperatorPriority(char c) {
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
     * @param parenLvl 
     * @param operator
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
     * @return
     */
    public BigDecimal evaluate(BigDecimal oper1, BigDecimal oper2) {
        switch (operator) {
            case LexToken.MULT_CHAR:
                return oper1.multiply(oper2);
            case LexToken.PLUS_CHAR:
                return oper1.add(oper2);
            default:
                throw new IllegalStateException("Unknown operator: " + Character.toString(operator));
        }
    }
}