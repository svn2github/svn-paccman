/*
 * CalculatorParser.java
 *
 * Created on 16 février 2006, 07:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc;

import java.awt.Toolkit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author joao
 */
public class CalculatorParser {
    
//:TODO:remove this    Calculator calc;
    
    static class MyFormatter extends Formatter {
        // This method is called for every log records
        public String format(LogRecord rec) {
            StringBuffer buf = new StringBuffer(1000);
            buf.append(rec.getLevel());
            buf.append(' ');
            buf.append(formatMessage(rec));
            buf.append('\n');
            return buf.toString();
        }
        
    }
    
    private static Logger logger = Logger.getLogger(Calculator.class.getName());
    static {
        logger.setLevel(Level.FINEST);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.FINEST);
        ch.setFormatter(new MyFormatter());
        logger.addHandler(ch);
    }
    
    enum Token { DIGIT, POINT, OPERATOR, EQUAL, SIGN, PC, CE }
    enum State { INIT, OPERAND_1, OPERATOR_1, OPERAND_2, OPERATOR_2, EVALUATED }

    /** Creates a new instance of CalculatorParser */
    public CalculatorParser(Calculator calc) {
//:TODO:remove this        this.calc = calc;
    }
    
    State state = State.INIT;
    
    StringBuffer operand_1;
    StringBuffer operand_2;
    
    Operator lastOperator;
    static Operator addOperator       = new AddOperator()     ;
    static Operator subtractOperator  = new SubtractOperator();
    static Operator multOperator      = new MultOperator()    ;
    static Operator divOperator       = new DivOperator()     ;
    static Operator nullOperator      = new NullOperator()    ;
    
    private Operator getOperator(String op) {
        char c = op.charAt(0);
        switch (c) {
            case '+': return addOperator     ;
            case '-': return subtractOperator;
            case '*': return multOperator    ;
            case '/': return divOperator     ;
            default : return nullOperator    ;
        }
    }
    
    private void appendIntDigit(StringBuffer operand, String digit) {
        operand.append(digit);
    }
    
    private void appendDecDigit(StringBuffer operand, String digit) {
        operand.append(digit);
    }
    
    private void appendPoint(StringBuffer operand) {
        operand.append('.');
    }
    
    boolean isDecimal_1 = false;
    boolean isDecimal_2 = false;
    
    private void invalidToken(Token token, String c) {
        logger.warning("Invalid token: " + token);
        Toolkit.getDefaultToolkit().beep();
    }
    
    State doInit(Token token, String key) {
        switch (token) {
            
            case DIGIT:
                isDecimal_1 = false;
                operand_1 = new StringBuffer();
                appendIntDigit(operand_1, key);
                return State.OPERAND_1;
            
            case POINT:
                isDecimal_1 = true;
                operand_1 = new StringBuffer("0.");
                return State.OPERAND_1;
                
        }
        invalidToken(token, key);
        return State.INIT; 
    }
    
    State doOperand1(Token token, String key) {
        switch (token) {
            case DIGIT:
                if (isDecimal_1) {
                    appendDecDigit(operand_1, key);
                } else {
                    if (operand_1.toString().equals("0")) {
                        operand_1 = new StringBuffer(key);
                    } else {
                        appendIntDigit(operand_1, key);
                    }
                }
                return State.OPERAND_1;
                
            case POINT:
                if (isDecimal_1) {
                    invalidToken(token, key);
                } else {
                    appendPoint(operand_1);
                    isDecimal_1 = true;
                }
                return State.OPERAND_1;
                
            case OPERATOR:
                lastOperator = getOperator(key);
                return State.OPERATOR_1;
                
            case SIGN:
                if (operand_1.charAt(0) == '-') {
                    operand_1.replace(0,1,"");
                } else {
                    operand_1.insert(0, '-');
                }
                return State.OPERAND_1;
                
            case CE:
                operand_1 = new StringBuffer("0");
                isDecimal_1 = false;
                return State.OPERAND_1;
                
        }
        invalidToken(token, key);
        return State.OPERAND_1;
    }
    
    State doOperand2(Token token, String key) {
        switch (token) {
            case DIGIT:
                if (isDecimal_2) {
                    appendDecDigit(operand_2, key);
                } else {
                    if (operand_2.toString().equals("0")) {
                        operand_2 = new StringBuffer(key);
                    } else {
                        appendIntDigit(operand_2, key);
                    }
                }
                return State.OPERAND_2;
                
            case POINT:
                if (isDecimal_2) {
                    invalidToken(token, key);
                } else {
                    appendPoint(operand_2);
                    isDecimal_2 = true;
                }
                return State.OPERAND_2;
                
            case SIGN:
                if (operand_2.charAt(0) == '-') {
                    operand_2.replace(0,1,"");
                } else {
                    operand_2.insert(0, '-');
                }
                return State.OPERAND_2;
            
            case OPERATOR:
                operand_1 = lastOperator.compute(operand_1, operand_2);
                operand_2 = null;
                return State.OPERATOR_2;
                
            case EQUAL:
                operand_1 = lastOperator.compute(operand_1, operand_2);
                operand_2 = null;
                return State.EVALUATED;
                
            case PC:
                operand_2 = multOperator.compute(operand_1, operand_2);
                operand_2 = divOperator.compute(operand_2, new StringBuffer("100.0"));
                operand_1 = lastOperator.compute(operand_1, operand_2);
                operand_2 = null;
                return State.EVALUATED;

            case CE:
                operand_2 = new StringBuffer("0");
                isDecimal_2 = false;
                return State.OPERAND_2;
                
        }
        invalidToken(token, key);
        return State.OPERAND_2;
    }
    
    State doOperator1(Token token, String key) {
        switch (token) {
        
            case OPERATOR:
                lastOperator = getOperator(key);
                return State.OPERATOR_1;
                
            case DIGIT:
                isDecimal_2 = false;
                operand_2 = new StringBuffer();
                appendIntDigit(operand_2, key);
                return State.OPERAND_2;
            
            case POINT:
                isDecimal_2 = true;
                operand_2 = new StringBuffer("0.");
                return State.OPERAND_2;
                
        }
        invalidToken(token, key);
        return State.OPERATOR_1;
        
    }
    
    State doOperator2(Token token, String key) {
        switch (token) {
        
            case OPERATOR:
                lastOperator = getOperator(key);
                return State.OPERATOR_2;
                
            case DIGIT:
                isDecimal_2 = false;
                operand_2 = new StringBuffer();
                appendIntDigit(operand_2, key);
                return State.OPERAND_2;
            
            case POINT:
                isDecimal_2 = true;
                operand_2 = new StringBuffer("0.");
                return State.OPERAND_2;
                
        }
        invalidToken(token, key);
        return State.OPERATOR_1;
        
    }
    
    State doEvaluated(Token token, String key) {
        switch ( token) {
            case OPERATOR:
                lastOperator = getOperator(key);
                return State.OPERATOR_1;
                
            case EQUAL:
                return State.EVALUATED;
                
            case DIGIT:
                isDecimal_1 = false;
                operand_1 = new StringBuffer();
                operand_2 = null;
                appendIntDigit(operand_1, key);
                return State.OPERAND_1;
            
            case POINT:
                isDecimal_1 = true;
                operand_1 = new StringBuffer("0.");
                operand_2 = null;
                return State.OPERAND_1;
                
        }
        invalidToken(token, key);
        return State.EVALUATED; 
    }
    
    void processDigit(String c) {
        process(Token.DIGIT, c);
    }
    
    void processPoint() {
        process(Token.POINT, ".");
    }
    
    void processOperator(String c) {
        process(Token.OPERATOR, c);
    }
    
    void processEqual() {
        process(Token.EQUAL, "=");
    }
    
    void processSign() {
        process(Token.SIGN, "");
    }
    
    void processReset() {
        state = State.INIT;
        operand_1 = null;
        operand_2 = null;
//:TODO:replace this to a propertyChange fire        calc.updateDisplay(state, operand_1, operand_2);
        logger.finest("RESET. operand_1 = operand_2 = null");
    }
    
    void processPc() {
        process(Token.PC, "%");
    }
    
    void processClearEntry() {
        process(Token.CE, "");
    }

    void process(Token token, String key) {
        switch (state) {
            case INIT:
                state = doInit(token, key);
                break;
                
            case OPERAND_1:
                state = doOperand1(token, key);
                break;
                
            case OPERAND_2:
                state = doOperand2(token, key);
                break;
                
            case OPERATOR_1:
                state = doOperator1(token, key);
                break;
                
            case OPERATOR_2:
                state = doOperator2(token, key);
                break;
                
            case EVALUATED:
                state = doEvaluated(token, key);
                break;
                
            default:
                assert false: "Invalid state";
                break;
        }
        
//:TODO:replace this with propertyChange firing        calc.updateDisplay(state, operand_1, operand_2);
        
        logger.finest("Current state: " + state + "; token: " + token);
        logger.finest("   operand_1 = " + (operand_1 != null ? operand_1 : "(null)"));
        logger.finest("   operand_2 = " + (operand_2 != null ? operand_2 : "(null)"));
        logger.finest("   last_operator = " + (lastOperator != null ? lastOperator : "(null)"));
        
    }
    
}
