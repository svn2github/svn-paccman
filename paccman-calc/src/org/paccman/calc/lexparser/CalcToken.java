/*
 * Token.java
 *
 * Created on 26 février 2007, 07:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.lexparser;

/**
 *
 * @author joao
 */
public abstract class CalcToken {
    
    /**
     * :TODO:
     */
    protected StringBuilder token = new StringBuilder();
    
    /** Creates a new instance of Token */
    public CalcToken() {
    }
    
    /**
     * 
     * @return 
     */
    abstract public String tokenString();
    
}
