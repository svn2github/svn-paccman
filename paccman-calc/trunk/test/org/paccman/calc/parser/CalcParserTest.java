/*
 * CalculatorTest.java
 *
 * Created on 15 sept. 2007, 08:46:06
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

import java.io.IOException;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.paccman.calc.parser.LexParser.ParseException;
import static org.junit.Assert.*;

/**
 *
 * @author joao
 */
public class CalcParserTest {

    public CalcParserTest() {
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

    private class TestData {

        CharSequence inputs;
        String[] expectedResults;

        TestData(CharSequence inputs, String[] expectedResults) {
            assert inputs.length() == expectedResults.length : "Number of expected result does not match number of input characters";
            this.inputs = inputs;
            this.expectedResults = expectedResults;
        }
    }
    private TestData[] testData = {new TestData("1+(2S+(3+4))+6/2=", new String[]{"1", "1", "1", "2", "-2", "-2", "-2", "3", "3", "4", "7", "5", "6", "6", "6", "2", "9"}), null};

    private void parseExpression(TestData testData) throws IOException, ParseException {
        CalcParser parser = new CalcParser(new MathContext(5, RoundingMode.HALF_DOWN));
        for (int i = 0; i < testData.inputs.length(); i++) {
            int c = testData.inputs.charAt(i);
            String actual = parser.parseChar((char) c);
            System.out.printf("    Read: '%1$c'. Display: %2$s%n", (char) c, actual);
            assertEquals(testData.expectedResults[i], actual);
        }
        System.out.println();
    }

    @Test
    public void parse() throws Exception {
        int i = 0;
        while (testData[i] != null) {
            System.out.printf("Parsing[%1$d]: '%2$s'%n", i, testData[i].inputs);
            parseExpression(testData[i]);
            i++;
        }
    } /* Test of parse method, of class Calculator. */
}