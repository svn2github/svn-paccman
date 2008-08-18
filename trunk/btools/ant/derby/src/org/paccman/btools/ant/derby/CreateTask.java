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

import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Create ant task.
 * @author joao
 */
public class CreateTask extends Task {

    private String connectionString;

    /**
     * Connection string attribute.
     * @param connectionString
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Create ant task body.
     * @throws org.apache.tools.ant.BuildException
     */
    @Override
    public void execute() throws BuildException {
        try {
            Class.forName(DerbyUtils.DERBY_DRIVER_NAME);
            Connection connection = DriverManager.getConnection(connectionString + ";create=true");
            connection.close();
            DerbyUtils.shutdownDb(connectionString);
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }
}
