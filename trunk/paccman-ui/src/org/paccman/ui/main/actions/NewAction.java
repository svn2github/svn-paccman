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
import javax.swing.JOptionPane;
import org.paccman.controller.DocumentController;
import org.paccman.ui.main.ContextMain;
import org.paccman.ui.main.DialogWaitableWorker;
import org.paccman.ui.main.Main;

/**
 * 
 * @author joao
 */
public class NewAction extends PaccmanAction {

    File fileToSaveTo;

    String title;

    NewAction() {
        super("New", "new.png", true);
    }

    Result doLogic() {
        // Close file if needed
        if (ContextMain.isDocumentEdited()) {
            Result closeDiag = Actions.theCloseAction.doLogic();
            if (closeDiag == Result.CANCEL) {
                return Result.CANCEL;
            }
            fileToSaveTo = Actions.theCloseAction.fileToSaveTo;
        }

        // Make a new document
        title = (String) JOptionPane.showInputDialog(Main.getMain(), "Enter the title for the new account file", "Document title",
                JOptionPane.QUESTION_MESSAGE, null, null, "NewDocument");
        return (title != null) ? Result.OK : Result.CANCEL;

    }

    void doProcess() {

        new DialogWaitableWorker<Void, Void>("Creating new file", -1, Main.getMain()) {

            @Override
            public void whenDone() {
                ContextMain.setDocumentController(DocumentController.newDocument(title));
                ContextMain.getDocumentController().setHasChanged(true);

                ContextMain.getDocumentController().notifyChange();
            }

            @Override
            public void whenFailed(Exception e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Void backgroundTask() throws Exception {
                // Save current edited file if required
                if (fileToSaveTo != null) {
                    Actions.getSaveAction().doSaveFile(fileToSaveTo, this);
                }
                return null;
            }

        }.start();
    }

}
