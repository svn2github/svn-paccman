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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class BackupTask extends Task {

    private String connectionString;

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    private String todir;

    public void setTodir(String destination) {
        this.todir = destination;
    }

    @Override
    public void execute() throws BuildException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection connection = DriverManager.getConnection(connectionString);
            String sqlstmt = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
            CallableStatement cs = connection.prepareCall(sqlstmt);
            cs.setString(1, todir);
            cs.execute();
            cs.close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            throw new BuildException(e);
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }
}
