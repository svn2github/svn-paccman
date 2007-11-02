/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.paccman.controller.DocumentController;
import org.paccman.paccman.Document;
import static org.paccman.db.PaccmanDbUtils.*;

/**
 *
 * @author joao
 */
public class PaccmanSave {

    private Connection connection;
    private Document doc;
    private Logger log = org.paccman.tools.Logger.getDefaultLogger(PaccmanSave.class);

    /**
     * 
     * @param connection 
     * @param ctrl
     * @throws java.sql.SQLException 
     */
    public void saveDocument(Connection connection, DocumentController ctrl) throws SQLException {
        log.finest("Start loading");

        this.connection = connection;
        this.doc = ctrl.getDocument();

        // Document information
        saveDocInfo();

    }

    private void saveDocInfo() throws SQLException {

        PreparedStatement stat = connection.prepareStatement("INSERT INTO DOCINFO(NAME, VALUE) VALUES(?,?)");

        stat.setString(1, DOCVERSION_KEY);
        stat.setString(2, CURRENT_VERSION);
        stat.executeUpdate();

        stat.setString(1, DOCUMENT_TITLE_KEY);
        stat.setString(2, doc.getTitle());
        stat.executeUpdate();

        stat.setString(1, CREATEDOC_DATE_KEY);
        stat.setDate(2, calendarToSqlDate(doc.getCreationDate()));
        stat.executeUpdate();

        stat.setString(1, UPDATEDOC_DATE_KEY);
        stat.setDate(2, calendarToSqlDate(doc.getLastUpdateDate()));
        stat.executeUpdate();

    }
}
