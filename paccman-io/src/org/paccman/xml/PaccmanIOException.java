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

package org.paccman.xml;

import java.io.IOException;

/**
 *
 * @author joao
 */
public class PaccmanIOException extends Exception {
    
    /** Creates a new instance of PaccmanIOException */
    public PaccmanIOException(String msg) {
        super(msg);
    }
    
    public PaccmanIOException(IOException ex) {
        super("IOException: " + ex.getMessage());
    }
    
}
