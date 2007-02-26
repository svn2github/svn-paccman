/*
 * OperandToken.java
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
public class OperandToken extends CalcToken {
    
    /** Creates a new instance of OperandToken */
    public OperandToken() {
    }
    
    /**
     * :TODO:
     * @param c 
     */
    public void append(char c) {
        token.append(c);
    }
    
}
