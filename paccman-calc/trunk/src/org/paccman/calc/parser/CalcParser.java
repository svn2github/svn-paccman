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
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @throws java.io.IOException
     * @throws org.paccman.calc.parser.LexParser.ParseException
     *
     */
    public CalcParser() throws IOException, ParseException {
        yaccParser = new YaccParser();
        lexParser = new LexParser(yaccParser);
        reset();
    }

    private String getDisplay() {
        switch (lexParser.state) {
            case ParseInt:
                return lexParser.getLastOperand().toString();
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
     */
    public String parseChar(char c) {
        try {
            lexParser.parseChar(c);
            return getDisplay();
        } catch (ParseException ex) {
            Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(CalcParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * @throws java.io.IOException
     */
    public void reset() throws ParseException, IOException {
        lexParser.reset();
        yaccParser.reset();
    }
}