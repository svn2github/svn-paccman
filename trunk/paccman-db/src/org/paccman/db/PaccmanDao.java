/*
 
    Copyright (C)    2005 Joao F. (joaof@sourceforge.net)
                     http://paccman.sourceforge.net 

    This program is free software; you can redistribute it and/or modify      
    it under the terms of the GNU General Public License as published by      
    the Free Software Foundation; either version 2 of the License, or         
    (at your option) any later version.                                       

    This program is distributed in the hope that it will be useful,           
    but WITHOUT ANY WARRANTY; without even the implied warranty of            
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
    GNU General Public License for more details.                              

    You should have received a copy of the GNU General Public License         
    along with this program; if not, write to the Free Software               
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 
*/

package org.paccman.db;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
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
     * Open database connection. If connection does not exist, it is created.
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
            if (!"08006".equals(ex.getSQLState())) {
                // If not "shutdown" (expected) exception, re-throws exception.
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

        // Do actually load the document
        PaccmanLoad pl = new PaccmanLoad();
        pl.loadDocument(connection, ctrl);

        // Close database
        shutdown();
    }

    /**
     * Backup the database to the specified directory.
     * @param backupTo Absolute path to the directory where to backup the database.
     * @throws java.sql.SQLException 
     */
    public void backup(File backupTo) throws SQLException {
        CallableStatement cs = connection.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
        cs.setString(1, backupTo.getAbsolutePath());
        cs.execute();
        cs.close();
        logger.info("Backed up database to " + backupTo);
    }

    /**
     * :TODO:
     * @param exportTo
     */
    public void export(File exportTo) {
        //:TODO:raw export all table to a jar file
    }

    java.util.logging.Logger logger = org.paccman.tools.Logger.getDefaultLogger(this.getClass());
}
