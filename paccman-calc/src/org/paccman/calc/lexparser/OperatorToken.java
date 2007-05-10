/*
 * OPeratorToken.java
 *
 * Created on 26 février 2007, 07:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.lexparser;

/**
 *
 * @author joao
 */
public class OperatorToken extends CalcToken {
    
    /** Creates a new instance of OperatorToken 
     * 
     * @param c 
     */
    public OperatorToken(char c) {
        token.append(c);
    }
    
    @Override
    public String toString() {
        return "OPERATOR [" + token.toString() + "]";
    }

    public String tokenString() {
        return token.toString();
    }
    
}
