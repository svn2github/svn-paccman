/*
 * OPeratorToken.java
 *
 * Created on 26 février 2007, 07:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

/**
 *
 * @author joao
 */
public class OperatorToken extends CalcToken {
    
    /** Creates a new instance of OPeratorToken 
     * 
     * @param c 
     */
    public OperatorToken(char c) {
        token.append(c);
    }
    
}
