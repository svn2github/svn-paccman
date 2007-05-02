/*
 * ExprParser.java
 *
 * Created on 17 mars 2007, 07:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc.yaccparser;

import java.util.Stack;
import org.paccman.calc.lexparser.CalcToken;

/**
 *
 * @author joao
 */
public class ExpressionParser {
    
    Stack<CalcToken> tokenStack = new Stack<CalcToken>();
    
    protected void pushToken(CalcToken token) {
        tokenStack.push(token);
    }
    
    protected CalcToken popToken() {
        return tokenStack.pop();
    }
    
    /** Creates a new instance of ExprParser */
    public ExpressionParser() {
    }
    
    /**
     * 
     * @param token 
     */
    public void parseToken(CalcToken token) {
        pushToken(token);
    }
    
}
