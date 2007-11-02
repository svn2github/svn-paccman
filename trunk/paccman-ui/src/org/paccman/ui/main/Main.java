/*
Copyright (C)    2005 Joao F. (joaof@sourceforge.net)
http://paccman.sourceforge.net
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.paccman.ui.main;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.paccman.calc.CalculatorFrame;
import org.paccman.controller.AccountController;
import org.paccman.controller.DocumentController;
import org.paccman.controller.PaccmanView;
import org.paccman.preferences.ui.MainPrefs;
import org.paccman.ui.*;
import org.paccman.ui.accounts.AccountFormTab;
import org.paccman.ui.banks.BankFormTab;
import org.paccman.ui.categories.CategoryFormTab;
import org.paccman.ui.paymentmethods.PaymentMethodFormTab;
import org.paccman.ui.payees.PayeeFormTab;
import org.paccman.ui.schedules.ScheduleFormTab;
import org.paccman.ui.transactions.TransactionFormTab;
import org.paccman.ui.welcome.WelcomePaneTab;
import org.paccman.xml.PaccmanFile;
import org.paccman.xml.PaccmanIOException;

/**
 *
 * @author  joao
 */
public class Main extends javax.swing.JFrame implements PaccmanView {

    DocumentController documentController;

    public TransactionFormTab getTransactionFormTab() {
        return transactionFormTab;
    }

    /** Creates new form Main */
    public Main() {
        initComponents();
        initMyComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        toolbarsPanel = new javax.swing.JPanel();
        mainToolBar = new javax.swing.JToolBar();
        openBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        mainToolBar.add(new JToolBar.Separator());
        quitBtn = new javax.swing.JButton();
        navigatorToolBar = new javax.swing.JToolBar();
        backBtn = new javax.swing.JButton();
        forwardBtn = new javax.swing.JButton();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMnu = new javax.swing.JMenu();
        newMnu = new javax.swing.JMenuItem();
        openMnu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        closeMnu = new javax.swing.JMenuItem();
        saveMnu = new javax.swing.JMenuItem();
        saveAsMnu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        propertiesMnu = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        quitMnu = new javax.swing.JMenuItem();
        optionMnu = new javax.swing.JMenu();
        showStartDialog = new javax.swing.JCheckBoxMenuItem();
        openLastFileMnu = new javax.swing.JCheckBoxMenuItem();
        toolsMnu = new javax.swing.JMenu();
        calculatorMnu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(NO_DOCUMENT_TITLE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/org/paccman/ui/resources/images/euro.png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().add(mainTabbedPane, java.awt.BorderLayout.CENTER);

        toolbarsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        openBtn.setAction(openAction);
        openBtn.setText("");
        mainToolBar.add(openBtn);

        saveBtn.setAction(saveAction);
        saveBtn.setText("");
        mainToolBar.add(saveBtn);

        quitBtn.setAction(quitAction);
        quitBtn.setText("");
        mainToolBar.add(quitBtn);

        toolbarsPanel.add(mainToolBar);

        navigatorToolBar.setVisible(false);

        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/paccman/ui/resources/images/go_back.png"))); // NOI18N
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        navigatorToolBar.add(backBtn);

        forwardBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/paccman/ui/resources/images/go_forward.png"))); // NOI18N
        forwardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardBtnActionPerformed(evt);
            }
        });
        navigatorToolBar.add(forwardBtn);

        toolbarsPanel.add(navigatorToolBar);

        getContentPane().add(toolbarsPanel, java.awt.BorderLayout.NORTH);

        fileMnu.setText("File");

        newMnu.setAction(newAction);
        fileMnu.add(newMnu);

        openMnu.setAction(new OpenAction());
        fileMnu.add(openMnu);
        fileMnu.add(jSeparator1);

        closeMnu.setAction(new CloseAction());
        fileMnu.add(closeMnu);

        saveMnu.setAction(saveAction);
        fileMnu.add(saveMnu);

        saveAsMnu.setAction(saveAsAction);
        fileMnu.add(saveAsMnu);
        fileMnu.add(jSeparator2);

        propertiesMnu.setAction(new PropertiesAction());
        fileMnu.add(propertiesMnu);
        fileMnu.add(jSeparator3);

        quitMnu.setAction(quitAction);
        fileMnu.add(quitMnu);

        mainMenuBar.add(fileMnu);

        optionMnu.setText("Options");

        showStartDialog.setSelected(MainPrefs.getShowStartDialog());
        showStartDialog.setText("Show start dialog");
        showStartDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showStartDialogActionPerformed(evt);
            }
        });
        optionMnu.add(showStartDialog);

        openLastFileMnu.setSelected(MainPrefs.getOpenLastSelectedFile());
        openLastFileMnu.setText("Open last file");
        openLastFileMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openLastFileMnuActionPerformed(evt);
            }
        });
        optionMnu.add(openLastFileMnu);

        mainMenuBar.add(optionMnu);

        toolsMnu.setText("Tools");

        calculatorMnu.setText("Calculator");
        calculatorMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorMnuActionPerformed(evt);
            }
        });
        toolsMnu.add(calculatorMnu);

        mainMenuBar.add(toolsMnu);

        setJMenuBar(mainMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    CalculatorFrame calculatorFrame = new CalculatorFrame();

    private void calculatorMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorMnuActionPerformed
        calculatorFrame.setVisible(true);
    }//GEN-LAST:event_calculatorMnuActionPerformed

    private void forwardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardBtnActionPerformed
        JOptionPane.showMessageDialog(this, ":TODO: NOT IMPLEMENTED YET", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_forwardBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        JOptionPane.showMessageDialog(this, ":TODO: NOT IMPLEMENTED YET", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_backBtnActionPerformed

    private void openLastFileMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openLastFileMnuActionPerformed
        MainPrefs.setOpenLastSelectedFile(!MainPrefs.getOpenLastSelectedFile());
    }//GEN-LAST:event_openLastFileMnuActionPerformed

    public enum OperationStatus {

        OK, CANCEL, FAILED
    }
     {
    }

    private int confirmSave() {
        assert (documentController != null) && documentController.isHasChanged();

        return JOptionPane.showConfirmDialog(this, "Do you want to save the changes ?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    private File selectSaveFile() {
        assert (documentController != null);

        PaccmanFileChooser pfc = new PaccmanFileChooser();
        if (pfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return pfc.getSelectedFile();
        } else {
            return null;
        }
    }

    private File selectOpenFile() {

        JFileChooser fc = new PaccmanFileChooser();

        // Actually show the open file dialog
        int s = fc.showOpenDialog(this);

        // Check return value
        if (s == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }

    public OperationStatus closeDocument() {
        assert isDocumentLoaded() : "Can not close if no document is loaded";

        // Save changes if any
        if (documentController.isHasChanged()) {
            int save = confirmSave();
            if (save == JOptionPane.CANCEL_OPTION) {
                return OperationStatus.CANCEL;
            } else if (save == JOptionPane.YES_OPTION) {
                OperationStatus saveDiag;
                if (documentController.getFile() == null) {
                    saveDiag = saveAsDocument();
                } else {
                    saveDiag = saveDocument();
                }
                if (saveDiag != OperationStatus.OK) {
                    return saveDiag;
                }
            }
        }

        // Do close the document
        // Remove all the tabs from the main tabbed pane
        mainTabbedPane.removeAll();

        documentController = null;
        documentControllerUpdated();

        updateAction();

        return OperationStatus.OK;
    }

    private void copyFile(File inputFile, File outputFile) throws IOException {
        // Create channel on the source
        FileChannel srcChannel = new FileInputStream(inputFile).getChannel();

        // Create channel on the destination
        FileChannel dstChannel = new FileOutputStream(outputFile).getChannel();

        // Copy file contents from source to destination
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

        // Close the channels
        srcChannel.close();
        dstChannel.close();
    }

    class ReadWorker extends SwingWorker<Object, Object> {

        File file;
        Main main;

        public ReadWorker(File file, Main main) {
            this.file = file;
            this.main = main;
        }

        @Override
        protected Object doInBackground() throws Exception {
            PaccmanFile paccmanFile = new PaccmanFile();
            DocumentController newDocumentController = new DocumentController();
            logger.fine("Opening file " + file.getAbsolutePath());
            paccmanFile.read(file, newDocumentController);
            logger.fine("File opened");

            // Make a copy of the file
            File fileOut = new File(file.getAbsolutePath() + new SimpleDateFormat("-yyMMddHHmmss").format(new Date()));
            try {
                copyFile(file, fileOut);
                logger.fine("Copied file to " + fileOut.getAbsolutePath());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException ex) {
                throw new PaccmanIOException("Failed to make a save copy of the file");
            }

            // Register views
            newDocumentController.registerView(main);
            // Register views
            newDocumentController.notifyChange();
            // Register views
            documentController = newDocumentController;

            documentController.setFile(file);

            // Keep last directory in preferences
            String path = file.getParent();
            MainPrefs.putDataDirectory(path);

            // Add file to MRU list
            try {

                // Add file to MRU list
                MainPrefs.addFilenameToMru(file.getCanonicalPath());
                MainPrefs.setLastSelectedFile(file.getCanonicalPath());
            } catch (IOException ex) {
                ex.printStackTrace(); //:TODO:
            }
            return null;
        }

        @Override
        protected void process(List<Object> chunks) {
        //:TODO:
        }

        @Override
        protected void done() {
            super.done();
            documentControllerUpdated();
            updateAction();
            documentController.notifyChange();
        //:TODO:handle error when opening
        }
    }

    public void openFile(File file) throws PaccmanIOException {
        ReadWorker rw = new ReadWorker(file, this);
        rw.execute();
    }

    private OperationStatus doOpenFile(File filein) {
        try {
            openFile(filein);
            return OperationStatus.OK;
        } catch (PaccmanIOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to open file '" + filein.getAbsolutePath() + "' (" + ex.getMessage() + ")", "Error", JOptionPane.ERROR_MESSAGE);
            return OperationStatus.FAILED;
        }
    }

    public OperationStatus openDocument(File file) {

        // Close current document if open
        if (isDocumentLoaded()) {
            OperationStatus closeDiag = closeDocument();
            if (closeDiag != OperationStatus.OK) {
                return closeDiag;
            }
        }

        // Do open a new document
        OperationStatus result = OperationStatus.FAILED;

        File fileToOpen = file == null ? selectOpenFile() : file;
        while (result == OperationStatus.FAILED) {
            if (fileToOpen == null) {
                return OperationStatus.CANCEL;
            } else {
                result = doOpenFile(fileToOpen);
                if (result == OperationStatus.FAILED) {
                    fileToOpen = selectOpenFile();
                }
            }
        }
        return result;
    }

    public OperationStatus newDocument() {

        // Close current document if open
        if (isDocumentLoaded()) {
            OperationStatus closeDiag = closeDocument();
            if (closeDiag != OperationStatus.OK) {
                return closeDiag;
            }
        }

        // Make a new document
        String title = (String) JOptionPane.showInputDialog(this, "Enter the title for the new account file", "Document title",
                JOptionPane.QUESTION_MESSAGE, null, null, "NewDocument");
        if (title != null) {
            documentController = new DocumentController();
            documentController.getDocument().setTitle(title);
            documentController.setHasChanged(true);

            documentControllerUpdated();

            updateAction();

            return OperationStatus.OK;
        } else {

            return OperationStatus.CANCEL;
        }
    }

    public OperationStatus saveDocument(File saveFile) {
        PaccmanFile pf = new PaccmanFile();
        try {
            pf.write(saveFile, documentController);
        } catch (PaccmanIOException pie) {
            pie.printStackTrace();
            return OperationStatus.FAILED;
        }
        return OperationStatus.OK;
    }

    public OperationStatus saveDocument() {
        assert isDocumentLoaded() : "'save' should not be called when no document loaded";
        assert documentController.getFile() != null : "'save' should be called when the document has a file";

        // Actually save the document to the file
        if (saveDocument(documentController.getFile()) == OperationStatus.OK) {

            // Update document controller
            documentController.setFile(documentController.getFile());
            setDocumentChanged(false);

            return OperationStatus.OK;
        } else {

            return OperationStatus.FAILED;
        }
    }

    public OperationStatus saveAsDocument() {
        assert isDocumentLoaded() : "'saveAs' should not be called when no document loaded";

        // Select the file
        File saveFile = selectSaveFile();
        if (saveFile == null) {
            return OperationStatus.CANCEL;
        }

        // Actually save the document to the file
        if (saveDocument(saveFile) == OperationStatus.OK) {

            // Update document controller
            documentController.setFile(saveFile);
            setDocumentChanged(false);

            return OperationStatus.OK;
        } else {

            return OperationStatus.FAILED;
        }
    }

    public OperationStatus quit() {

        // Close current document if open
        if (isDocumentLoaded()) {
            OperationStatus closeDiag = closeDocument();
            if (closeDiag != OperationStatus.OK) {
                return closeDiag;
            }
        }

        // Save preferences
        if ((getState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            MainPrefs.setMaximized(true);
        } else {
            MainPrefs.setMaximized(false);
            MainPrefs.putLocation(getLocationOnScreen());
            MainPrefs.putSize(getSize());
        }

        System.exit(0);
        return OperationStatus.OK;
    }

    public abstract class PaccmanAction extends AbstractAction {

        public PaccmanAction(String name, Icon icon, boolean enabled) {
            super(name, icon);
            setEnabled(enabled);
        }

        public PaccmanAction(String name, Icon icon) {
            super(name, icon);
        }
    }

    public final class NewAction extends PaccmanAction {

        public NewAction() {
            super("New...", new javax.swing.ImageIcon(OpenAction.class.getResource("/org/paccman/ui/resources/images/new.png")));
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            newDocument();
        }
    }

    public final class OpenAction extends PaccmanAction {

        public OpenAction() {
            super("Open", new javax.swing.ImageIcon(OpenAction.class.getResource("/org/paccman/ui/resources/images/open.png")));
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            openDocument(null);
        }
    }

    public final class CloseAction extends PaccmanAction {

        public CloseAction() {
            super("Close", new javax.swing.ImageIcon(SaveAction.class.getResource("/org/paccman/ui/resources/images/close.png")), false);
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            closeDocument();
        }
    }

    public final class SaveAction extends PaccmanAction {

        public SaveAction() {
            super("Save", new javax.swing.ImageIcon(SaveAction.class.getResource("/org/paccman/ui/resources/images/save.png")), false);
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (documentController.getFile() != null) {
                saveDocument();
            } else {
                saveAsDocument();
            }
        }
    }

    public final class SaveAsAction extends PaccmanAction {

        public SaveAsAction() {
            super("Save as...", new javax.swing.ImageIcon(SaveAction.class.getResource("/org/paccman/ui/resources/images/save_as.png")), false);
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            saveAsDocument();
        }
    }

    public final class QuitAction extends PaccmanAction {

        public QuitAction() {
            super("Quit", new javax.swing.ImageIcon(SaveAction.class.getResource("/org/paccman/ui/resources/images/exit.png")));
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            quit();
        }
    }

    public final class PropertiesAction extends PaccmanAction {

        public PropertiesAction() {
            super("Properties", new javax.swing.ImageIcon(SaveAction.class.getResource("/org/paccman/ui/resources/images/properties.png")), false);
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            PropertiesFrame pf = new PropertiesFrame();
            documentController.registerView(pf);
            pf.onChange(documentController);
            pf.setVisible(true);
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        quit();
    }//GEN-LAST:event_formWindowClosing

    private void showStartDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showStartDialogActionPerformed
        MainPrefs.setShowStartDialog(!MainPrefs.getShowStartDialog());
    }//GEN-LAST:event_showStartDialogActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        String fileToOpen = null;

        if (startOption.getFilename() != null) {
            // If the user entered a filename in the command line, open the
            // entered file
            fileToOpen = startOption.getFilename();
        } else if (MainPrefs.getOpenLastSelectedFile()) {
            // If the "Open last file" option is set, open the last file
            fileToOpen = MainPrefs.getLastSelectedFile();
        }

        if (fileToOpen != null) {
            //:DEBUG             try {
            //                openFile(new File(fileToOpen));
            openDocument(new File(fileToOpen));
            return;
        /*:DEBUG            } catch (PaccmanIOException ex) {
            ex.printStackTrace();//:TODO:
            }*/
        }

        // If "show start dialog" option is true, show the start dialog
        if (MainPrefs.getShowStartDialog()) {
            StartDialog startDialog = new StartDialog(this, true);
            StartDialog.Option opt = startDialog.doModal();
            switch (opt) {
                case OPEN_FILE:
                    documentControllerUpdated();
                    updateAction();
                    break;
                case NEW_FILE:
                    break;
                case CANCEL:
                    break;
                default:
                    break;
            }
            showStartDialog.setSelected(MainPrefs.getShowStartDialog());
        }
    }//GEN-LAST:event_formWindowOpened

    private void initMyComponents() {
        // Look and feel menu
        optionMnu.addSeparator();
        optionMnu.add(new LookAndFeelMenu(this));

        calculatorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void showTabbedPanes() {
        assert documentController != null : "The document controller must exists";

        // Welcome pane
        welcomePane = new WelcomePaneTab();
        mainTabbedPane.addTab("Welcome", welcomePane);
        welcomePane.registerToDocumentCtrl();

        // Transactions pane
        transactionFormTab = new TransactionFormTab();
        mainTabbedPane.addTab("Transactions", transactionFormTab);
        transactionFormTab.registerToDocumentCtrl();

        // Accounts pane
        accountsPanel = new AccountFormTab();
        mainTabbedPane.addTab("Accounts", accountsPanel);
        accountsPanel.registerToDocumentCtrl();

        // Banks pane
        bankFormTab = new BankFormTab();
        mainTabbedPane.addTab("Banks", bankFormTab);
        bankFormTab.registerToDocumentCtrl();

        // Categories pane
        categoryFormTab = new CategoryFormTab();
        mainTabbedPane.addTab("Categories", categoryFormTab);
        categoryFormTab.registerToDocumentCtrl();

        // Payee pane
        payeeFormTab = new PayeeFormTab();
        mainTabbedPane.addTab("Payees", payeeFormTab);
        payeeFormTab.registerToDocumentCtrl();

        // PaymentMethod pane
        paymentMethodFormTab = new PaymentMethodFormTab();
        mainTabbedPane.addTab("Payment Method", paymentMethodFormTab);
        paymentMethodFormTab.registerToDocumentCtrl();

        // Schedules pane
        scheduleFormTab = new ScheduleFormTab();
        mainTabbedPane.addTab("Schedules", scheduleFormTab);
        scheduleFormTab.registerToDocumentCtrl();
    }

    public void gotoAccountTransactionTab(AccountController account) {
        transactionFormTab.setSelectedAccount(account);
        mainTabbedPane.setSelectedComponent(transactionFormTab);
    }

    public void gotoWelcomeTab() {
        mainTabbedPane.setSelectedComponent(welcomePane);
    }

    private int showQuitDialog() {
        if (JOptionPane.showConfirmDialog(this, "Do you really want to quit ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return JOptionPane.YES_OPTION;
        } else {
            return JOptionPane.NO_OPTION;
        }
    }
    static Main main;

    public static Main getMain() {
        return main;
    }

    public static DocumentController getDocumentCtrl() {
        return main != null ? main.documentController : null;
    }

    static class StartOption {

        String filename;

        public String getFilename() {
            return filename;
        }

        public void parse(String[] options) {
            if (options.length == 1) {
                filename = options[0];
            }
        }
    }
    static StartOption startOption;
    static {
        startOption = new StartOption();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //:TODO:START:trying a new look and feel
//        try {
//            UIManager.installLookAndFeel("Plastic3DLookAndFeel", "Plastic3DLookAndFeel");
//            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
//        } catch (Exception e) {
//        }
        //:TODO:END:
        startOption.parse(args);
        //:TODO:END:
        java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
            public void run() {
                        main = new Main //:TODO:END:
                                ();
                        main.setLocation(MainPrefs.getLocation());
                        if (MainPrefs.isMaximized()) {
                            main.setState(MAXIMIZED_BOTH);
                        } else {
                            main.setSize(MainPrefs.getSize());
                        }
                        main.setVisible(true);
                    }
                });
    }

    @Override
    public void onChange(org.paccman.controller.Controller controller) {
        DocumentController docCtrl = (DocumentController) controller;
        setTitle(docCtrl.getDocument().getTitle());
        updateAction();
        setTitle(getTitleString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton backBtn;
    public javax.swing.JMenuItem calculatorMnu;
    public javax.swing.JMenuItem closeMnu;
    public javax.swing.JMenu fileMnu;
    public javax.swing.JButton forwardBtn;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JMenuBar mainMenuBar;
    public javax.swing.JTabbedPane mainTabbedPane;
    public javax.swing.JToolBar mainToolBar;
    public javax.swing.JToolBar navigatorToolBar;
    public javax.swing.JMenuItem newMnu;
    public javax.swing.JButton openBtn;
    public javax.swing.JCheckBoxMenuItem openLastFileMnu;
    public javax.swing.JMenuItem openMnu;
    public javax.swing.JMenu optionMnu;
    public javax.swing.JMenuItem propertiesMnu;
    public javax.swing.JButton quitBtn;
    public javax.swing.JMenuItem quitMnu;
    public javax.swing.JMenuItem saveAsMnu;
    public javax.swing.JButton saveBtn;
    public javax.swing.JMenuItem saveMnu;
    public javax.swing.JCheckBoxMenuItem showStartDialog;
    public javax.swing.JPanel toolbarsPanel;
    public javax.swing.JMenu toolsMnu;
    // End of variables declaration//GEN-END:variables

    WelcomePaneTab welcomePane;
    AccountFormTab accountsPanel;
    BankFormTab bankFormTab;
    CategoryFormTab categoryFormTab;
    TransactionFormTab transactionFormTab;
    PayeeFormTab payeeFormTab;
    PaymentMethodFormTab paymentMethodFormTab;
    ScheduleFormTab scheduleFormTab;
    OpenAction openAction = new OpenAction();
    CloseAction closeAction = new CloseAction();
    SaveAction saveAction = new SaveAction();
    SaveAsAction saveAsAction = new SaveAsAction();
    NewAction newAction = new NewAction();
    QuitAction quitAction = new QuitAction();

    /**
     * Getter for property documentLoaded.
     * @return Value of property documentLoaded.
     */
    public boolean isDocumentLoaded() {
        return documentController != null;
    }

    /**
     * Title when no document is being edited
     */
    private final String NO_DOCUMENT_TITLE = "[No document]";

    private String getTitleString() {
        if (documentController == null) {
            return NO_DOCUMENT_TITLE;
        } else {
            if (documentController.isHasChanged()) {
                return documentController.getDocument().getTitle() + "*";
            } else {
                return documentController.getDocument().getTitle();
            }
        }
    }

    /**
     * Called when the DocumentController changes
     */
    private void documentControllerUpdated() {
        if (documentController == null) {
            mainTabbedPane.removeAll();
            setTitle(getTitleString());
        } else {
            showTabbedPanes();
            documentController.registerView(this);
            documentController.notifyChange();
        }
    }

    public static void setDocumentChanged(boolean changed) {
        main.getDocumentCtrl().setHasChanged(changed);
        main.onChange(main.getDocumentCtrl());
    }

    private void updateAction() {
        closeMnu.getAction().setEnabled(isDocumentLoaded());
        saveMnu.getAction().setEnabled(isDocumentLoaded() && (documentController.isHasChanged()));
        saveAsMnu.getAction().setEnabled(isDocumentLoaded());
        propertiesMnu.getAction().setEnabled(isDocumentLoaded());
    }

    //
    // Logging
    //

    private static Logger logger = Logger.getLogger("org.paccman");
    private static Handler consoleHandler = new ConsoleHandler();
     {
        consoleHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
    }
}
