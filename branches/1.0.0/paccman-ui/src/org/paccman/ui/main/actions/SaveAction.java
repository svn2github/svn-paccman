/*
 *
 *  Copyright (C)    2008 Joao F. (joaof@sourceforge.net)
 *                   http://paccman.sourceforge.net 
 *
 *  This file is part of PAccMan.
 *
 *  PAccMan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PAccMan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PAccMan.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.paccman.ui.main.actions;

import java.io.File;
import java.io.File;
import java.util.Calendar;
import java.util.logging.Level;
import org.paccman.db.PaccmanDao;
import org.paccman.tools.FileUtils;
import javax.swing.JFileChooser;
import org.paccman.ui.PaccmanFileChooser;
import org.paccman.ui.main.ContextMain;
import org.paccman.ui.main.DialogWaitableWorker;
import org.paccman.ui.main.Main;
import static org.paccman.ui.main.ContextMain.*;

/**
 * Action for "Save" and "Save As" actions.
 * @author jfer
 */
public class SaveAction extends PaccmanAction {

    File fileToSaveTo;

    private boolean saveAs;

    SaveAction(boolean saveAs) {
        super(saveAs ? "Save As..." : "Save", "save.png", false);
        this.saveAs = saveAs;
    }

    @Override
    void doReset() {
        fileToSaveTo = null;
    }
    
    @Override
    Result doLogic() {
        assert ContextMain.isDocumentEdited() : "Can not save when no document edited";

        if (saveAs || (ContextMain.getDocumentController().getFile() == null)) {
            // SaveAs action or document has never been saved (newly created)
            fileToSaveTo = selectSaveFile();
            if (fileToSaveTo == null) {
                return Result.CANCEL;
            }
        } else if (!saveAs && (ContextMain.getDocumentController().getFile() != null)) {
            fileToSaveTo = ContextMain.getDocumentController().getFile();
        }
        return Result.OK;
    }

    @Override
    void doProcess() {
        assert fileToSaveTo != null : "No file selected.";

        new DialogWaitableWorker<Void, Void>("Saving file", -1, Main.getMain()) {

            @Override
            public void whenDone() {
                // Update document controller and notify listeners
                getDocumentController().setFile(fileToSaveTo);
                getDocumentController().setHasChanged(false);
                getDocumentController().notifyChange();
            }

            @Override
            public void whenFailed(Exception e) {
                throw new UnsupportedOperationException("Failed to save file: " + e.getMessage());
            }

            @Override
            public Void backgroundTask() throws Exception {
                doSaveFile(fileToSaveTo, this);
                Thread.sleep(10000); //TODO: remove. for debug only.
                return null;
            }

        }.start();
    }

    File selectSaveFile() {
        assert (getDocumentController() != null);

        PaccmanFileChooser pfc = new PaccmanFileChooser();
        final int diagRes = pfc.showSaveDialog(Main.getMain());
        if (diagRes == JFileChooser.APPROVE_OPTION) {
            return pfc.getSelectedFile();
        } else {
            return null;
        }
    }

    void doSaveFile(File saveFile, DialogWaitableWorker worker) throws Exception {
        logger.log(Level.INFO, "Saving to file {0}", saveFile);

        String tempDb = System.getProperty("java.io.tmpdir") + File.separator +
                "paccman_" + FileUtils.getTimeString();
        PaccmanDao db = new PaccmanDao(tempDb);

        // Write document to database 
        worker.nextStep("Writing to database");
        getDocumentController().getDocument().setLastUpdateDate(Calendar.getInstance());
        db.save(getDocumentController());

        // Zip to destination file
        worker.nextStep("Compressing file");
        final File tempDbFile = new File(tempDb);
        FileUtils.zipDirectory(tempDbFile, saveFile);

        // Remove temporary directory
        worker.nextStep("Cleaning");
        FileUtils.deleteDir(tempDbFile);

        logger.log(Level.INFO, "Done");
    }

    // -------------------------------------------------------------------------
    // Logging
    // -------------------------------------------------------------------------
    private static java.util.logging.Logger logger = org.paccman.tools.Logger.getDefaultLogger(Actions.class);
}
