/*
 * Logger.java
 * 
 * Created on 14 juin 2007, 08:08:58
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
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
