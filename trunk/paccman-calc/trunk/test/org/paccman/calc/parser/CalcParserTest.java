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
            assert inputs.length() == expectedResults.length : 
                "Number of expected result does not match number of input characters";
            this.inputs = inputs;
            this.expectedResults = expectedResults;
        }
    }
    
    private TestData[] testData = {
        new TestData("12+34=", new String[]{"1", "12", "12", "3", "34", "46"}),
        new TestData("12+34+56=", new String[]{"1", "12", "12", "3", "34", "46", "5", "56", "102"}),
        new TestData("1+2*3=", new String[]{"1", "1", "2", "2", "3", "7"}),
        new TestData("2*3+4=", new String[]{"2", "2", "3", "6", "4", "10"}),
        new TestData("12+34+*2=", new String[]{"1", "12", "12", "3", "34", "46", "46", "2", "92"}),
        new TestData("1+2*(3+4)=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "7", "15"}),
        new TestData("1+2*(3*4)=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "12", "25"}),
        new TestData("1+2*(3+4)+2=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "7", "15", "2", "17"}),
        new TestData("1+2*(3+4)*2=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "7", "14", "2", "29"}),
        new TestData("1+2*(3+4)*3+5=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "7", "14", "3", "43", "5", "48"}),
        new TestData("1+2=+5=", new String[]{"1", "1", "2", "3", "3", "5", "8"}),
        new TestData("1.2+3.4=", new String[] {"1", "1.", "1.2", "1.2", "3", "3.", "3.4", "4.6"}),
        new TestData("1.2+.4=", new String[] {"1", "1.", "1.2", "1.2", "0.", "0.4", "1.6"}),
        new TestData(".2*.4=", new String[] {"0.", "0.2", "0.2", "0.", "0.4", "0.08"}),
        new TestData("1.2*0.4*+234.56789=/2=", new String[] {"1", "1.", "1.2", "1.2", "0", "0.", "0.4",
                "0.48", "0.48", "2", "23", "234", "234.", "234.5", "234.56", "234.567", 
                "234.5678", "234.56789", "235.04789", "235.04789", "2", "117.523945"}), 
        new TestData("1+-+2=", new String[] {"1", "1", "1", "1", "2", "3"}),
        new TestData("1+2s=", new String[] {"1", "1", "2", "-2", "-1"}),
        new TestData("1+2ss=", new String[] {"1", "1", "2", "-2", "2", "3"}),
        new TestData("1+0s.s2=", new String[] {"1", "1", "0", "-0", "-0.", "0.", "0.2", "1.2"}),
        new TestData("1+2=s*5=", new String[] {"1", "1", "2", "3", "-3", "-3", "5", "-15"}),
        new TestData("1s+0.2s5=+3s=", new String[] {"1", "-1", "-1", "0", "0.", "0.2", "-0.2", "-0.25", "-1.25", "-1.25","3", "-3", "-4.25"}),
        new TestData("1+(2+(3+4))+6/2=", new String[] {
                "1", "1", "1", "2", "2", "2", "3", "3", "4", "7", "9", "10", "6", "6", "2", "13"}),
        new TestData("1+(2s+(3+4))+6/2=", new String[] {
                "1", "1", "1", "2", "-2", "-2", "-2", "3", "3", "4", "7", "5", "6", "6", "6", "2", "9"}),
        new TestData("12+3c4=", new String[]{"1", "12", "12", "3", "0", "4", "16"}),
        new TestData("12+3.4c5=", new String[]{"1", "12", "12", "3", "3.", "3.4", "0", "5", "17"}),
        new TestData("1.2.3+4=", new String[]{"1", "1.", "1.2", null, "1.23", "1.23", "4", "5.23"}),
        null
    };

    private void parseExpression(TestData testData) throws IOException, ParseException {
        CalcParser parser = new CalcParser(new MathContext(10, RoundingMode.HALF_DOWN));
        for (int i = 0; i < testData.inputs.length(); i++) {
            int c = testData.inputs.charAt(i);
            try {
                String actual = parser.parseChar((char)c);
                System.out.printf("    Read: '%1$c'. Display: %2$s%n", (char)c, actual);
                assertEquals(testData.expectedResults[i], actual);
            } catch (ParseException ex) {
                assertNull(testData.expectedResults[i]);
            }
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