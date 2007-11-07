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
import org.paccman.paccman.CategoryBase;
import org.paccman.paccman.Document;
import org.paccman.paccman.Payee;
import org.paccman.paccman.PaymentMethod;
import org.paccman.paccman.SimplePayment;
import org.paccman.paccman.SplitPayment;
import org.paccman.paccman.SplitTransaction;
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
            "FROM OBJ_CATEGORIES WHERE PARENT_CATEGORY_ID IS NULL";

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
            account.setLastReconciliationDate(sqlDateToCalendar(rs.getTimestamp("LAST_RECONCILIATION_DATE")));
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
            "SELECT TO_FROM_ACCOUNTS.NAME AS ACCOUNT_NAME, OBJ_TRANSACTIONS.TRANSACTION_ID FROM OBJ_TRANSFERS " +
            "INNER JOIN OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_TRANSFERS.TRANSACTION_ID " +
            "INNER JOIN OBJ_ACCOUNTS ON OBJ_TRANSACTIONS.ACCOUNT_ID = OBJ_ACCOUNTS.ACCOUNT_ID " +
            "INNER JOIN OBJ_ACCOUNTS AS TO_FROM_ACCOUNTS ON OBJ_TRANSFERS.ACCOUNT_ID = TO_FROM_ACCOUNTS.ACCOUNT_ID " +
            "WHERE (OBJ_ACCOUNTS.ACCOUNT_ID = ?) AND (OBJ_TRANSACTIONS.IS_SCHEDTRANSACTION = ?)";
    private final String SEL_SIMPLEPAYMENTS_SQL =
            "SELECT OBJ_PAYEES.NAME AS PAYEE_NAME, OBJ_PAYMENT_METHODS.NAME AS PAYMENT_METHOD_NAME,  " +
            "OBJ_CATEGORIES.NAME AS CATEGORY_NAME, " +
            "PARENT_CATEGORIES.NAME AS PARENT_CATEGORY_NAME, OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "FROM OBJ_SIMPLE_PAYMENTS " +
            "INNER JOIN OBJ_PAYMENTS ON OBJ_PAYMENTS.TRANSACTION_ID = OBJ_SIMPLE_PAYMENTS.TRANSACTION_ID " +
            "INNER JOIN OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_PAYMENTS.TRANSACTION_ID " +
            "INNER JOIN OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSACTIONS.ACCOUNT_ID " +
            "INNER JOIN OBJ_PAYMENT_METHODS ON OBJ_PAYMENTS.PAYMENT_METHOD_ID = OBJ_PAYMENT_METHODS.PAYMENT_METHOD_ID " +
            "INNER JOIN OBJ_PAYEES ON OBJ_PAYMENTS.PAYEE_ID = OBJ_PAYEES.PAYEE_ID " +
            "INNER JOIN OBJ_CATEGORIES ON OBJ_CATEGORIES.CATEGORY_ID = OBJ_SIMPLE_PAYMENTS.CATEGORY_ID " +
            "LEFT JOIN OBJ_CATEGORIES AS PARENT_CATEGORIES ON OBJ_CATEGORIES.PARENT_CATEGORY_ID = PARENT_CATEGORIES.CATEGORY_ID " +
            "WHERE (OBJ_ACCOUNTS.ACCOUNT_ID = ?) AND (OBJ_TRANSACTIONS.IS_SCHEDTRANSACTION = ?) ";
    private final String SEL_SPLITPAYMENTS_SQL =
            "SELECT OBJ_PAYEES.NAME AS PAYEE_NAME, OBJ_PAYMENT_METHODS.NAME AS PAYMENT_METHOD_NAME," +
            "OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "FROM OBJ_SPLIT_PAYMENTS " +
            "INNER JOIN OBJ_PAYMENTS ON OBJ_PAYMENTS.TRANSACTION_ID = OBJ_SPLIT_PAYMENTS.TRANSACTION_ID  " +
            "INNER JOIN OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_PAYMENTS.TRANSACTION_ID  " +
            "INNER JOIN OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSACTIONS.ACCOUNT_ID  " +
            "INNER JOIN OBJ_PAYMENT_METHODS ON OBJ_PAYMENTS.PAYMENT_METHOD_ID = OBJ_PAYMENT_METHODS.PAYMENT_METHOD_ID  " +
            "INNER JOIN OBJ_PAYEES ON OBJ_PAYMENTS.PAYEE_ID = OBJ_PAYEES.PAYEE_ID " +
            "WHERE (OBJ_ACCOUNTS.ACCOUNT_ID = ?) AND (OBJ_TRANSACTIONS.IS_SCHEDTRANSACTION = ?)";

    CategoryBase getCategory(String name, String parentName) {
        if (parentName == null) {
            return doc.getCategory(name);
        } else {
            return doc.getCategory(parentName).getSubCategory(name);
        }
    }

    private void loadTransactions() throws SQLException {
        PreparedStatement statTransfer = connection.prepareStatement(SEL_TRANSFERS_SQL);
        PreparedStatement statSimplePayment = connection.prepareStatement(SEL_SIMPLEPAYMENTS_SQL);
        PreparedStatement statSplitPayment = connection.prepareStatement(SEL_SPLITPAYMENTS_SQL);
        PreparedStatement statTransactionBase = connection.prepareStatement(SEL_TRANSACTIONBASE_SQL);

        for (Account account : doc.getAccounts()) {
            logger.finest("Loading transactions for account : " + account.getName());

            // -- Transfers
            {
                statTransfer.setLong(1, account.getAccountId());
                statTransfer.setBoolean(2, false);
                ResultSet rs = statTransfer.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    Transfer transfer = new TransferController().getTransfer();

                    transfer.setToFromAccount(doc.getAccount(rs.getString("ACCOUNT_NAME")));
                    long transactionId = rs.getLong("TRANSACTION_ID");

                    loadTransactionBase(statTransactionBase, transfer, transactionId, account.getAccountId(), false);

                    account.addTransfer(transfer, false);
                }
            }

            // -- Simple Payments
            {
                statSimplePayment.setLong(1, account.getAccountId());
                statSimplePayment.setBoolean(2, false);
                ResultSet rs = statSimplePayment.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    SimplePayment simplePayment = new SimplePaymentController().getPayment();

                    long transactionId = rs.getLong("TRANSACTION_ID");
                    simplePayment.setPayee(doc.getPayee(rs.getString("PAYEE_NAME")));
                    simplePayment.setPaymentMethod(doc.getPaymentType(rs.getString("PAYMENT_METHOD_NAME")));
                    simplePayment.setCategory(getCategory(rs.getString("CATEGORY_NAME"), rs.getString("PARENT_CATEGORY_NAME")));
                    loadTransactionBase(statTransactionBase, simplePayment, transactionId, account.getAccountId(), false);

                    account.addSimplePayment(simplePayment, false);
                }
            }

            // -- Split payments
            {
                statSplitPayment.setLong(1, account.getAccountId());
                statSplitPayment.setBoolean(2, false);
                ResultSet rs = statSplitPayment.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    SplitPayment splitPayment = new SplitPaymentController().getPayment();

                    long transactionId = rs.getLong("TRANSACTION_ID");
                    splitPayment.setPayee(doc.getPayee(rs.getString("PAYEE_NAME")));
                    splitPayment.setPaymentMethod(doc.getPaymentType(rs.getString("PAYMENT_METHOD_NAME")));
                    loadSplitTransactions(splitPayment);
                    loadTransactionBase(statTransactionBase, splitPayment, transactionId, account.getAccountId(), false);

                    account.addSplitPayment(splitPayment, false);
                }
            }

        }
    }

    private final String SEL_SPLIT_TRANSACTIONS_SQL =
            "select AMOUNT, OBJ_CATEGORIES.NAME as CAT_NAME, PARENT_CATEGORIES.NAME as PARENT_CAT_NAME, NOTE " +
            "from OBJ_SPLIT_TRANSACTIONS " +
            "inner join OBJ_CATEGORIES on OBJ_CATEGORIES.CATEGORY_ID = OBJ_SPLIT_TRANSACTIONS.CATEGORY_ID " +
            "inner join OBJ_CATEGORIES AS PARENT_CATEGORIES ON OBJ_CATEGORIES.PARENT_CATEGORY_ID = PARENT_CATEGORIES.CATEGORY_ID " +
            "where OBJ_SPLIT_TRANSACTIONS.TRANSACTION_ID = ?";

    private void loadSplitTransactions(SplitPayment payment) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_SPLIT_TRANSACTIONS_SQL);
        stat.setLong(1, payment.getTransactionId());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            SplitTransaction st = new SplitTransaction();
            st.setAmount(rs.getBigDecimal("AMOUNT"));
            st.setCategory(getCategory("CAT_NAME", "PARENT_CAT_NAME"));
            st.setNote(rs.getString("NOTE"));
            
            payment.addSplitTransaction(st);
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

    private final String SEL_TRANSACTIONBASE_SQL =
            "SELECT AMOUNT, VALUE_DATE,TRANSACTION_DATE, NOTE, LABEL," +
            "RECONCILIATION_STATE, RECONCILIATION_DATE " +
            "FROM OBJ_TRANSACTIONS " +
            "WHERE (TRANSACTION_ID = ?) AND " +
            "      (ACCOUNT_ID = ?)";

    private void loadTransactionBase(PreparedStatement statTransactionBase, TransactionBase transaction, long transactionId, long accountId, boolean isScheduled) throws SQLException {
        statTransactionBase.setLong(1, transactionId);
        statTransactionBase.setLong(2, accountId);
        ResultSet rs = statTransactionBase.executeQuery();
        rs.next();
        transaction.setAmount(rs.getBigDecimal("AMOUNT"));
        transaction.setValueDate(sqlDateToCalendar(rs.getTimestamp("VALUE_DATE")));
        transaction.setTransactionDate(sqlDateToCalendar(rs.getTimestamp("TRANSACTION_DATE")));
        transaction.setNote(rs.getString("NOTE"));
        transaction.setLabel(rs.getString("LABEL"));
        transaction.setReconciliationState(dbReconcileToReconcile(rs.getString("RECONCILIATION_STATE")));
        transaction.setReconciliationDate(sqlDateToCalendar(rs.getTimestamp("RECONCILIATION_DATE")));
    }

}
