/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.paccman.controller.DocumentController;

/**
 *
 * @author joao
 */
public class PaccmanDao {
    private String database;
    private Connection connection;

    /**
     * 
     * @param database
     */
    public PaccmanDao(String database) {
        this.database = database;
    }
    
    private String getConnectionString(String option) {
        return "jdbc:derby:" + database + ";" + option;
    }
    
    /**
     * Open databse connection. If connection does not exist, it is created.
     * @throws java.sql.SQLException
     * @throws java.io.UnsupportedEncodingException 
     */
    private void create() throws SQLException, UnsupportedEncodingException {
        String connectionString = getConnectionString("create=true");
        connection = DriverManager.getConnection(connectionString);
        
        String path = "/scripts/create.sql";
        InputStream is = getClass().getResourceAsStream(path);
        int res = org.apache.derby.tools.ij.runScript(connection, is, "UTF-8", System.out, "UTF-8");
        if ((res != -1) && (res != 0)) {
            throw new Error(":TODO:handle this better please");
        }
    }
    
    /**
     * Open database connection.
     * @throws java.sql.SQLException
     */
    private void open() throws SQLException {
        String connectionString = getConnectionString("");
        connection = DriverManager.getConnection(connectionString);
    }
    
    /**
     * Close database connection.
     * @throws java.sql.SQLException
     */
    private void shutdown() throws SQLException {
        String connectionString = getConnectionString("shutdown=true");
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            if (! "08006".equals(ex.getSQLState())) {
                // If not "shutdown" (expected) exception, rethrows exception.
                throw ex;
            } 
        }
    }
    
    /**
     * Save the document associated to the specified controller to the database file.
     * @param ctrl The document controller providing the document to save.
     * @throws java.sql.SQLException
     * @throws java.io.UnsupportedEncodingException 
     */
    public void save(DocumentController ctrl) throws SQLException, UnsupportedEncodingException {
        // First create the database
        create();
        
        // Do actually save the document
        PaccmanSave pl = new PaccmanSave();
        pl.saveDocument(connection, ctrl);
        
        // Close database
        shutdown();
    }
    
    /**:TODO:
     * Save the document associated to the specified controller to the database file.
     * @param ctrl The document controller providing the document to save.
     * @throws java.sql.SQLException
     * @throws java.io.UnsupportedEncodingException 
     */
    public void load(DocumentController ctrl) throws SQLException, UnsupportedEncodingException {
        // First create the database
        open();
        
        // Do actually save the document
        PaccmanLoad pl = new PaccmanLoad();
        pl.loadDocument(connection, ctrl);
        
        // Close database
        shutdown();
    }
    
}
