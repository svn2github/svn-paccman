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

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.XMLFormatter;

/**
 * The logger of PAccMan. The default logger writes to the console and to log rotating
 * files in the log directory under the working directory (i.e. in <b>workdir</b>/log)
 * @author joao
 */
public class Logger {

    /**
     * Name pattern for log files.
     */
    static final String PACCCMAN_FILE_LOG_PATTERN = "paccman%g.log.xml";
    /**
     * Max number of log files.
     */
    static final int PACCMAN_FILE_LOG_CNT = 10;
    /**
     * Max log fileHandler size.
     */
    static final int PACCMAN_FILE_LOG_MAX_SIZE = 1 * 1024 * 1024;
    /**
     * FileHandler for PAccMan. All logs go to this file.
     */
    static private FileHandler fileLogHandler;

    static class PaccmanLogFormatter extends XMLFormatter {

        @Override
        public String getHead(Handler h) {
            String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + new String(new char[] { Character.LINE_SEPARATOR });
            s += "<?xml-stylesheet type=\"text/xsl\" href=\"paccmanlog.xslt\"?>" + new String(new char[] { Character.LINE_SEPARATOR });
            return s + "<log>";
        }
    }
    
    static {
        try {
            String fullLogPattern = System.getProperty("user.dir") + 
                    File.separator + "log" + File.separator + PACCCMAN_FILE_LOG_PATTERN;
            fileLogHandler = new FileHandler(fullLogPattern,
                    PACCMAN_FILE_LOG_MAX_SIZE, PACCMAN_FILE_LOG_CNT);
            fileLogHandler.setFormatter(new PaccmanLogFormatter());
        } catch (Exception e) {
            ErrorManager.fatal(e, "Logger.static");
        }
    }

    /**
     * Create or get the logger with the specified name.
     * @param name Name of the logger.
     * @return The logger with the specified name.
     */
    public static java.util.logging.Logger getDefaultLogger(String name) {

        java.util.logging.Logger logger = java.util.logging.LogManager.getLogManager().getLogger(name);
        if (logger == null) {
            // Create the new logger and set level.
            logger = java.util.logging.Logger.getLogger(name);
            logger.setLevel(Level.ALL);

            // Console handler.
            Handler consoleHandler = new ConsoleHandler();
            logger.addHandler(consoleHandler);

            // File handler.
            logger.addHandler(fileLogHandler);

            // Not using parent handlers
            logger.setUseParentHandlers(false);
        }
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
