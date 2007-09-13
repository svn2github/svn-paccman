/*
 * LexToken.java
 *
 * Created on 12 sept. 2007, 21:40:43
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

/**
 *
 * @author joao
 */
public final class LexToken {

    public static final char SIGN_CHAR = '±';
    public static final char EVAL_CHAR = '=';
    public static final char DEC_POINT = '.';
    public static final char OPEN_PAR = '(';
    public static final char CLOSE_PAR = ')';
    public static final char NEG_SIGN = '-';
    public static final char MIN_DIGIT = '0';
    public static final char MAX_DIGIT = '0';
    public static final char MULT_CHAR = '*';
    public static final char PLUS_CHAR = '+';
    public static final char MINUS_CHAR = '-';
    public static final char DIV_CHAR = '/';

    public static  boolean isOperator(char c) {
        return (c == MULT_CHAR) || (c == DIV_CHAR) || (c == MINUS_CHAR) || 
                (c == PLUS_CHAR);
    }
    
    public static boolean isDigit(char c) {
        return (c >= MIN_DIGIT) && (c <= MAX_DIGIT);
    }
}
