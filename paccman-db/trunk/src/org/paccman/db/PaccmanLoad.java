/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.paccman.controller.DocumentController;
import org.paccman.paccman.Document;

/**
 *
 * @author joao
 */
public class PaccmanLoad {
    private Connection connection;
    private Document doc;

    private Logger log = org.paccman.tools.Logger.getDefaultLogger(PaccmanLoad.class);

    /**
     * 
     * @param connection 
     * @param ctrl
     * @throws java.sql.SQLException 
     */
    public void loadDocument(Connection connection, DocumentController ctrl) throws SQLException {
        log.finest("Start loading");

        
        this.doc = ctrl.getDocument();
        this.connection = connection;

        // Doc info
        loadDocInfo();

    }
    
    private void loadDocInfo() throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("select NAME, VALUE from DOCINFO");
        
        while (rs.next()) {
            final String name = rs.getString("NAME");
            final String value = rs.getString("VALUE");
            if (name.equals("title")) {
                doc.setTitle(value);
            } else if (name.equals("version")) {
                doc.setVersion(value);
            } else if (name.equals("creation_utc")) {
                try {
                    Calendar c = new GregorianCalendar();
                    c.setTime(new SimpleDateFormat().parse(value));
                    doc.setCreationDate(c);
                } catch (ParseException ex) {
                    //:TODO:
                    Logger.getLogger(PaccmanLoad.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (name.equals("update_utc")) {
                try {
                    Calendar c = new GregorianCalendar();
                    c.setTime(new SimpleDateFormat().parse(value));
                    doc.setLastUpdateDate(c);
                } catch (ParseException ex) {
                    //:TODO:
                    Logger.getLogger(PaccmanLoad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
