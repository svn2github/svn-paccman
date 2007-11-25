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
import java.util.Stack;

/**
 *
 * @author joao
 */
public class YaccParser {

    Stack<BigDecimal> operandStack = new Stack<BigDecimal>();
    Stack<OperatorToken> operatorStack = new Stack<OperatorToken>();

    /**
     * 
     * @param mathContext
     */
    public YaccParser(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    /**
     *
     * @param token
     */
    public void parseOperand(BigDecimal token) {
        operandStack.push(token);
    }

    /**
     *
     * @param token
     */
    public void parseOperator(OperatorToken token) {
        parseEvaluate(token.getPriority());
        operatorStack.push(token);
    }
    
    /**
     * 
     * @param priority 
     */
    public void parseClosePar(int priority) {
       parseEvaluate(priority); 
    }

    /**
     * Parse the whole expression: end of expression received
     */
    public void parseEnd() {
        parseEvaluate(0);
    }

    /**
     * 
     * @param token 
     */
    public void replaceOperator(OperatorToken token) {
        assert  ! operatorStack.isEmpty();
        operatorStack.pop();
        operatorStack.push(token);
    }

    void parseClearEntry() {
        assert  ! operandStack.isEmpty();
        operandStack.pop();
        operandStack.push(BigDecimal.ZERO);
    }

    void parseNegate() {
        assert  ! operandStack.isEmpty();
        operandStack.push(operandStack.pop().negate());
    }
    
    private MathContext mathContext;
    
    /**
     * Unstack until the specified priority
     * @param priority 
     */
    private void parseEvaluate(int priority) {
        OperatorToken ot;
        while (!operatorStack.isEmpty() && (operatorStack.peek().getPriority() >= priority)) {
            ot = operatorStack.pop();
            BigDecimal oper2nd = operandStack.pop();
            BigDecimal oper1st = operandStack.pop();
            oper1st = ot.evaluate(oper1st, oper2nd, mathContext);
            operandStack.push(oper1st);
        }
    }

    /**
     * Returns opeand in the top of stack, the one to be displayed.
     * @return
     */
    public BigDecimal getTopOperand() {
        return operandStack.peek();
    }

    void reset() {
        operandStack.clear();
        operatorStack.clear();
    }
}