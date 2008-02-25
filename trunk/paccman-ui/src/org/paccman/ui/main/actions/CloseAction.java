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
import org.paccman.ui.main.ContextMain;
import org.paccman.ui.main.DialogWaitableWorker;
import org.paccman.ui.main.Main;
import static org.paccman.ui.main.ContextMain.*;

/**
 * 
 * @author joao
 */
class CloseAction extends PaccmanAction {

    File fileToSaveTo = null;

    /**
     * TODO
     */
    public CloseAction() {
        super("Close", "close.png", false);
    }

    Result doLogic() {
        assert ContextMain.isDocumentEdited() : "Can not close when no document edited";

        // Save changes if any
        if (getDocumentController().isHasChanged()) {
            int save = JOptionPane.showConfirmDialog(Main.getMain(), "Do you want to save the changes ?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (save == JOptionPane.CANCEL_OPTION) {
                return Result.CANCEL;
            } else if (save == JOptionPane.NO_OPTION) {
                return Result.OK;
            } else if (save == JOptionPane.YES_OPTION) {
                Result saveDiag;
                saveDiag = Actions.theSaveAction.doLogic();
                if (saveDiag == Result.CANCEL) {
                    return Result.CANCEL;
                }
                fileToSaveTo = Actions.theSaveAction.fileToSaveTo;
            }
        }

        return Result.OK;
    }

    void doProcess() {

        new DialogWaitableWorker<Void, Void>("Closing file", -1, Main.getMain()) {

            @Override
            public void whenDone() {
                setDocumentController(null);
            }

            @Override
            public void whenFailed(Exception e) {
                throw new UnsupportedOperationException("Failed to save file: " + e.getMessage());
            }

            @Override
            public Void backgroundTask() throws Exception {
                if (fileToSaveTo != null) {
                    Actions.getSaveAction().doSaveFile(fileToSaveTo, this);
                }
                Thread.sleep(10000); //TODO: remove. for test only.
                return null;
            }

        }.start();
    }
}
