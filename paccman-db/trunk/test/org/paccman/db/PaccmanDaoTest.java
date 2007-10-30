/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.sql.Connection;
import java.sql.SQLException;
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
public class PaccmanDaoTest {

    public PaccmanDaoTest() {
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
     * Test of create method, of class PaccmanDao.
     */
    @Test
    public void create() {
        System.out.println("create");
        PaccmanDao instance = new PaccmanDao("/tmp/testdb.paccman");
        try {
            instance.create();
        } catch (SQLException ex) {
            fail(ex.getStackTrace().toString());
        }
    }

    /**
     * Test of create method, of class PaccmanDao.
     */
    @Test
    public void open() {
        System.out.println("open");
        PaccmanDao instance = new PaccmanDao("/tmp/testdb.paccman");
        try {
            instance.open();
        } catch (SQLException ex) {
            fail(ex.getMessage());
        }
    }

}