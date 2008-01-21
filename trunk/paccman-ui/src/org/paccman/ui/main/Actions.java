/*
 *
 *  Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
 *                   http://paccman.sourceforge.net 
 *
 *  This program is free software; you can redistribute it and/or modify      
 *  it under the terms of the GNU General Public License as published by      
 *  the Free Software Foundation; either version 2 of the License, or         
 *  (at your option) any later version.                                       
 *
 *  This program is distributed in the hope that it will be useful,           
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of            
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
 *  GNU General Public License for more details.                              
 *
 *  You should have received a copy of the GNU General Public License         
 *  along with this program; if not, write to the Free Software               
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *
 */

package org.paccman.ui.main;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.paccman.controller.DocumentController;
import org.paccman.db.PaccmanDao;
import org.paccman.preferences.ui.MainPrefs;
import org.paccman.tools.FileUtils;
import org.paccman.ui.PaccmanFileChooser;
import org.paccman.ui.common.MessageDialog;
import static org.paccman.ui.main.ContextMain.*;

/**
 * This class implements the processing of the application actions (<code>Open</code>, <code>New</code>, 
 * <code>Save</code>, <code>Save As</code>, ...)
 * 
 * For each operation <code>op</code>:
 * 
 * <ul>
 * <li>The <code><i>Op</i>Action</code> represents the <code>Action</code> class</li>
 * <li>The <code><i>op</i>Document</code> implements the full processing of the action 
 * including extra steps (e.g. for <code>New</code>, if a file is being editing, 
 * asks if it needs to be saved and saves the file if the user select <code>Yes</code>)</li>
 * <li>The <code>do<i>Op</i>Document</code> actually implements the action (e.g for <code>Save</code>,
 * write the document to the file)</li>
 * </ul>
 * 
 * @author joaof
 */
public class Actions {

    /**
     * The action's result possible values.
     */
    public static enum ActionResult {

        /**
         * The operation was successfully performed.
         */
        OK,
        /**
         * The operation was cancelled by the user.
         */
        CANCEL,
        /**
         * The operation failed.
         */
        FAILED
    }

    abstract static class PaccmanAction extends AbstractAction {

        /**
         * The path to the icon images location.
         */
        private static final String ROOT_PATH_RESSOURCES = "/org/paccman/ui/resources/images/";

        PaccmanAction(String name, String iconFileName) {
            super(name, new javax.swing.ImageIcon(PaccmanAction.class.getResource(ROOT_PATH_RESSOURCES + iconFileName)));
        }
    }

    //--------------------------------------------------------------------------
    // New Action
    //--------------------------------------------------------------------------
    static class NewAction extends PaccmanAction {

        public NewAction() {
            super("New...", "new.png");
        }

        public void actionPerformed(ActionEvent e) {
            ActionResult res = newDocument();
            switch (res) {
                case OK:
                case CANCEL:
                case FAILED:
                    //:TODO:do the appropriate
                    return;
                default:
                    throw new AssertionError("Unhandled ActionResult: " + res.toString());
            }
        }
    }

    private static ActionResult newDocument() {

        // Close current document if open
        if (isDocumentEdited()) {
            ActionResult closeDiag = closeDocument();
            if (closeDiag != ActionResult.OK) {
                return closeDiag;
            }
        }

        // Make a new document
        String title = (String) JOptionPane.showInputDialog(Main.getMain(), "Enter the title for the new account file", "Document title",
                JOptionPane.QUESTION_MESSAGE, null, null, "NewDocument");
        if (title != null) {
            setDocumentController(DocumentController.newDocument(title));
            getDocumentController().setHasChanged(true);

            getDocumentController().notifyChange();

            return ActionResult.OK;
        } else {

            return ActionResult.CANCEL;
        }
    }

    //--------------------------------------------------------------------------
    // Close Action
    //--------------------------------------------------------------------------
    static class CloseAction extends PaccmanAction {

        public CloseAction() {
            super("Close", "close.png");
        }

        public void actionPerformed(ActionEvent e) {
            ActionResult res = closeDocument();
            switch (res) {
                case OK:
                case CANCEL:
                case FAILED:
                    //:TODO:do the appropriate
                    return;
                default:
                    throw new AssertionError("Unhandled ActionResult: " + res.toString());
            }
        }
    }

    private static ActionResult closeDocument() {
        assert isDocumentEdited() : "Can not close if no document is loaded";

        // Save changes if any
        if (getDocumentController().isHasChanged()) {
            int save = JOptionPane.showConfirmDialog(Main.getMain(), "Do you want to save the changes ?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (save == JOptionPane.CANCEL_OPTION) {
                return ActionResult.CANCEL;
            } else if (save == JOptionPane.YES_OPTION) {
                Actions.ActionResult saveDiag;
                if (getDocumentController().getFile() == null) {
                    saveDiag = saveAsDocument();
                } else {
                    saveDiag = saveDocument();
                }
                if (saveDiag != ActionResult.OK) {
                    return saveDiag;
                }
            }
        }

        // Do close the document
        // Remove all the tabs from the main tabbed pane
        Main.getMain().hideTabbedPanes();

        setDocumentController(null);

        return ActionResult.OK;
    }

    //--------------------------------------------------------------------------
    // Save Action
    //--------------------------------------------------------------------------
    static class SaveAction extends PaccmanAction {

        public SaveAction() {
            super("Save", "save.png");
        }

        public void actionPerformed(ActionEvent e) {
            ActionResult res;
            if (getDocumentController().getFile() != null) {
                res = saveDocument();
            } else {
                res = saveAsDocument();
            }
            switch (res) {
                case OK:
                case CANCEL:
                case FAILED:
                    //:TODO:do the appropriate
                    return;
                default:
                    throw new AssertionError("Unhandled ActionResult: " + res.toString());
            }
        }
    }

    private static ActionResult saveDocument() {
        assert isDocumentEdited() : "'Save' should not be called when no document loaded";
        assert getDocumentController().getFile() != null : "'Save' should be called when the document has a file";

        // Backup previous file
        String backupFileName = getDocumentController().getFile() + "_" + FileUtils.getTimeString();
        getDocumentController().getFile().renameTo(new File(backupFileName));

        // Actually save the document to the file
        return doSaveDocument(getDocumentController().getFile());
    }

    private static ActionResult doSaveDocument(File saveFile) {
        logger.log(Level.INFO, "Saving to file {0}", saveFile);

        String tempDb = System.getProperty("java.io.tmpdir") + File.separator +
                "paccman_" + FileUtils.getTimeString();
        PaccmanDao db = new PaccmanDao(tempDb);
        try {
            // Write document to database 
            getDocumentController().getDocument().setLastUpdateDate(Calendar.getInstance());
            db.save(getDocumentController());

            // Zip to destination file
            final File tempDbFile = new File(tempDb);
            FileUtils.zipDirectory(tempDbFile, saveFile);

            // Remove temporary directory
            FileUtils.deleteDir(tempDbFile);

            // Update document controller and notify listeners
            getDocumentController().setFile(getDocumentController().getFile());
            getDocumentController().setHasChanged(false);
            getDocumentController().notifyChange();

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        logger.log(Level.INFO, "Done");
        return ActionResult.OK;
    }

    //--------------------------------------------------------------------------
    // Save As Action
    //--------------------------------------------------------------------------
    static class SaveAsAction extends PaccmanAction {

        public SaveAsAction() {
            super("Save as...", "save_as.png");
        }

        public void actionPerformed(ActionEvent e) {
            ActionResult res = saveAsDocument();
            switch (res) {
                case OK:
                case CANCEL:
                case FAILED:
                    //:TODO:do the appropriate
                    return;
                default:
                    throw new AssertionError("Unhandled ActionResult: " + res.toString());
            }
        }
    }

    private static File selectSaveFile() {
        assert (getDocumentController() != null);

        PaccmanFileChooser pfc = new PaccmanFileChooser();
        final int diagRes = pfc.showSaveDialog(Main.getMain());
        if (diagRes == JFileChooser.APPROVE_OPTION) {
            return pfc.getSelectedFile();
        } else {
            return null;
        }
    }

    private static ActionResult saveAsDocument() {
        assert isDocumentEdited() : "'saveAs' should not be called when no document loaded";

        // Select the file
        File saveFile = selectSaveFile();
        if (saveFile == null) {
            return ActionResult.CANCEL;
        }

        // Actually save the document to the file
        return doSaveDocument(saveFile);

    }

    //--------------------------------------------------------------------------
    // Open Action
    //--------------------------------------------------------------------------
    static class OpenAction extends PaccmanAction {

        public OpenAction() {
            super("Open", "open.png");
        }

        public void actionPerformed(ActionEvent e) {
            openDocument();
        }
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

    private static void openDocument() {

        // Close current document if open
        if (isDocumentEdited()) {
            Actions.ActionResult closeDiag = closeDocument();
            if (closeDiag != ActionResult.OK) {
                return; // result = closeDiag;
            }
        }

        // Do open a new document
        final File fileToOpen = selectOpenFile();
        if (fileToOpen == null) {
            return; // result = ActionResult.CANCEL;
        } else {
            doOpenFile(fileToOpen);
        }
    }

    public static void doOpenFile(final File fileToOpen) {
        new DialogWaitableWorker<Void, Void>("Opening file", 4, Main.getMain()) {

            private DocumentController newDocumentCtrl;

            @Override
            public void whenDone() {
                setDocumentController(newDocumentCtrl);
                getDocumentController().setFile(fileToOpen);
                logger.info("Done");
            }

            @Override
            public Void backgroundTask() throws Exception {
                nextStep("Unzipping file");
                // Unzip file (= zip of directory database) to temporary folder...
                String tempDb = System.getProperty("java.io.tmpdir") + File.separator +
                        "paccman_" + FileUtils.getTimeString();
                final File tempDbFile = new File(tempDb);
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
                try {
                    // Add file to MRU list
                    MainPrefs.addFilenameToMru(fileToOpen.getCanonicalPath());
                    MainPrefs.setLastSelectedFile(fileToOpen.getCanonicalPath());
                } catch (IOException ex) {
                    ex.printStackTrace(); //:TODO:
                    return null;
                }

                return null;

            }

            @Override
            public void whenFailed(Exception e) {
                if (e.getCause() instanceof FileNotFoundException) {
                    MessageDialog.showErrorMessage(Main.getMain(), "File %s not found", fileToOpen);
                    System.exit(-1); //:TODO: exit onlywhen argument passed in command line
                }
            }
        }.start();
    }

    //--------------------------------------------------------------------------
    // Quit Action
    //--------------------------------------------------------------------------
    static class QuitAction extends PaccmanAction {

        public QuitAction() {
            super("Quit", "exit.png");
        }

        public void actionPerformed(ActionEvent e) {
            ActionResult res = quit();
            switch (res) {
                case OK:
                    System.exit(0);
                    break;
                case CANCEL:
                case FAILED:
                    //:TODO:do the appropriate
                    return;
                default:
                    throw new AssertionError("Unhandled ActionResult: " + res.toString());
            }
        }
    }

    /**
     * Called when the user is existing the application.
     * @return OK, CANCEL or FAILED.
     */
    static Actions.ActionResult quit() {

        // Close current document if open
        if (isDocumentEdited()) {
            Actions.ActionResult closeDiag = closeDocument();
            if (closeDiag != ActionResult.OK) {
                return closeDiag;
            }
        }

        // Save preferences
        if ((Main.getMain().getState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            MainPrefs.setMaximized(true);
        } else {
            MainPrefs.setMaximized(false);
            MainPrefs.putLocation(Main.getMain().getLocationOnScreen());
            MainPrefs.putSize(Main.getMain().getSize());
        }

        return ActionResult.OK;
    }

    //--------------------------------------------------------------------------
    // Constructor
    //--------------------------------------------------------------------------
    private Actions() {
    }

    // -------------------------------------------------------------------------
    // Logging
    // -------------------------------------------------------------------------
    private static java.util.logging.Logger logger = org.paccman.tools.Logger.getDefaultLogger(Actions.class);
}
