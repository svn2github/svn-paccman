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

package org.paccman.tools;

import javax.swing.JOptionPane;

/**
 * Fatal error manager. 
 * 
 * Currently it only displays an error message box and exits the JVM.
 * 
 * @author joao
 */
public class ErrorManager {

    /**
     * Show an error message box and exits the JVM.
     * @param e The exception causing the fatal error.
     * @param context Context to figure out where exception was thrown.
     */
    public static void fatal(Exception e, String context) {
        final String msgFormat = "Exception caught in %2$s: '%1$s'";
        
        JOptionPane.showMessageDialog(null, String.format(msgFormat, e.getMessage(), context), 
                "Fatal error ", JOptionPane.ERROR_MESSAGE, null); 
        System.exit(-1);
    }

    private ErrorManager() {
    }
}
