/*
 * YaccParser.java
 *
 * Created on 12 oct. 2007, 22:37:07
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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