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

import java.math.MathContext;
import org.paccman.calc.parser.LexParser.ParseException;

/**
 *
 * @author joao
 */
public class CalcParser {

    LexParser lexParser;
    YaccParser yaccParser;

    /**
     * 
     * @param mathContext
     * @throws org.paccman.calc.parser.LexParser.ParseException
     */
    public CalcParser(MathContext mathContext) throws ParseException {
        yaccParser = new YaccParser(mathContext);
        lexParser = new LexParser(yaccParser);
        reset();
    }

    /**
     * Returns the current displayed value.
     * @return
     */
    public String getDisplay() {
        switch (lexParser.state) {
            case ParseOperand:
                return lexParser.getOperandDisplay();
            case Idle:
            case ReadOp:
            case WaitNext:
            case WaitOp:
                return yaccParser.getTopOperand().toString();
        }
        throw new IllegalStateException("Invalid state: " + lexParser.state.toString());
    }

    /**
     * 
     * @param c
     * @return
     * @throws org.paccman.calc.parser.LexParser.ParseException
     */
    public String parseChar(char c) throws ParseException {
        lexParser.parseChar(c);
        return getDisplay();
    }

    /**
     *
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * 
     */
    public void reset() throws ParseException {
        lexParser.reset();
        yaccParser.reset();
    }
}