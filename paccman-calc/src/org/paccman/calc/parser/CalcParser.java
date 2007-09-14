/*
 * CalcParser.java
 *
 * Created on 13 sept. 2007, 09:08:40
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.paccman.calc.parser.LexParser.ParseException;

/**
 *
 * @author joao
 */
public class CalcParser {

    PipedReader reader;
    PipedWriter writer;
    LexParser lexParser;
    Calculator calc;

    /**
     * 
     * @throws java.io.IOException 
     */
    public CalcParser() throws IOException {
        reader = new PipedReader();
        writer = new PipedWriter(reader);
        lexParser = new LexParser(writer);
        calc = new Calculator(reader);
    }
    
    /**
     * 
     * @param c 
     * @return 
     */
    public boolean parseChar(char c) {
        try {
            lexParser.parseChar(c);
            return true;
        } catch (ParseException ex) {
            Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}