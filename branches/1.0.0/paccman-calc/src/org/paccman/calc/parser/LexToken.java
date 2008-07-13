/*
 
    Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
                     http://paccman.sourceforge.net 

    This program is free software; you can redistribute it and/or modify      
    it under the terms of the GNU General Public License as published by      
    the Free Software Foundation; either version 2 of the License, or         
    (at your option) any later version.                                       

    This program is distributed in the hope that it will be useful,           
    but WITHOUT ANY WARRANTY; without even the implied warranty of            
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
    GNU General Public License for more details.                              

    You should have received a copy of the GNU General Public License         
    along with this program; if not, write to the Free Software               
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 
*/

package org.paccman.calc.parser;

/**
 *
 * @author joao
 */
public final class LexToken {

    /**
     * 
     */
    public static final char SIGN_CHAR = 's';
    /**
     * 
     */
    public static final char EVAL_CHAR = '=';
    /**
     * 
     */
    public static final char DEC_POINT = '.';
    /**
     * 
     */
    public static final char OPEN_PAR = '(';
    /**
     * 
     */
    public static final char CLOSE_PAR = ')';
    /**
     * 
     */
    public static final char MULT_CHAR = '*';
    /**
     * 
     */
    public static final char PLUS_CHAR = '+';
    /**
     * 
     */
    public static final char MINUS_CHAR = '-';
    /**
     * 
     */
    public static final char DIV_CHAR = '/';
    /**
     * Clear entry
     */
    public static final char CE_CHAR = 'c';
    /**
     * Reset 
     */
    public static final char RST_CHAR = 'z';
    /**
     * Per cent 
     */
    public static final char PC_CHAR = '%';
    

    /**
     * 
     * @param c 
     * @return 
     */
    public static  boolean isOperator(char c) {
        return (c == MULT_CHAR) || (c == DIV_CHAR) || (c == MINUS_CHAR) || 
                (c == PLUS_CHAR);
    }
    
    private LexToken() {
    }
}
