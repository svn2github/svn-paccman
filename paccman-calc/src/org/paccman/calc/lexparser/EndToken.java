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
public class EndToken extends CalcToken {
    
    /** Creates a new instance of EndToken */
    private EndToken() {
    }
    
    private static EndToken theEndToken = new EndToken();
    
    /**
     * 
     * @return 
     */
    public static EndToken getEndToken() {
        return theEndToken;
    }
    
    @Override
    public String toString() {
        return "END";
    }

    public String tokenString() {
        return "";
    }
    
}
