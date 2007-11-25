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

package org.paccman.ui.main;

/**
 * This class is used to handle the command line arguments passed to PAccMan.
 * @author joao
 */
class StartOption {

    String filename;

    public String getFilename() {
        return filename;
    }

    /**
     * Parse the arguments and initiliaze the <code>StartOption</code> fields.
     * @param arguments The arguments passed in the command line.
     */
    public void parse(String[] arguments) {
        if (arguments.length == 1) {
            filename = arguments[0];
        }
    }
}
