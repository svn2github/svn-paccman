/*
 
    Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
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

package org.paccman.derbyant.btools;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for derby embedded database.
 * @author joao
 */
public class DerbyUtils {
    
    /**
     * 
     */
    public static final String DERBY_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    
    /**
     * Shutdown the specified database. It is used basically to handle the derby
     * shutdown exception ("shutdown always throws an exception" - sic)
     * @param database The URL of the database to be shutdown.
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException 
     */
    public static void shutdownDb(String database) throws SQLException, ClassNotFoundException {
        try {
            Class.forName(DERBY_DRIVER_NAME);
            DriverManager.getConnection(database + ";shutdown=true");
        } catch (SQLException ex) {
            if (!"08006".equals(ex.getSQLState())) {
                // If not (expected) "shutdown" exception, re-throws exception.
                throw ex;
            }
        }
    }

    private DerbyUtils() {
    }
}
