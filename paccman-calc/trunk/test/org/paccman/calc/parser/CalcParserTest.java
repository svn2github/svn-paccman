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
    private String[] exprs = {"12+34+56", "12a34+5678", "1234+56g78", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3"};

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
//        new TestData("12+34=", new String[]{"1", "12", "12", "3", "34", "46"}),
//        new TestData("12+34+56=", new String[]{"1", "12", "12", "3", "34", "46", "5", "56", "102"}),
//        new TestData("1+2*3=", new String[]{"1", "1", "2", "2", "3", "7"}),
//        new TestData("2*3+4=", new String[]{"2", "2", "3", "6", "4", "10"}),
//        new TestData("12+34+*2=", new String[]{"1", "12", "12", "3", "34", "46", "46", "2", "92"}),
        new TestData("1+2*(3+4)=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "7", "15"}),
        new TestData("1+2*(3*4)=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "12", "25"}),
//        new TestData("1+2*(3+4)+2=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "4", "15", "15", "17"}),
//        new TestData("1+2*(3+4)*2=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "4", "15", "15", "17"}),
//        new TestData("1+2*(3+4)*3+5=", new String[]{"1", "1", "2", "2", "2", "3", "3", "4", "4", "15", "15", "17", "17", "1", "1"}),
        null
    };

    private void parseExpression(TestData testData) throws IOException, ParseException {
        CalcParser parser = new CalcParser();
        for (int i = 0; i < testData.inputs.length(); i++) {
            int c = testData.inputs.charAt(i);
            String actual = parser.parseChar((char)c);
            assertEquals(testData.expectedResults[i], actual);
        }
    }

    @Test
    public void parse() throws Exception {
        System.out.println("parse");
        int i = 0;
        while (testData[i] != null) {
            parseExpression(testData[i]);
            i++;
        }
    } /* Test of parse method, of class Calculator. */
}