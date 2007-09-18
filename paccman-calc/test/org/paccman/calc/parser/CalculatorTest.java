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
import java.io.InputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CalculatorTest {

    public CalculatorTest() {
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
    private String[] exprs = {
        // "12+34", 
        "12+34+56", 
        // "a12+34*56", 
        "12a34+5678", "1234+56g78", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3", "12+(52+6)*3"};

    private void parseExpression(String s) throws ParseException, IOException, InterruptedException {
        System.out.println("\n--------------------------------------");
        System.out.println("------------- Parsing: " + s);
        final PipedReader pr = new PipedReader();
        PipedWriter pw = new PipedWriter(pr);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    new org.paccman.calc.parser.Calculator(pr).parse();
                    pr.close();
                } catch (ParseException ex) {
                    Logger.getLogger(CalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);
            pw.write(c);
            System.out.printf("->->->->->->->->->->->->->%1$c\n", c);
            Thread.sleep(5000);
            pw.flush();
        }
        pw.close();
        t.join();

//        StringReader sr = new StringReader(s);
//        Calculator instance = new org.paccman.calc.parser.Calculator(sr);
//        instance.parse();
        System.out.println("Done --------------------------------------");
    }

    @Test
    public void parse() throws Exception {
        System.out.println("parse");
        for (String s : exprs) {
            parseExpression(s);
        }
        fail("The test case is a prototype.");
    } /* Test of parse method, of class Calculator. */
}