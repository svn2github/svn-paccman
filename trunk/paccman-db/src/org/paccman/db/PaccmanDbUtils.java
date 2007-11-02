/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author joao
 */
public class PaccmanDbUtils {
    
    /**
     * Current version of database model.
     */
    static final String CURRENT_VERSION = "1.0.0.0";
    
    /**
     * Name of key for the title of the document in DOCINFO table.
     */
    static final String DOCUMENT_TITLE_KEY = "title";

    /**
     * Name of key for the creation date of the document in DOCINFO table.
     */
    static final String CREATEDOC_DATE_KEY = "creationdate";
    
    /**
     * Name of key for the last update date of the document in DOCINFO table.
     */
    static final String UPDATEDOC_DATE_KEY = "lastupdate";
    
    /**
     * Version of the databsae model.
     */
    static final String DOCVERSION_KEY = "dbversion";
    
    /**
     * Convert the given calendar to a SQL Date.
     * @param c The calendar to convert.
     * @return The SQL date equivalent of the <code>c</code> Calendar.
     */
    static final Date calendarToSqlDate(Calendar c) {
        return new Date(c.getTimeInMillis());
    }

    private PaccmanDbUtils() {
    }
}
