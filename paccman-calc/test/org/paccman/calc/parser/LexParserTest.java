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
            assertEquals( "12 + 34 * ( 13 - 52.25 ) - 0.3 / 15",
                    testParseExpression("12+34*(13-52.25)-.3/15="));
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
            // 12 + 34.45
            assertEquals(testParseExpression("12+34..45="), "12 + 34.45");
            // 12 + 34.98
            assertEquals(testParseExpression("12+34....98="), "12 + 34.98");
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
            // 12 + 34.4567812
            assertEquals(testParseExpression("12+34.45678.12="), "12 + 34.4567812");
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
            // 12 + 3456
            assertEquals(testParseExpression("12+34a56="), "12 + 3456");
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
            assertEquals("-2 + 6 * ( 3 * 52 )", testParseExpression("2±+6*(3*5±±=2)="));
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
            assertEquals("2 + 3 * ( 3 - 4 )",
                    testParseExpression("2+3*(3-4))="));
            assertEquals("2 + 3 * ( ( ( 3 - 4 ) + 2.3 * 12 ) )",
                    testParseExpression("2+3*(((3-4)+2=.3*12)=)="));
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }

    /**
     * 
     */
    @Test 
    public void testInvalidParen() {
        try {
            // 2 + 33 - 4
            assertEquals(testParseExpression("2+3(3-4=") ,
                    "2 + 33 - 4");  // missing operator before '('
            System.out.println();
            // ( 1.6 + 2 )
            assertEquals(testParseExpression("(1.6+2)="),
                    "( 1.6 + 2 )");  // OK
        } catch (Exception ex) {
            fail("Exception caught: " + ex.getMessage());
        }
        System.out.println();
    }
    
    private String testParseExpression(String expression)
            throws IOException, LexParser.LexParseException {
        StringBuilder sb = new StringBuilder();
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
                        if (sb.length() > 0) {
                            sb.append(' ');
                        }
                        sb.append(ct.tokenString());
                        java.lang.System.out.printf("ReadToken = %s\n", ct);
                    }
                }
            } catch (LexParser.InvalidCharException ex) {
                System.out.printf("%s\n    => Ignoring.\n", ex.getMessage());
            }
        } while (c != -1);
        return sb.toString();
    }
    
}
