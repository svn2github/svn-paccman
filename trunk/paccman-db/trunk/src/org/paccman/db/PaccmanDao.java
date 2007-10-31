/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
    public void create() throws SQLException {
        String connectionString = new String("jdbc:derby:");
        connectionString = connectionString + database;
        connectionString = connectionString + ";create=true";
        connection = DriverManager.getConnection(connectionString);
    }
    
    public void open() throws SQLException {
        String connectionString = new String("jdbc:derby:");
        connectionString = connectionString + database;
        connection = DriverManager.getConnection(connectionString);
    }
    
}
