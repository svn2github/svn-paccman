/*
 * ExprParserTest.java
 * JUnit 4.x based test
 *
 * Created on 10 avril 2007, 08:23
 */

package org.paccman.calc.parser;

import org.paccman.calc.lexparser.CalcToken;
import org.paccman.calc.yaccparser.ExpressionParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.paccman.calc.lexparser.LexParser;
import static org.junit.Assert.*;

/**
 *
 * @author joao
 */
public class ExprParserTest {
    
    public ExprParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private void testParseExpression(String expr) throws org.paccman.calc.lexparser.LexParser.LexParseException {
        //:TODO: I WAS HERE
        LexParser lp = new LexParser();
        CalcToken token = null;
        ExpressionParser ep = new ExpressionParser();
        for (int i = 0 ; i < expr.length() ; i++) {
            char c = expr.charAt(i);
            token = lp.parseChar(c);
            if (token != null) {
                System.out.println("Read token: " + token.toString());
                ep.parseToken(token);
            }
        } 
    }
    
    @Test
    public void parseToken() {
        try     {
            testParseExpression("12.345678+90123456.8763=");
        }
        catch (org.paccman.calc.lexparser.LexParser.LexParseException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
}
    
}
