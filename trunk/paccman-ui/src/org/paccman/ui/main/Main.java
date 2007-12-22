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

import java.io.File;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import org.paccman.calc.CalculatorFrame;
import org.paccman.controller.AccountController;
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
import static org.paccman.ui.main.ContextMain.*;
import static org.paccman.ui.main.Actions.ActionResult.*;

/**
 *
 * @author  joao
 */
public class Main extends javax.swing.JFrame implements PaccmanView {

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
        newBtn = new javax.swing.JButton();
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

        newBtn.setAction(newAction);
        newBtn.setText("");
        mainToolBar.add(newBtn);

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

        openMnu.setAction(openAction);
        fileMnu.add(openMnu);
        fileMnu.add(jSeparator1);

        closeMnu.setAction(closeAction);
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

    ////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////
    @Deprecated
    public TransactionFormTab getTransactionFormTab() {
        return transactionFormTab;
    }
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

    @Deprecated
    public abstract class PaccmanAction extends AbstractAction {

        public PaccmanAction(String name, Icon icon, boolean enabled) {
            super(name, icon);
            setEnabled(enabled);
        }

        public PaccmanAction(String name, Icon icon) {
            super(name, icon);
        }

    }

    @Deprecated
    public final class PropertiesAction extends PaccmanAction {

        public PropertiesAction() {
            super("Properties", new javax.swing.ImageIcon(PropertiesAction.class.getResource("/org/paccman/ui/resources/images/properties.png")), false);
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            PropertiesFrame pf = new PropertiesFrame();
            getDocumentController().registerView(pf);
            pf.onChange(getDocumentController());
            pf.setVisible(true);
        }

    }
    
    @Deprecated
   private void showStartDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showStartDialogActionPerformed
        MainPrefs.setShowStartDialog(!MainPrefs.getShowStartDialog());
    }//GEN-LAST:event_showStartDialogActionPerformed

    @Deprecated
    public void gotoAccountTransactionTab(AccountController account) {
        transactionFormTab.setSelectedAccount(account);
        mainTabbedPane.setSelectedComponent(transactionFormTab);
    }

    @Deprecated
    public void gotoWelcomeTab() {
        mainTabbedPane.setSelectedComponent(welcomePane);
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
    public javax.swing.JButton newBtn;
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

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // Tabs
    // -------------------------------------------------------------------------
    WelcomePaneTab welcomePane;
    AccountFormTab accountsPanel;
    BankFormTab bankFormTab;
    CategoryFormTab categoryFormTab;
    TransactionFormTab transactionFormTab;
    PayeeFormTab payeeFormTab;
    PaymentMethodFormTab paymentMethodFormTab;
    ScheduleFormTab scheduleFormTab;

    void showTabbedPanes() {
        assert getDocumentController() != null : "The document controller must exists";

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

    void hideTabbedPanes() {
        mainTabbedPane.removeAll();
    }

    // -------------------------------------------------------------------------
    // Actions 
    // -------------------------------------------------------------------------
    Actions.NewAction newAction = new Actions.NewAction();
    Actions.OpenAction openAction = new Actions.OpenAction();
    Actions.CloseAction closeAction = new Actions.CloseAction();
    Actions.SaveAction saveAction = new Actions.SaveAction();
    Actions.SaveAsAction saveAsAction = new Actions.SaveAsAction();
    Actions.QuitAction quitAction = new Actions.QuitAction();

    // -------------------------------------------------------------------------
    // DocumentController methods
    // -------------------------------------------------------------------------
    public void onChange(org.paccman.controller.Controller controller) {

        // Update enable status of Actions
        closeMnu.getAction().setEnabled(isDocumentEdited());
        saveMnu.getAction().setEnabled(isDocumentEdited() && (getDocumentController().isHasChanged()));
        saveAsMnu.getAction().setEnabled(isDocumentEdited());
        propertiesMnu.getAction().setEnabled(isDocumentEdited());

        // Updates title
        setTitle(getTitleString());
    }

    private static final String NO_DOCUMENT_TITLE = "[No document]";

    private String getTitleString() {
        if (getDocumentController() == null) {
            return NO_DOCUMENT_TITLE;
        } else {
            if (getDocumentController().isHasChanged()) {
                return getDocumentController().getDocument().getTitle() + "*";
            } else {
                return getDocumentController().getDocument().getTitle();
            }
        }
    }

    // -------------------------------------------------------------------------
    // Logging
    // -------------------------------------------------------------------------
    Logger logger = org.paccman.tools.Logger.getDefaultLogger(Main.class);


    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    private void initMyComponents() {
        // Look and feel menu
        optionMnu.addSeparator();
        optionMnu.add(new LookAndFeelMenu(this));

        calculatorFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    StartOption startOption = new StartOption();
    
    /**
     * Constructor for <code>Main<code> 
     */
    public Main() {
        initComponents();
        initMyComponents();
    }
    
    /**
     * Constructor for <code>Main<code> which takes the arguments passed in the command 
     * line.
     * @param args The arguments passed in the command line.
     */
    public Main(String[] args) {
        this();
        startOption.parse(args);
    }

    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO quit();
    }//GEN-LAST:event_formWindowClosing

     private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         String fileToOpen = null;

         // If file passed in the comand line, this is the fiole to open
         if (startOption.getFilename() != null) {
             fileToOpen = startOption.getFilename();
         }

         if (fileToOpen != null) {
             Actions.doOpenFile(new File(fileToOpen));
         }
    }//GEN-LAST:event_formWindowOpened

     /**
     * <em>The</em> Main
     * @return Main.
     */
    public static Main getMain() {
        return main;
    }

    static Main main;

    /**
     * Main entry point for PAccMan.
     * @param args Options and arguments for PAccMan. See StartOption#parse(args).
     */
    public static void main(String[] args) {
        final String[] lArgs = args;
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                main = new Main(lArgs);
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

}
