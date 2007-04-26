/*
 * LexParserTest.java
 * JUnit 4.x based test
 *
 * Created on 28 mars 2007, 21:45
 */

package org.paccman.calc.parser;

import org.paccman.calc.lexparser.LexParser;
import java.io.IOException;
import java.io.StringReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joao
 */
public class LexParserTest {
    
    /**
     * 
     */
    public LexParserTest() {
    }
    
    /**
     * 
     * @throws java.lang.Exception 
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }
    
    /**
     * 
     * @throws java.lang.Exception 
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    /**
     * 
     * @throws java.lang.Exception 
     */
    @Before
    public void setUp() throws Exception {
    }
    
    /**
     * 
     * @throws java.lang.Exception 
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * 
     */
    @Test
    public void validExpr() {
        try {
            testParseExpression("12+34*(13-52.25)-.3/15=");
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }
    
    /**
     * 
     */
    @Test
    public void invalidDotDot() {
        try {
            testParseExpression("12+34..45=");
            testParseExpression("12+34....98=");
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }
    
    /**
     * 
     */
    @Test
    public void invalidDotOperandDot() {
        try {
            testParseExpression("12+34.45678.12=");
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }
    
    /**
     * 
     */
    @Test
    public void invalidCharA() {
        try {
            testParseExpression("12+34a56=");
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }

    /**
     * 
     */
    @Test
    public void negOperand() {
        try {
            testParseExpression("2±+6*(3*5±±=");
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }

    /**
     * 
     */
    @Test
    public void testParenLevel() {
        try {
            testParseExpression("2+3*(3-4))=");     // extra '('
            testParseExpression("2+3*(((3-4)+2)="); // missing ')'
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }

    private void testParseExpression(String expression)
            throws IOException, LexParser.LexParseException {
        System.out.println("Parsing: '" + expression + "'");
        StringReader sr = new StringReader(expression);
        LexParser instance = new LexParser();
        int c;
        do {
            org.paccman.calc.lexparser.CalcToken ct;
            
            c = sr.read();
            try {
                if (c != -1) {
                    if ((ct = instance.parseChar((char)c)) != null) {
                        java.lang.System.out.printf("ReadToken = %s\n", ct);
                    }
                }
            } catch (LexParser.InvalidCharException ex) {
                System.out.printf("%s\n    => Ignoring.\n", ex.getMessage());
            }
        } while (c != -1);
    }
    
}
