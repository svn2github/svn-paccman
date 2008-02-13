/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.derbyant.btools;

import org.paccman.btools.derbyant.DerbyUtils;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class DbUtilsTest {

    public DbUtilsTest() {
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

    /**
     * Test of shutdownDb method, of class DerbyUtils.
     * @throws java.lang.Exception 
     */
    @Test
    public void shutdownDb() throws Exception {
        System.out.println("shutdownDb");
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        String connectionString = "jdbc:derby:/home/joao/data/devel/projects/paccman/src/paccman-db/build/template.paccmandb";
        Connection connection = DriverManager.getConnection(connectionString);
        connection.close();
        DerbyUtils.shutdownDb(connectionString);
    }
}
