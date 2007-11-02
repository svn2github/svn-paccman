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

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

/**
 *
 * @author joao
 */
public class Logger {

    /**
     * 
     * @param name
     * @return
     */
    public static java.util.logging.Logger getDefaultLogger(String name) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(name);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
        return logger;
    }

    /**
     * Create or get the logger with the name of the class of the given object.
     * @param o The object.
     * @return The logger.
     */
    public static java.util.logging.Logger getDefaultLogger(Object o) {
        return getDefaultLogger(o.getClass());
    }
    
    /**
     * Create or get the logger with the name of the given class.
     * @param c The class.
     * @return The logger.
     */
    public static java.util.logging.Logger getDefaultLogger(Class c) {
        return getDefaultLogger(c.getName());
    }
    
    private Logger() {
    }
}
