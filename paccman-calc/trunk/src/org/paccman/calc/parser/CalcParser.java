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
     * @throws org.paccman.calc.parser.ParseException
     */
    public CalcParser() throws IOException, org.paccman.calc.parser.ParseException {
        reader = new PipedReader();
        writer = new PipedWriter(reader);
        lexParser = new LexParser(writer);
        calc = new Calculator(reader);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    calc.parse();
                } catch (org.paccman.calc.parser.ParseException ex) {
                    Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private String display;
    
    /**
     *
     * @param c
     * @return
     */
    public String parseChar(char c) {
        try {
            lexParser.parseChar(c);
            return display;
        } catch (ParseException ex) {
            Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}