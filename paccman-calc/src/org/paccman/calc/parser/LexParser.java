/*
 * Parser.java
 *
 * Created on 10 septembre 2006, 23:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

import org.paccman.calc.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author joao
 */
public class LexParser {
    
    /**
     *
     */
    public enum Token {
        /**
         * Any digit ('0' to '9')
         */
        DIGIT      ,
        /**
         * Any operator ('+', '-', '/', '*')
         */
        OPERATOR   ,
        /**
         * Left parenthesis ('(')
         */
        LEFT_PAREN ,
        /**
         * Right parenthesis (')')
         */
        RIGHT_PAREN,
        /**
         * Decimal point ('.')
         */
        POINT      ,
        /**
         * Percent ('%')
         */
        PER_CENT
    }
    
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
    
    private CalcToken lastToken   ;
    private CalcToken currentToken;
    
    /**
     *
     * @param c
     * @return
     */
    public CalcToken parseChar(char c) {
        
        if ((c == '.') || ((c >= '0') && (c <= '9'))) {
            
            if (currentToken instanceof OperandToken) {
                // Current token is an operand token: append read char to current.
                OperandToken ot = (OperandToken)currentToken;
                ot.append(c);
                return null;
            } else {
                // New token detected
                OperandToken ot = new OperandToken();
                ot.append(c);
                lastToken = currentToken;
                currentToken = ot;
                return lastToken;
            }
            
        } else {
            
            switch(c) {
            case '+':
            case '-':
            case '/':
            case '*':
                lastToken = currentToken;
                currentToken = new OperatorToken(c);
                return lastToken;

            case '=':
                break;
            
            default:
                ; //:TODO: parse error
            }
        }
        return null; //:TODO:
    }
    
}
