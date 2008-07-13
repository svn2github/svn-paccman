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

package org.paccman.ui.form;

import javax.swing.JComponent;

/**
 *
 * @author joao
 */
public class BadInputException extends Exception {
    
    /** Creates a new instance of BadInputException
     * @param message
     * @param component 
     */
    public BadInputException(String message, javax.swing.JComponent component) {
        super(message);
        this.component = component;
    }

    /**
     * Holds value of property component.
     */
    private JComponent component;

    /**
     * Getter for property component.
     * @return Value of property component.
     */
    public JComponent getComponent() {
        return this.component;
    }

    /**
     * Setter for property component.
     * @param component New value of property component.
     */
    public void setComponent(JComponent component) {
        this.component = component;
    }
    
}
