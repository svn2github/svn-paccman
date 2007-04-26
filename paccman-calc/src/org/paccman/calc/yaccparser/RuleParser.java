/*
 * RuleParser.java
 * 
 * Created on 16 avr. 2007, 08:14:43
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.yaccparser;

import org.paccman.calc.lexparser.CalcToken;

/**
 *
 * @author joao
 */
public abstract class RuleParser {

    /**
     * 
     */
    public RuleParser() {
    }

    /**
     * 
     * @param token 
     */
    abstract public void parse(CalcToken token);
        
}
