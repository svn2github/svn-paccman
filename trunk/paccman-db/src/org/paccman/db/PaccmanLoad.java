/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.paccman.controller.AccountController;
import org.paccman.controller.BankController;
import org.paccman.controller.CategoryController;
import org.paccman.controller.DocumentController;
import org.paccman.controller.PayeeController;
import org.paccman.controller.PaymentMethodController;
import org.paccman.controller.SimplePaymentController;
import org.paccman.controller.SplitPaymentController;
import org.paccman.controller.SubCategoryController;
import org.paccman.controller.TransferController;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.paccman.Category;
import org.paccman.paccman.Document;
import org.paccman.paccman.Payee;
import org.paccman.paccman.PaymentMethod;
import org.paccman.paccman.SimplePayment;
import org.paccman.paccman.SplitPayment;
import org.paccman.paccman.SubCategory;
import org.paccman.paccman.TransactionBase;
import org.paccman.paccman.Transfer;
import static org.paccman.db.PaccmanDbUtils.*;

/**
 *
 * @author joao
 */
public class PaccmanLoad {

    private Connection connection;
    private Document doc;
    private Logger logger = org.paccman.tools.Logger.getDefaultLogger(PaccmanLoad.class);

    /**
     * :TODO:
     * @param connection 
     * @param ctrl
     * @throws java.sql.SQLException 
     */
    public void loadDocument(Connection connection, DocumentController ctrl) throws SQLException {
        logger.finest("Start loading");


        this.doc = ctrl.getDocument();
        this.connection = connection;

        // Doc info
        loadDocInfo(ctrl);

        loadBanks();

        loadCategories();

        loadPayees();

        loadPaymentMethods();

        loadAccounts();

        // load transaction after accounts (transfer transactions need the
        // destination account to be created)
        loadTransactions();

        // load scheduled transaction after accounts (transfer transactions need the
        // destination account to be created)
        loadScheduledTransactions();

        // update all balances after all transaction have been loaded
        // (including transfers from other accounts !!!)
        updateAllBalances();

    }

    private final static String SEL_BANKS_SQL =
            "SELECT BANK_ID, NAME, AGENCY, ADDRESS_NUMBER, ADDRESS_LINE1, ADDRESS_LINE2, " +
            "ADDRESS_CITY, ADDRESS_ZIP, ADDRESS_COUNTRY, TELEPHONE, FAX, INTERNET, EMAIL " +
            "FROM OBJ_BANKS";

    private void loadBanks() throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery(SEL_BANKS_SQL);
        while (rs.next()) {
            BankController bankCtrl = new BankController();
            Bank bank = bankCtrl.getBank();

            bank.setBankId(rs.getLong("BANK_ID"));
            bank.setName(rs.getString("NAME"));
            bank.setAgency(rs.getString("AGENCY"));
            bank.setAddressNumber(rs.getString("ADDRESS_NUMBER"));
            bank.setAddressLine1(rs.getString("ADDRESS_LINE1"));
            bank.setAddressLine2(rs.getString("ADDRESS_LINE2"));
            bank.setAddressCity(rs.getString("ADDRESS_CITY"));
            bank.setAddressZip(rs.getString("ADDRESS_ZIP"));
            bank.setAddressCountry(rs.getString("ADDRESS_COUNTRY"));
            bank.setTelephone(rs.getString("TELEPHONE"));
            bank.setFax(rs.getString("FAX"));
            bank.setInternet(rs.getString("INTERNET"));
            bank.setEmail(rs.getString("EMAIL"));

            doc.addBank(bank);

            logger.finest("Loaded Bank: " + bank.getName());
        }
    }

    private final static String SEL_CATEGORIES_SQL =
            "SELECT CATEGORY_ID, NAME, DESCRIPTION, IS_INCOME " +
            "FROM OBJ_CATEGORIES WHERE PARENT_CATEGORY_ID IS NOT NULL";

    private void loadCategories() throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery(SEL_CATEGORIES_SQL);
        while (rs.next()) {
            CategoryController categoryCtrl = new CategoryController();
            Category category = categoryCtrl.getCategory();

            category.setCategoryId(rs.getLong("CATEGORY_ID"));
            category.setName(rs.getString("NAME"));
            category.setDescription(rs.getString("DESCRIPTION"));
            category.setIncome(rs.getBoolean("IS_INCOME"));

            loadSubCategories(category);

            doc.addCategory(category);

            logger.finest("Loaded Category: " + category.getName());
        }
    }

    private final static String SEL_SUBCATEGORIES_SQL =
            "SELECT CATEGORY_ID, NAME, DESCRIPTION " +
            "FROM OBJ_CATEGORIES WHERE PARENT_CATEGORY_ID = ?";

    private void loadSubCategories(Category category) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_SUBCATEGORIES_SQL);
        stat.setLong(1, category.getCategoryId());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            SubCategoryController subCatCtrl = new SubCategoryController(category);
            SubCategory subCategory = subCatCtrl.getSubCategory();

            subCategory.setCategoryId(rs.getLong("CATEGORY_ID"));
            subCategory.setName(rs.getString("NAME"));
            subCategory.setDescription(rs.getString("DESCRIPTION"));

            category.addSubCategory(subCategory);

            logger.finest("Loaded SubCategory: " + subCategory.getName());
        }
    }

    private final static String SEL_PAYEES_SQL =
            "SELECT PAYEE_ID, NAME " +
            "FROM OBJ_PAYEES";

    private void loadPayees() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_PAYEES_SQL);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            PayeeController payeeCtrl = new PayeeController();
            Payee payee = payeeCtrl.getPayee();
            payee.setPayeeId(rs.getByte("PAYEE_ID"));
            payee.setName(rs.getString("NAME"));

            doc.addPayee(payee);
        }
    }

    private final static String SEL_PAYMENTMETHODS_SQL =
            "SELECT PAYMENT_METHOD_ID, NAME " +
            "FROM OBJ_PAYMENT_METHODS";

    private void loadPaymentMethods() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_PAYMENTMETHODS_SQL);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            PaymentMethodController paymentCtrl = new PaymentMethodController();
            PaymentMethod paymentMethod = paymentCtrl.getPaymentMethod();
            paymentMethod.setPaymentMethodId(rs.getByte("PAYMENT_METHOD_ID"));
            paymentMethod.setName(rs.getString("NAME"));

            doc.addPaymentMethod(paymentMethod);
        }
    }

    private final static String SEL_ACCOUNTS_SQL =
            "SELECT " +
            "ACCOUNT_ID, OBJ_ACCOUNTS.NAME as NAME, OBJ_BANKS.NAME as BANK_NAME," +
            "HOLDER_NAME,HOLDER_ADDRESS," +
            "INITIAL_BALANCE,ACCOUNT_NUMBER,ACCOUNT_NUMBER_KEY,NOTE," +
            "LAST_RECONCILIATION_DATE,LAST_RECONCILIATION_BALANCE," +
            "PENDING_RECONCILIATION,PENDING_RECONCILIATION_DATE,PENDING_RECONCILIATION_BALANCE " +
            "FROM OBJ_ACCOUNTS " +
            "INNER JOIN OBJ_BANKS ON OBJ_ACCOUNTS.BANK_ID = OBJ_BANKS.BANK_ID";

    private void loadAccounts() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_ACCOUNTS_SQL);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            Account account = new AccountController().getAccount();

            // Actual account data
            account.setAccountId(rs.getLong("ACCOUNT_ID"));
            account.setBank(doc.getBank(rs.getString("BANK_NAME")));
            account.setName(rs.getString("NAME"));
            account.setInitialBalance(rs.getBigDecimal("INITIAL_BALANCE"));
            account.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
            account.setAccountNumberKey(rs.getString("ACCOUNT_NUMBER_KEY"));
            account.setNote(rs.getString("NOTE"));

            // Holder data
            account.setHolderName(rs.getString("HOLDER_NAME"));
            account.setHolderAddress(rs.getString("HOLDER_ADDRESS"));

            // Reconciliation data
            account.setLastReconciliationBalance(rs.getBigDecimal("LAST_RECONCILIATION_BALANCE"));
            account.setLastReconciliationDate(sqlDateToCalendar(rs.getTimestamp("LAST_RECONCILIATION_BALANCE")));
            account.setPendingReconciliation(rs.getBoolean("PENDING_RECONCILIATION"));
            if (account.isPendingReconciliation()) {
                account.setPendingReconciliationBalance(rs.getBigDecimal("PENDING_RECONCILIATION_BALANCE"));
                account.setPendingReconciliationDate(sqlDateToCalendar(rs.getTimestamp("PENDING_RECONCILIATION_DATE")));
            } else {
                account.setPendingReconciliationBalance(null);
                account.setPendingReconciliationDate(null);
            }

            doc.addAccount(account);

            logger.finest("Loaded Account: " + account.getName());
        }
    }

    private void loadScheduledTransactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final String SEL_TRANSFERS_SQL =
            "SELECT TRANSACTION_ID,OBJ_ACCOUNTS.NAME AS ACCOUNT_NAME FROM OBJ_TRANSFERS " +
            "INNER JOIN OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSFERS.ACCOUNT_ID";
    private final String SEL_SIMPLEPAYMENTS_SQL =
            ":TODO:";
    private final String SEL_SPLITPAYMENTS_SQL =
            ":TODO:";

    private void loadTransaction(TransactionBase transaction, long transactionId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadTransactions() throws SQLException {
        for (Account account : doc.getAccounts()) {
            logger.finest("Loading transactions for account : " + account.getName());

            // -- Transfers
            {
                PreparedStatement stat = connection.prepareStatement(SEL_TRANSFERS_SQL);
                ResultSet rs = stat.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    Transfer transfer = new TransferController().getTransfer();

                    transfer.setToFromAccount(doc.getAccount(rs.getString("ACCOUNT_NAME")));
                    long transactionId = rs.getLong("TRANSACTION_ID");

                    loadTransaction(transfer, transactionId);

                    account.addTransfer(transfer, false);
                }
            }

            // -- Simple Payments
            {
                PreparedStatement stat = connection.prepareStatement(SEL_SIMPLEPAYMENTS_SQL);
                ResultSet rs = stat.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    SimplePayment simplePayment = new SimplePaymentController().getPayment();

                    long transactionId = rs.getLong("TRANSACTION_ID");
                    //:TODO:
                    loadTransaction(simplePayment, transactionId);

                    account.addSimplePayment(simplePayment, false);
                }
            }

            // -- Split payments
            {
                PreparedStatement stat = connection.prepareStatement(SEL_SPLITPAYMENTS_SQL);
                ResultSet rs = stat.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    SplitPayment splitPayment = new SplitPaymentController().getPayment();

                    long transactionId = rs.getLong("TRANSACTION_ID");

                    //:TODO:
                    loadTransaction(splitPayment, transactionId);

                    account.addSplitPayment(splitPayment, false);
                }
            }

        }
    }

    private void loadDocInfo(DocumentController ctrl) throws SQLException {
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("select NAME, VALUE from DOCINFO");

        while (rs.next()) {
            final String name = rs.getString("NAME");
            final String value = rs.getString("VALUE");
            if (name.equals(DOCVERSION_KEY)) {
                ctrl.setLoadVersion(value);
            } else if (name.equals(DOCUMENT_TITLE_KEY)) {
                doc.setTitle(value);
            } else if (name.equals(CREATEDOC_DATE_KEY)) {
                try {
                    Calendar c = new GregorianCalendar();
                    c.setTime(new SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(value));
                    doc.setCreationDate(c);
                } catch (ParseException ex) {
                    //:TODO:
                    logger.log(Level.SEVERE, null, ex);
                }
            } else if (name.equals(UPDATEDOC_DATE_KEY)) {
                try {
                    Calendar c = new GregorianCalendar();
                    c.setTime(new SimpleDateFormat(ISO_8601_DATE_FORMAT).parse(value));
                    doc.setLastUpdateDate(c);
                } catch (ParseException ex) {
                    //:TODO:
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void updateAllBalances() {
        for (Account account : doc.getAccounts()) {
            logger.finest("Updating balances for account: " + account.getName());
            account.updateBalances();
        }
    }

}
