/*
 * Parser.java
 *
 * Created on 10 septembre 2006, 23:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.lexparser;

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
    
    private int parenLevelCnt;
    
    /**
     * 
     */
    public LexParser() {
    }
    
    /**
     * 
     */
    public class LexParseException extends Exception {
        /**
         * 
         * @param message 
         */
        public LexParseException(String message) {
            super(message);
        }
    }
    
    /**
     * 
     */
    public class InvalidCharException extends LexParseException {
        /**
         * 
         * @param c 
         */
        public InvalidCharException(char c) {
            super("Unexpected character: '" + Character.toString(c) + "'");
            this.c = c;
        }

        /**
         * 
         * @param c 
         * @param msg 
         */
        public InvalidCharException(char c, String msg) {
            super("Unexpected character: '" + Character.toString(c) + "' [" + msg + "]");
            this.c = c;
        }

        private char c;

        /**
         * 
         * @return 
         */
        public char getC() {
            return c;
        }
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
    
    private void switchToNewToken(CalcToken token) {
        lastToken = currentToken;
        currentToken = token;
    }
    
    /**
     *
     * @param c
     * @return
     * @throws org.paccman.calc.parser.LexParser.LexParseException 
     * 
     */
    public CalcToken parseChar(char c) throws LexParseException {
        if ((c == '±') || (c == '.') || ((c >= '0') && (c <= '9'))) {
            if (currentToken instanceof OperandToken) {
                // Current token is an operand token: append read char to current.
                OperandToken ot = (OperandToken)currentToken;
                if ((c == '.') && ot.hasDecimalPoint()) {
                    throw new InvalidCharException(c);
                }
                if (c == '±') {
                    ot.invertSign();
                } else {
                    ot.append(c);
                }
                return null;
            } else {
                if (c == '±') {
                    throw new InvalidCharException(c);
                }
                // New token detected
                OperandToken ot = new OperandToken();
                ot.append(c);
                switchToNewToken(ot);
                return lastToken;
            }
            
        } else {
            
            switch(c) {
            case '(':
                parenLevelCnt++;
                switchToNewToken(LeftParen.getLeftParen());
                return lastToken;
                
            case ')':
                if (parenLevelCnt == 0) {
                    throw  new InvalidCharException(c, "No matching '('");
                }
                parenLevelCnt--;
                switchToNewToken(RightParen.getRightParen());
                return lastToken;
                
            case '+':
            case '-':
            case '/':
            case '*':
                switchToNewToken(new OperatorToken(c));
                return lastToken;
                
            case '=':
                if (parenLevelCnt > 0) {
                    throw  new InvalidCharException(c, "Missing ')'");
                }
                switchToNewToken(EndToken.getEndToken());
                return lastToken; 
                
            case (char)-1:
                switchToNewToken(null);
                return lastToken;
                
            default:
                throw new InvalidCharException(c);
                
            }
        }
    }
    
}
