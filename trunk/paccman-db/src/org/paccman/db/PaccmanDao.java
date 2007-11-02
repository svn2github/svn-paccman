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
    
    /**
     * 
     * @throws java.sql.SQLException
     * @throws java.io.UnsupportedEncodingException 
     */
    public void create() throws SQLException, UnsupportedEncodingException {
        String connectionString = new String("jdbc:derby:");
        connectionString = connectionString + database;
        connectionString = connectionString + ";create=true";
        connection = DriverManager.getConnection(connectionString);
        
        String path = "/scripts/create.sql";
        InputStream is = getClass().getResourceAsStream(path);
        int res = org.apache.derby.tools.ij.runScript(connection, is, "UTF-8", System.out, "UTF-8");
        if ((res != -1) && (res != 0)) {
            throw new Error(":TODO:handle dthis better please");
        }
    }
    
    /**
     * 
     * @throws java.sql.SQLException
     */
    public void open() throws SQLException {
        String connectionString = new String("jdbc:derby:");
        connectionString = connectionString + database;
        connection = DriverManager.getConnection(connectionString);
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
    }
    
}
