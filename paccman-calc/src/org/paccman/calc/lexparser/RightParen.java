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
public class RightParen extends CalcToken {
    
    /** Creates a new instance of EndToken */
    private RightParen() {
    }
    
    private static RightParen theRightParenToken = new RightParen();
    
    /**
     * 
     * @return 
     */
    public static RightParen getRightParen() {
        return theRightParenToken;
    }
    
    @Override
    public String toString() {
        return "RIGHT_PAREN [)]";
    }

    public String tokenString() {
        return ")";
    }
    
}
