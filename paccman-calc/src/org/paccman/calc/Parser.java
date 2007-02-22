/*
 * Parser.java
 *
 * Created on 10 septembre 2006, 23:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.calc;

/**
 *
 * @author joao
 */
public class Parser {
    
    public enum Token {
        DIGIT      ,
        OPERATOR   ,
        LEFT_PAREN ,
        RIGHT_PAREN,
        POINT      ,
        PER_CENT
    }
    
    /** Creates a new instance of Parser */
    public Parser() {
    }
    
}
