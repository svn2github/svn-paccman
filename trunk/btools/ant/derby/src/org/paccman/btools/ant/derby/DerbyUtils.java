/*
 *
 *  Copyright (C)    2008 Joao F. (joaof at sourceforge.net)
 *                   http://paccman.sourceforge.net
 *
 *  This file is part of PAccMan.
 *
 *  PAccMan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PAccMan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PAccMan.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.paccman.btools.ant.derby;

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
