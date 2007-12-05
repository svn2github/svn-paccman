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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.SQLExec;

/**
 * Execute script ant task. This is the SQLExec ant task plus the shutdown.
 * @author joao
 */
public class ExecScriptTask extends SQLExec {

    /**
     * Creates the connection used to execute the task.
     * We don't use getConnection from JDBCTask which uses a <code>Driver</code> to 
     * create the connection (the shutdown fails if we dont do that.
     * @return The connection to use for SQL operation.
     * @throws org.apache.tools.ant.BuildException
     */
    @Override
    protected Connection getConnection() throws BuildException {
        try {
            Class.forName(DerbyUtils.DERBY_DRIVER_NAME);
            Connection cnx = DriverManager.getConnection(getUrl());
            return cnx;
        } catch (ClassNotFoundException ex) {
            throw new BuildException(ex);
        } catch (SQLException ex) {
            throw new BuildException(ex);
        }
    }

    /**
     * Execute script ant task body.
     * @throws org.apache.tools.ant.BuildException
     */
    @Override
    public void execute() throws BuildException {
        // Do execute
        super.execute();

        // Shutdown database
        try {
            DerbyUtils.shutdownDb(getUrl());
        } catch (SQLException ex) {
            throw new BuildException(ex);
        } catch (ClassNotFoundException ex) {
            throw new BuildException(ex);
        }
    }
}
