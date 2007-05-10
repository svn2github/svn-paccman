/*
 * EndToken.java
 *
 * Created on 17 mars 2007, 07:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.lexparser;

/**
 *
 * @author joao
 */
public class LeftParen extends CalcToken {
    
    /** Creates a new instance of EndToken */
    private LeftParen() {
    }
    
    private static LeftParen theLeftParenToken = new LeftParen();
    
    public static LeftParen getLeftParen() {
        return theLeftParenToken;
    }
    
    @Override
    public String toString() {
        return "LEFT_PAREN [(]";
    }

    public String tokenString() {
        return "(";
    }
    
}
