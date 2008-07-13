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

import java.awt.Frame;
import java.io.File;
import org.paccman.preferences.ui.MainPrefs;
import org.paccman.ui.main.DialogWaitableWorker;
import org.paccman.ui.main.Main;
import static org.paccman.ui.main.ContextMain.*;

/**
 * 
 * @author joao
 */
public class QuitAction extends PaccmanAction {

    File fileToSaveTo = null;

    QuitAction() {
        super("Quit", "exit.png", true);
    }

    @Override
    void doReset() {
        fileToSaveTo = null;
    }
    
    @Override
    Result doLogic() {

        if (!isDocumentEdited()) {
            return Result.OK;
        } else {
            // A document is edited. Close it.
            Result closeDiag = Actions.theCloseAction.doLogic();
            if (closeDiag != Result.CANCEL) {
                fileToSaveTo = Actions.theCloseAction.fileToSaveTo;
                return Result.OK;
            } else {
                return Result.CANCEL;
            }
        }

    }

    @Override
    void doProcess() {

        new DialogWaitableWorker<Void, Void>("Quitting", -1, Main.getMain()) {

            @Override
            public void whenDone() {
                System.exit(1);
            }

            @Override
            public void whenFailed(Exception e) {
                throw new UnsupportedOperationException("Failed to save file: " + e.getMessage());
            }

            @Override
            public Void backgroundTask() throws Exception {
                // Save file if any to save.
                if (fileToSaveTo != null) {
                    Actions.getSaveAction().doSaveFile(fileToSaveTo, this);
                }

                // Save preferences
                nextStep("Saving preferences");
                if ((Main.getMain().getState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                    MainPrefs.setMaximized(true);
                } else {
                    MainPrefs.setMaximized(false);
                    MainPrefs.putLocation(Main.getMain().getLocationOnScreen());
                    MainPrefs.putSize(Main.getMain().getSize());
                }
                return null;
            }

        }.start();
    }

}
