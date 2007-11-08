/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import org.paccman.controller.DocumentController;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.paccman.Category;
import org.paccman.paccman.Document;
import org.paccman.paccman.Payee;
import org.paccman.paccman.Payment;
import org.paccman.paccman.PaymentMethod;
import org.paccman.paccman.ScheduledTransaction;
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
public class PaccmanSave {

    private Connection connection;
    private Document doc;
    private Logger log = org.paccman.tools.Logger.getDefaultLogger(PaccmanSave.class);

    /**
     * 
     * @param connection 
     * @param ctrl
     * @throws java.sql.SQLException 
     */
    public void saveDocument(Connection connection, DocumentController ctrl) throws SQLException {
        log.finest("Start loading");

        this.connection = connection;
        this.doc = ctrl.getDocument();

        saveDocInfo();

        saveBanks();

        saveCategories();

        savePayees();

        savePaymentTypes();

        saveAccounts();

        saveTransactions();

        saveSchedTransactions();

    }
    private final static String SAVE_CATEGORIES_SQL = "INSERT INTO OBJ_CATEGORIES(" +
            "NAME, " +
            "DESCRIPTION," +
            "IS_INCOME," +
            "PARENT_CATEGORY_ID)" +
            "VALUES(?,?,?,?)";

    private void saveCategories() throws SQLException {
        PreparedStatement statCat = connection.prepareStatement(SAVE_CATEGORIES_SQL, Statement.RETURN_GENERATED_KEYS);

        for (Category category : doc.getCategories()) {
            statCat.setString(1, category.getName());
            statCat.setString(2, category.getDescription());
            statCat.setBoolean(3, category.isIncome());
            statCat.setNull(4, Types.BIGINT);
            statCat.executeUpdate();

            long categoryId = getIdentity(statCat);
            category.setCategoryId(categoryId);

            for (SubCategory sc : category.getSubCategories()) {
                statCat.setString(1, sc.getName());
                statCat.setString(2, sc.getDescription());
                statCat.setNull(3, Types.SMALLINT);
                statCat.setLong(4, categoryId);
                statCat.executeUpdate();
                sc.setCategoryId(getIdentity(statCat));
            }
        }
    }

    private void saveDocInfo() throws SQLException {

        PreparedStatement stat = connection.prepareStatement("INSERT INTO DOCINFO(NAME, VALUE) VALUES(?,?)");

        stat.setString(1, DOCVERSION_KEY);
        stat.setString(2, CURRENT_VERSION);
        stat.executeUpdate();

        stat.setString(1, DOCUMENT_TITLE_KEY);
        stat.setString(2, doc.getTitle());
        stat.executeUpdate();

        stat.setString(1, CREATEDOC_DATE_KEY);
        DateFormat df = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
        stat.setString(2, df.format(doc.getCreationDate().getTime()));
        stat.executeUpdate();

        stat.setString(1, UPDATEDOC_DATE_KEY);
        stat.setString(2, df.format(doc.getLastUpdateDate().getTime()));
        stat.executeUpdate();

    }
    static private final String SAVE_BANKS_SQL =
            "INSERT INTO OBJ_BANKS(" +
            "NAME," +
            "AGENCY," +
            "ADDRESS_NUMBER," +
            "ADDRESS_LINE1," +
            "ADDRESS_LINE2," +
            "ADDRESS_CITY," +
            "ADDRESS_ZIP," +
            "ADDRESS_COUNTRY," +
            "TELEPHONE," +
            "FAX," +
            "INTERNET," +
            "EMAIL) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private void saveBanks() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_BANKS_SQL, Statement.RETURN_GENERATED_KEYS);

        for (Bank bank : doc.getBanks()) {
            assert (bank.getName() != null) && (bank.getName().length() > 0);

            int index = 1;
            stat.setString(index++, bank.getName());
            stat.setString(index++, bank.getAgency());
            stat.setString(index++, bank.getAddressNumber());
            stat.setString(index++, bank.getAddressLine1());
            stat.setString(index++, bank.getAddressLine2());
            stat.setString(index++, bank.getAddressCity());
            stat.setString(index++, bank.getAddressZip());
            stat.setString(index++, bank.getAddressCountry());
            stat.setString(index++, bank.getTelephone());
            stat.setString(index++, bank.getFax());
            stat.setString(index++, bank.getInternet());
            stat.setString(index++, bank.getEmail());

            stat.executeUpdate();

            bank.setBankId(getIdentity(stat));
        }
    }
    static private final String SAVE_PAYEES_SQL =
            "INSERT INTO OBJ_PAYEES(" +
            "NAME)" +
            "VALUES(?)";

    private void savePayees() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_PAYEES_SQL, Statement.RETURN_GENERATED_KEYS);

        for (Payee payee : doc.getPayees()) {
            stat.setString(1, payee.getName());

            stat.executeUpdate();

            payee.setPayeeId(getIdentity(stat));
        }
    }
    static private final String SAVE_PAYMENTTYPES_SQL =
            "INSERT INTO OBJ_PAYMENT_METHODS(" +
            "NAME)" +
            "VALUES(?)";

    private void savePaymentTypes() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_PAYMENTTYPES_SQL, Statement.RETURN_GENERATED_KEYS);

        for (PaymentMethod paymentMethod : doc.getPaymentMethods()) {
            stat.setString(1, paymentMethod.getName());

            stat.executeUpdate();

            paymentMethod.setPaymentMethodId(getIdentity(stat));
        }
    }
    static private final String SAVE_ACCOUNTS_SQL =
            "INSERT INTO OBJ_ACCOUNTS(" +
            "NAME," +
            "BANK_ID," +
            "INITIAL_BALANCE," +
            "ACCOUNT_NUMBER," +
            "ACCOUNT_NUMBER_KEY," +
            "NOTE," +
            "HOLDER_NAME," +
            "HOLDER_ADDRESS," +
            "LAST_RECONCILIATION_DATE," +
            "LAST_RECONCILIATION_BALANCE," +
            "PENDING_RECONCILIATION," +
            "PENDING_RECONCILIATION_DATE," +
            "PENDING_RECONCILIATION_BALANCE)" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private void saveAccounts() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_ACCOUNTS_SQL, Statement.RETURN_GENERATED_KEYS);

        for (Account account : doc.getAccounts()) {
            stat.setString(1, account.getName());
            stat.setLong(2, account.getBank().getBankId());
            stat.setBigDecimal(3, account.getInitialBalance());
            stat.setString(4, account.getAccountNumber());
            stat.setString(5, account.getAccountNumberKey());
            stat.setString(6, account.getNote());
            stat.setString(7, account.getHolderName());
            stat.setString(8, account.getHolderAddress());
            stat.setTimestamp(9, calendarToSqlDate(account.getLastReconciliationDate()));
            stat.setBigDecimal(10, account.getLastReconciliationBalance());
            stat.setBoolean(11, account.isPendingReconciliation());
            stat.setTimestamp(12, calendarToSqlDate(account.getLastReconciliationDate()));
            stat.setBigDecimal(13, account.getPendingReconciliationBalance());
            stat.executeUpdate();

            account.setAccountId(getIdentity(stat));
        }
    }
    static private final String SAVE_TRANSACTIONS_SQL =
            "INSERT INTO OBJ_TRANSACTIONS(" +
            "AMOUNT," +
            "VALUE_DATE," +
            "TRANSACTION_DATE," +
            "NOTE," +
            "LABEL," +
            "RECONCILIATION_STATE," +
            "RECONCILIATION_DATE," +
            "ACCOUNT_ID," +
            "IS_TEMPLATE)" +
            "VALUES(?,?,?,?,?,?,?,?,?)";

    private void saveTransaction(Account account, TransactionBase t, boolean isScheduled) throws SQLException, SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_TRANSACTIONS_SQL, Statement.RETURN_GENERATED_KEYS);

        stat.setBigDecimal(1, t.getAmount());
        stat.setTimestamp(2, calendarToSqlDate(t.getValueDate()));
        stat.setTimestamp(3, calendarToSqlDate(t.getTransactionDate()));
        stat.setString(4, t.getNote());
        stat.setString(5, t.getLabel());
        stat.setString(6, reconcileToDbReconcile(t.getReconciliationState()));
        stat.setTimestamp(7, calendarToSqlDate(t.getReconciliationDate()));
        stat.setLong(8, account.getAccountId());
        stat.setBoolean(9, isScheduled);

        stat.executeUpdate();

        long transactionBaseId = getIdentity(stat);
        t.setTransactionId(transactionBaseId);

        if (t instanceof Transfer) {
            saveTransfer((Transfer) t, transactionBaseId);
        } else if (t instanceof Payment) {
            savePayment((Payment) t, transactionBaseId);
        }
    }

    private void saveTransactions() throws SQLException {
        for (Account account : doc.getAccounts()) {
            for (TransactionBase t : account.getTransactions()) {
                saveTransaction(account, t, false);
            }
        }
    }
    static private final String SAVE_TRANSFER_SQL =
            "INSERT INTO OBJ_TRANSFERS(" +
            "TRANSACTION_ID," +
            "ACCOUNT_ID)" +
            "VALUES(?,?)";

    private void saveTransfer(Transfer transfer, long transactionBaseId) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_TRANSFER_SQL, Statement.RETURN_GENERATED_KEYS);
        stat.setLong(1, transactionBaseId);
        stat.setLong(2, transfer.getToFromAccount().getAccountId());

        stat.executeUpdate();
    }
    static private final String SAVE_PAYMENT_SQL =
            "INSERT INTO OBJ_PAYMENTS(" +
            "TRANSACTION_ID," +
            "PAYEE_ID," +
            "PAYMENT_METHOD_ID)" +
            "VALUES(?,?,?)";

    private void savePayment(Payment payment, long transactionBaseId) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_PAYMENT_SQL, Statement.RETURN_GENERATED_KEYS);
        stat.setLong(1, transactionBaseId);
        if (payment.getPayee() != null) {
            stat.setLong(2, payment.getPayee().getPayeeId());
        } else {
            stat.setNull(2, Types.BIGINT);
        }
        if (payment.getPaymentMethod() != null) {
            stat.setLong(3, payment.getPaymentMethod().getPaymentMethodId());
        } else {
            stat.setNull(3, Types.BIGINT);
        }
        stat.executeUpdate();
        if (payment instanceof SimplePayment) {
            saveSimplePayment((SimplePayment) payment, transactionBaseId);
        } else if (payment instanceof SplitPayment) {
            saveSplitPayment((SplitPayment) payment, transactionBaseId);
        }
    }
    static private final String SAVE_SIMPLE_PAYMENT_SQL =
            "INSERT INTO OBJ_SIMPLE_PAYMENTS(" +
            "TRANSACTION_ID," +
            "CATEGORY_ID)" +
            "VALUES(?,?)";

    private void saveSimplePayment(SimplePayment simplePayment, long transactionBaseId) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_SIMPLE_PAYMENT_SQL, Statement.RETURN_GENERATED_KEYS);
        stat.setLong(1, transactionBaseId);
        stat.setLong(2, simplePayment.getCategory().getCategoryId());

        stat.executeUpdate();
    }
    static private final String SAVE_SPLIT_PAYMENT_SQL =
            "INSERT INTO OBJ_SPLIT_PAYMENTS(" +
            "TRANSACTION_ID)" +
            "VALUES(?)";

    private void saveSplitPayment(SplitPayment splitPayment, long transactionBaseId) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_SPLIT_PAYMENT_SQL, Statement.RETURN_GENERATED_KEYS);
        stat.setLong(1, transactionBaseId);

        stat.executeUpdate();

        for (SplitTransaction t : splitPayment.getSplitTransactions()) {
            saveSplitTransaction(t, transactionBaseId);
        }
    }
    static private final String SAVE_SPLIT_TRANSACTION_SQL =
            "INSERT INTO OBJ_SPLIT_TRANSACTIONS(" +
            "TRANSACTION_ID," +
            "AMOUNT," +
            "CATEGORY_ID," +
            "NOTE)" +
            "VALUES(?,?,?,?)";

    private void saveSplitTransaction(SplitTransaction t, long transactionBaseId) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_SPLIT_TRANSACTION_SQL, Statement.RETURN_GENERATED_KEYS);
        stat.setLong(1, transactionBaseId);
        stat.setBigDecimal(2, t.getAmount());
        stat.setLong(3, t.getCategory().getCategoryId());
        stat.setString(4, t.getNote());
        stat.executeUpdate();
    }
    static private final String SAVE_SCHED_TRANSACTION_SQL =
            "INSERT INTO OBJ_SCHED_TRANSACTIONS(" +
            "ACCOUNT_ID, " +
            "AUTOMATIC ," +
            "DESCRIPTION," +
            "FIXED_AMOUNT," +
            "NEXT_OCCURENCE," +
            "PERIOD," +
            "PERIOD_UNIT," +
            "SCHED_DAYS," +
            "TRANSACTION_ID" +
            ")" +
            "VALUES(?,?,?,?,?,?,?,?,?)";

    private void saveSchedTransactions() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SAVE_SCHED_TRANSACTION_SQL, Statement.RETURN_GENERATED_KEYS);
        for (Account account : doc.getAccounts()) {
            for (ScheduledTransaction t : account.getScheduledTransactions()) {
                saveTransaction(account, t.getTransactionBase(), true);

                stat.setLong(1, account.getAccountId());
                stat.setBoolean(2, t.isAutomatic());
                stat.setString(3, t.getDescription());
                stat.setBoolean(4, t.isFixedAmount());
                stat.setTimestamp(5, calendarToSqlDate(t.getNextOccurence()));
                stat.setInt(6, t.getPeriod());
                stat.setString(7, periodUnitToDbPeriodUnit(t.getPeriodUnit()));
                stat.setInt(8, t.getScheduleDays());
                stat.setLong(9, t.getTransactionBase().getTransactionId());

                stat.executeUpdate();

            }
        }
    }
}

