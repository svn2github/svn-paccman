/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.ui.common;

import java.math.BigDecimal;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class AmountFormatTest {

    public AmountFormatTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
   class TestData {
       String id;
       String input;
       String result;
       boolean showSign;
       boolean showSymbol;

        public TestData(String id, String input, String result, boolean showSign, boolean showSymbol) {
            this.id = id;
            this.input = input;
            this.result = result;
            this.showSign = showSign;
            this.showSymbol = showSymbol;
        }
   }

   TestData[] testDataFRANCE = {
       new TestData("1", "1234567.89", "1\u00A0234\u00A0567,89 €", true, true),
       new TestData("2", "-1234567.89", "-1\u00A0234\u00A0567,89 €", true, true),
       new TestData("3", "1234567.89", "1\u00A0234\u00A0567,89 €", false, true),
       new TestData("4", "-1234567.89", "1\u00A0234\u00A0567,89 €", false, true),
       new TestData("5", "1234567.89", "1\u00A0234\u00A0567,89", true, false),
       new TestData("6", "-1234567.89", "-1\u00A0234\u00A0567,89", true, false),
       new TestData("7", "1234567.89", "1\u00A0234\u00A0567,89", false, false),
       new TestData("8", "-1234567.89", "1\u00A0234\u00A0567,89", false, false)
   };
   TestData[] testDataUK = {
       new TestData("1", "1234567.89", "£1,234,567.89", true, true),
       new TestData("2", "-1234567.89", "-£1,234,567.89", true, true),
       new TestData("3", "1234567.89", "£1,234,567.89", false, true),
       new TestData("4", "-1234567.89", "£1,234,567.89", false, true),
       new TestData("5", "1234567.89", "1,234,567.89", true, false),
       new TestData("6", "-1234567.89", "-1,234,567.89", true, false),
       new TestData("7", "1234567.89", "1,234,567.89", false, false),
       new TestData("8", "-1234567.89", "1,234,567.89", false, false)
   };
   TestData[] testDataUS = {
       new TestData("1", "1234567.89", "$1,234,567.89", true, true),
       new TestData("2", "-1234567.89", "($1,234,567.89)", true, true),
       new TestData("3", "1234567.89", "$1,234,567.89", false, true),
       new TestData("4", "-1234567.89", "$1,234,567.89", false, true),
       new TestData("5", "1234567.89", "1,234,567.89", true, false),
       new TestData("6", "-1234567.89", "-1,234,567.89", true, false),
       new TestData("7", "1234567.89", "1,234,567.89", false, false),
       new TestData("8", "-1234567.89", "1,234,567.89", false, false)
   };
   
   TestData[] testDataJAPAN = {
       // No decimal part (1234567.89 would be rounded to 1234568)
       new TestData("1", "1234567", "\uffe51,234,567", true, true),
       new TestData("2", "-1234567", "-\uffe51,234,567", true, true),
       new TestData("3", "1234567", "\uffe51,234,567", false, true),
       new TestData("4", "-1234567", "\uffe51,234,567", false, true),
       new TestData("5", "1234567", "1,234,567", true, false),
       new TestData("6", "-1234567", "-1,234,567", true, false),
       new TestData("7", "1234567", "1,234,567", false, false),
       new TestData("8", "-1234567", "1,234,567", false, false)
   };

   /**
     * Test of format method, of class AmountFormat.
     */
    @Test
    public void testFormat() {
        AmountFormat af;
        String result;
        
        System.out.println("For FRANCE locale");
        Locale.setDefault(Locale.FRANCE);
        for (TestData testData: testDataFRANCE) {
            af = new AmountFormat(testData.showSign, testData.showSymbol);
            result = af.format(new BigDecimal(testData.input));
            assertEquals("[FRANCE] Failed id = " + testData.id, result, testData.result);
        }
        
        System.out.println("For UK locale");
        Locale.setDefault(Locale.UK);
        for (TestData testData: testDataUK) {
            af = new AmountFormat(testData.showSign, testData.showSymbol);
            result = af.format(new BigDecimal(testData.input));
            assertEquals("[UK] Failed id = " + testData.id, result, testData.result);
        }

        System.out.println("For US locale");
        Locale.setDefault(Locale.US);
        for (TestData testData: testDataUS) {
            af = new AmountFormat(testData.showSign, testData.showSymbol);
            result = af.format(new BigDecimal(testData.input));
            assertEquals("[US] Failed id = " + testData.id, result, testData.result);
        }

        System.out.println("For JAPAN locale");
        Locale.setDefault(Locale.JAPAN);
        for (TestData testData: testDataJAPAN) {
            af = new AmountFormat(testData.showSign, testData.showSymbol);
            result = af.format(new BigDecimal(testData.input));
            assertEquals("[JAPAN] Failed id = " + testData.id, result, testData.result);
        }
    }

}
