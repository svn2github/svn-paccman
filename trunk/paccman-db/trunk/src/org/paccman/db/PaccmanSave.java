/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.util.logging.Logger;
import org.paccman.controller.DocumentController;
import org.paccman.paccman.Document;

/**
 *
 * @author joao
 */
public class PaccmanSave {

    private Logger log = org.paccman.tools.Logger.getDefaultLogger(PaccmanSave.class);

    /**
     * 
     * @param ctrl
     */
    public void saveDocument(DocumentController ctrl) {
        log.finest("Start loading");

        Document doc = ctrl.getDocument();

    }
}
