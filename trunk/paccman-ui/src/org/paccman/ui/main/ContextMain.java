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

package org.paccman.ui.main;

import org.paccman.controller.DocumentController;

/**
 *
 * @author joao
 */
public class ContextMain {
    
    private static DocumentController documentController;

    /**
     * Return the current document controller.
     * @return The current document controller or null if none has been loaded 
     * or created.     
     */
    public static DocumentController getDocumentController() {
        return documentController;
    }

    /**
     * Set the current document controller.
     * @param documentController The document controller to set.
     */
    public static void setDocumentController(DocumentController documentController) {
        ContextMain.documentController = documentController;
    }
    
    private ContextMain() {
    }
    
}
