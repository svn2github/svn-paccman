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
import javax.swing.JFileChooser;
import org.paccman.controller.DocumentController;
import org.paccman.db.PaccmanDao;
import org.paccman.preferences.ui.MainPrefs;
import org.paccman.tools.FileUtils;
import org.paccman.ui.PaccmanFileChooser;
import org.paccman.ui.main.ContextMain;
import org.paccman.ui.main.DialogWaitableWorker;
import org.paccman.ui.main.Main;

/**
 * 
 * @author joao
 */
public class OpenAction extends PaccmanAction {

    File fileToSaveTo;
    File fileToOpen;
    
    OpenAction() {
        super("Open", "open.png", true);
    }

    Result doLogic() {
        if (ContextMain.isDocumentEdited()) {
            Result closeDiag =Actions.theCloseAction.doLogic();
            if (closeDiag == Result.CANCEL) {
                return Result.CANCEL;
            }
            fileToSaveTo = Actions.theCloseAction.fileToSaveTo;
        }
        fileToOpen = selectOpenFile();
        if (fileToOpen == null) {
            return Result.CANCEL;
        }
        return Result.OK;
    }
    
    /**
     * Method wrapping call to the open action when not called from menu or button
     * (used when passing filename as argument to PAccMan)
     * @param fileToOpen
     */
    public void doOpenFile(File fileToOpen) {
        this.fileToOpen = fileToOpen;
        doProcess();
    }
    
    private static File selectOpenFile() {

        JFileChooser fc = new PaccmanFileChooser();

        // Actually show the open file dialog
        int s = fc.showOpenDialog(Main.getMain());

        // Check return value
        if (s == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }

    void doProcess() {

        new DialogWaitableWorker<Void, Void>("Opening file", -1, Main.getMain()) {

            private DocumentController newDocumentCtrl;
            
            @Override
            public void whenDone() {
                ContextMain.setDocumentController(newDocumentCtrl);
                ContextMain.getDocumentController().setFile(fileToOpen);
            }

            @Override
            public void whenFailed(Exception e) {
                throw new UnsupportedOperationException("Failed to open file: " + e.getMessage());
            }

            @Override
            public Void backgroundTask() throws Exception {
                // Save current edited file if required
                if (fileToSaveTo != null) {
                    Actions.getSaveAction().doSaveFile(fileToSaveTo, this);
                }

                // Unzip file (= zip of directory database) to temporary folder...
                nextStep("Unzipping file");
                String tempDb = System.getProperty("java.io.tmpdir") + File.separator +
                        "paccman_" + FileUtils.getTimeString();
                final File tempDbFile = new File(tempDb);
                tempDbFile.mkdirs();
                FileUtils.unzipDirectory(fileToOpen, tempDbFile);

                // ...open database
                nextStep("Opening database");
                newDocumentCtrl = new DocumentController();
                PaccmanDao db = new PaccmanDao(new File(tempDbFile.getAbsolutePath()).getPath());
                db.load(newDocumentCtrl);

                // ... remove temporary folder
                nextStep("Cleaning");
                FileUtils.deleteDir(tempDbFile);

                // Keep last directory in preferences
                nextStep("Updating preferences");
                String path = fileToOpen.getParent();
                MainPrefs.putDataDirectory(path);

                // Add file to MRU list
                MainPrefs.addFilenameToMru(fileToOpen.getCanonicalPath());
                MainPrefs.setLastSelectedFile(fileToOpen.getCanonicalPath());
                
                return null;
            }

        }.start();
    }
    
}
