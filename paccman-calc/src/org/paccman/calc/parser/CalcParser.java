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

/**
 *
 * @author joao
 */
public class CalcParser {

    PipedReader reader;
    PipedWriter writer;
    LexParser lexParser;
Calculator calc;

    public CalcParser() throws IOException {
        reader = new PipedReader();
        writer = new PipedWriter(reader);
        lexParser = new LexParser(writer);
        calc = new Calculator(reader);
    }
}