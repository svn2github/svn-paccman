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
    
    /**
     * Check if a document is currently edited (new or loaded)
     * @return <code>true</code> if a document is currentlty edited (new or loaded). <code>false</code> otherwise.
     */
    public static boolean isDocumentEdited() {
        return documentController != null;
    }

    private static DocumentController documentController;

    /**
     * Return the current document controller.
     * @return The current document controller or <code>null</code> if none has been loaded 
     * or created.     
     */
    public static DocumentController getDocumentController() {
        return documentController;
    }

    /**
     * Set the current document controller. If the new controller is not <code>null</code>, 
     * the <code>Main</code> frame is registered to the new controller and its tabs 
     * are displayed. Otherwise the tabs are hidden (removed).
     * @param documentController The document controller to set.
     */
    public static void setDocumentController(DocumentController documentController) {
        if (documentController != null) {
            documentController.unregisterView(Main.getMain());
        }
        ContextMain.documentController = documentController;
        if (documentController != null) {
            documentController.registerView(Main.getMain());
            Main.getMain().showTabbedPanes();
        } else {
            Main.getMain().hideTabbedPanes();
        }
    }
    
    private ContextMain() {
    }
    
}
