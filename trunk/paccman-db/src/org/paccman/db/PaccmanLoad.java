/*
 
    Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
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
import org.paccman.controller.ScheduledTransactionController;
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
            "select BANK_ID, NAME, AGENCY, ADDRESS_NUMBER, ADDRESS_LINE1, ADDRESS_LINE2, " +
            "ADDRESS_CITY, ADDRESS_ZIP, ADDRESS_COUNTRY, TELEPHONE, FAX, INTERNET, EMAIL " +
            "from OBJ_BANKS";

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
            "select CATEGORY_ID, NAME, DESCRIPTION, IS_INCOME " +
            "from OBJ_CATEGORIES where PARENT_CATEGORY_ID IS NULL";

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
            "select CATEGORY_ID, NAME, DESCRIPTION " +
            "from OBJ_CATEGORIES where PARENT_CATEGORY_ID = ?";

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
            "select PAYEE_ID, NAME " +
            "from OBJ_PAYEES";

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
            "select PAYMENT_METHOD_ID, NAME " +
            "from OBJ_PAYMENT_METHODS";

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
            "select " +
            "ACCOUNT_ID, OBJ_ACCOUNTS.NAME as NAME, OBJ_BANKS.NAME as BANK_NAME," +
            "HOLDER_NAME,HOLDER_ADDRESS," +
            "INITIAL_BALANCE,ACCOUNT_NUMBER,ACCOUNT_NUMBER_KEY,NOTE," +
            "LAST_RECONCILIATION_DATE,LAST_RECONCILIATION_BALANCE," +
            "PENDING_RECONCILIATION,PENDING_RECONCILIATION_DATE,PENDING_RECONCILIATION_BALANCE " +
            "from OBJ_ACCOUNTS " +
            "inner join OBJ_BANKS ON OBJ_ACCOUNTS.BANK_ID = OBJ_BANKS.BANK_ID";

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

    private final static String SEL_SCHEDTRANSACTIONS_SQL =
            "select SCHED_TRANSACTION_ID, TRANSACTION_ID, " +
            "AUTOMATIC, DESCRIPTION, FIXED_AMOUNT, NEXT_OCCURENCE, PERIOD, PERIOD_UNIT, SCHED_DAYS " +
            "from OBJ_SCHED_TRANSACTIONS " +
            "inner join OBJ_ACCOUNTS on OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_SCHED_TRANSACTIONS.ACCOUNT_ID " +
            "where OBJ_ACCOUNTS.ACCOUNT_ID = ?";

    private void loadScheduledTransactions() throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_SCHEDTRANSACTIONS_SQL);

        PreparedStatement statTransfer = connection.prepareStatement(SEL_TRANSFERS_DATA_SQL);
        PreparedStatement statSimple = connection.prepareStatement(SEL_SIMPLE_DATA_SQL);
        PreparedStatement statSplit = connection.prepareStatement(SEL_SPLIT_DATA_SQL);

        for (Account account : doc.getAccounts()) {
            logger.finest("Loading transactions for account : " + account.getName());

            stat.setLong(1, account.getAccountId());
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                ScheduledTransaction st = new ScheduledTransactionController().getScheduledTransaction();
                st.setAutomatic(rs.getBoolean("AUTOMATIC"));
                st.setDescription(rs.getString("DESCRIPTION"));
                st.setFixedAmount(rs.getBoolean("FIXED_AMOUNT"));
                st.setNextOccurence(sqlDateToCalendar(rs.getTimestamp("NEXT_OCCURENCE")));
                st.setPeriod(rs.getInt("PERIOD"));
                st.setPeriodUnit(dbPeriodUnitToPeriodUnit(rs.getString("PERIOD_UNIT")));
                st.setScheduleDays(rs.getInt("SCHED_DAYS"));

                long transactionId = rs.getLong("TRANSACTION_ID");
                TransactionBase t = loadTransaction(transactionId, statTransfer, statSimple, statSplit);
                st.setTransactionBase(t);
                account.addScheduledTransaction(st);
            }

        }
    }

    private final String SEL_TRANSFERS_SQL =
            "select TO_FROM_ACCOUNTS.NAME AS ACCOUNT_NAME, OBJ_TRANSACTIONS.TRANSACTION_ID from OBJ_TRANSFERS " +
            "inner join OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_TRANSFERS.TRANSACTION_ID " +
            "inner join OBJ_ACCOUNTS ON OBJ_TRANSACTIONS.ACCOUNT_ID = OBJ_ACCOUNTS.ACCOUNT_ID " +
            "inner join OBJ_ACCOUNTS AS TO_FROM_ACCOUNTS ON OBJ_TRANSFERS.ACCOUNT_ID = TO_FROM_ACCOUNTS.ACCOUNT_ID " +
            "where (OBJ_ACCOUNTS.ACCOUNT_ID = ?) AND (OBJ_TRANSACTIONS.IS_TEMPLATE = ?) ";
    private final String SEL_SIMPLEPAYMENTS_SQL =
            "select OBJ_PAYEES.NAME AS PAYEE_NAME, OBJ_PAYMENT_METHODS.NAME AS PAYMENT_METHOD_NAME,  " +
            "OBJ_CATEGORIES.NAME AS CATEGORY_NAME, " +
            "PARENT_CATEGORIES.NAME AS PARENT_CATEGORY_NAME, OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "from OBJ_SIMPLE_PAYMENTS " +
            "inner join OBJ_PAYMENTS ON OBJ_PAYMENTS.TRANSACTION_ID = OBJ_SIMPLE_PAYMENTS.TRANSACTION_ID " +
            "inner join OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_PAYMENTS.TRANSACTION_ID " +
            "inner join OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSACTIONS.ACCOUNT_ID " +
            "left join OBJ_PAYMENT_METHODS ON OBJ_PAYMENTS.PAYMENT_METHOD_ID = OBJ_PAYMENT_METHODS.PAYMENT_METHOD_ID " +
            "left join OBJ_PAYEES ON OBJ_PAYMENTS.PAYEE_ID = OBJ_PAYEES.PAYEE_ID " +
            "inner join OBJ_CATEGORIES ON OBJ_CATEGORIES.CATEGORY_ID = OBJ_SIMPLE_PAYMENTS.CATEGORY_ID " +
            "LEFT JOIN OBJ_CATEGORIES AS PARENT_CATEGORIES ON OBJ_CATEGORIES.PARENT_CATEGORY_ID = PARENT_CATEGORIES.CATEGORY_ID " +
            "where (OBJ_ACCOUNTS.ACCOUNT_ID = ?) AND (OBJ_TRANSACTIONS.IS_TEMPLATE = ?) ";
    private final String SEL_SPLITPAYMENTS_SQL =
            "select OBJ_PAYEES.NAME AS PAYEE_NAME, OBJ_PAYMENT_METHODS.NAME AS PAYMENT_METHOD_NAME," +
            "OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "from OBJ_SPLIT_PAYMENTS " +
            "inner join OBJ_PAYMENTS ON OBJ_PAYMENTS.TRANSACTION_ID = OBJ_SPLIT_PAYMENTS.TRANSACTION_ID  " +
            "inner join OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_PAYMENTS.TRANSACTION_ID  " +
            "inner join OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSACTIONS.ACCOUNT_ID  " +
            "left join OBJ_PAYMENT_METHODS ON OBJ_PAYMENTS.PAYMENT_METHOD_ID = OBJ_PAYMENT_METHODS.PAYMENT_METHOD_ID  " +
            "left join OBJ_PAYEES ON OBJ_PAYMENTS.PAYEE_ID = OBJ_PAYEES.PAYEE_ID " +
            "where (OBJ_ACCOUNTS.ACCOUNT_ID = ?) AND (OBJ_TRANSACTIONS.IS_TEMPLATE = ?)";

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

        for (Account account : doc.getAccounts()) {
            logger.finest("Loading transactions for account : " + account.getName());

            // -- Simple Payments
            {
                statSimplePayment.setLong(1, account.getAccountId());
                statSimplePayment.setBoolean(2, false);
                ResultSet rs = statSimplePayment.executeQuery();
                while (rs.next()) {
                    logger.finest("Transaction no: " + account.getTransactions().size());
                    SimplePayment simplePayment = new SimplePaymentController().getPayment();

                    long transactionId = rs.getLong("TRANSACTION_ID");
                    simplePayment.setTransactionId(transactionId);
                    simplePayment.setPayee(doc.getPayee(rs.getString("PAYEE_NAME")));
                    simplePayment.setPaymentMethod(doc.getPaymentType(rs.getString("PAYMENT_METHOD_NAME")));
                    simplePayment.setCategory(getCategory(rs.getString("CATEGORY_NAME"), rs.getString("PARENT_CATEGORY_NAME")));
                    Transaction t = Transaction.getInstance(connection);
                    t.loadTransactionBaseData(transactionId, simplePayment);

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
                    splitPayment.setTransactionId(transactionId);
                    splitPayment.setPayee(doc.getPayee(rs.getString("PAYEE_NAME")));
                    splitPayment.setPaymentMethod(doc.getPaymentType(rs.getString("PAYMENT_METHOD_NAME")));
                    loadSplitTransactions(splitPayment);
                    Transaction t = Transaction.getInstance(connection);
                    t.loadTransactionBaseData(transactionId, splitPayment);

                    account.addSplitPayment(splitPayment, false);
                }
            }

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
                    transfer.setTransactionId(transactionId);

                    Transaction t = Transaction.getInstance(connection);
                    t.loadTransactionBaseData(transactionId, transfer);

                    account.addTransfer(transfer, false);
                }
            }

        }
    }

    private final String SEL_SPLIT_TRANSACTIONS_SQL =
            "select AMOUNT, OBJ_CATEGORIES.NAME as CAT_NAME, PARENT_CATEGORIES.NAME as PARENT_CAT_NAME, NOTE " +
            "from OBJ_SPLIT_TRANSACTIONS " +
            "inner join OBJ_CATEGORIES on OBJ_CATEGORIES.CATEGORY_ID = OBJ_SPLIT_TRANSACTIONS.CATEGORY_ID " +
            "left join OBJ_CATEGORIES AS PARENT_CATEGORIES ON OBJ_CATEGORIES.PARENT_CATEGORY_ID = PARENT_CATEGORIES.CATEGORY_ID " +
            "where OBJ_SPLIT_TRANSACTIONS.TRANSACTION_ID = ?";

    private void loadSplitTransactions(SplitPayment payment) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_SPLIT_TRANSACTIONS_SQL);
        stat.setLong(1, payment.getTransactionId());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            SplitTransaction st = new SplitTransaction();
            st.setAmount(rs.getBigDecimal("AMOUNT"));
            st.setCategory(getCategory(rs.getString("CAT_NAME"), rs.getString("PARENT_CAT_NAME")));
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

    private final String SEL_TRANSACTION_TYPE_SQL =
            "select  " +
            "    OBJ_TRANSFERS.TRANSACTION_ID AS TRANSFER,   " +
            "    OBJ_SIMPLE_PAYMENTS.TRANSACTION_ID AS SIMPLE,   " +
            "    OBJ_SPLIT_PAYMENTS.TRANSACTION_ID AS SPLIT " +
            "from  " +
            "    OBJ_TRANSACTIONS " +
            "    left join OBJ_TRANSFERS on OBJ_TRANSFERS.TRANSACTION_ID = OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "    left join OBJ_SIMPLE_PAYMENTS on OBJ_SIMPLE_PAYMENTS.TRANSACTION_ID = OBJ_TRANSACTIONS.TRANSACTION_ID  " +
            "    left join OBJ_SPLIT_PAYMENTS on OBJ_SPLIT_PAYMENTS.TRANSACTION_ID = OBJ_TRANSACTIONS.TRANSACTION_ID  " +
            "where  " +
            "    OBJ_TRANSACTIONS.TRANSACTION_ID = ? ";
    private final String SEL_TRANSFERS_DATA_SQL =
            "select TO_FROM_ACCOUNTS.NAME AS ACCOUNT_NAME, OBJ_TRANSACTIONS.TRANSACTION_ID from OBJ_TRANSFERS " +
            "inner join OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_TRANSFERS.TRANSACTION_ID " +
            "inner join OBJ_ACCOUNTS ON OBJ_TRANSACTIONS.ACCOUNT_ID = OBJ_ACCOUNTS.ACCOUNT_ID " +
            "inner join OBJ_ACCOUNTS AS TO_FROM_ACCOUNTS ON OBJ_TRANSFERS.ACCOUNT_ID = TO_FROM_ACCOUNTS.ACCOUNT_ID " +
            "where (OBJ_TRANSACTIONS.TRANSACTION_ID = ?)";
    private final String SEL_SIMPLE_DATA_SQL =
            "select OBJ_PAYEES.NAME AS PAYEE_NAME, OBJ_PAYMENT_METHODS.NAME AS PAYMENT_METHOD_NAME,  " +
            "OBJ_CATEGORIES.NAME AS CATEGORY_NAME, " +
            "PARENT_CATEGORIES.NAME AS PARENT_CATEGORY_NAME, OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "from OBJ_SIMPLE_PAYMENTS " +
            "inner join OBJ_PAYMENTS ON OBJ_PAYMENTS.TRANSACTION_ID = OBJ_SIMPLE_PAYMENTS.TRANSACTION_ID " +
            "inner join OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_PAYMENTS.TRANSACTION_ID " +
            "inner join OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSACTIONS.ACCOUNT_ID " +
            "inner join OBJ_PAYMENT_METHODS ON OBJ_PAYMENTS.PAYMENT_METHOD_ID = OBJ_PAYMENT_METHODS.PAYMENT_METHOD_ID " +
            "inner join OBJ_PAYEES ON OBJ_PAYMENTS.PAYEE_ID = OBJ_PAYEES.PAYEE_ID " +
            "inner join OBJ_CATEGORIES ON OBJ_CATEGORIES.CATEGORY_ID = OBJ_SIMPLE_PAYMENTS.CATEGORY_ID " +
            "LEFT JOIN OBJ_CATEGORIES AS PARENT_CATEGORIES ON OBJ_CATEGORIES.PARENT_CATEGORY_ID = PARENT_CATEGORIES.CATEGORY_ID " +
            "where (OBJ_TRANSACTIONS.TRANSACTION_ID = ?) ";
    private final String SEL_SPLIT_DATA_SQL =
            "select OBJ_PAYEES.NAME AS PAYEE_NAME, OBJ_PAYMENT_METHODS.NAME AS PAYMENT_METHOD_NAME," +
            "OBJ_TRANSACTIONS.TRANSACTION_ID " +
            "from OBJ_SPLIT_PAYMENTS " +
            "inner join OBJ_PAYMENTS ON OBJ_PAYMENTS.TRANSACTION_ID = OBJ_SPLIT_PAYMENTS.TRANSACTION_ID  " +
            "inner join OBJ_TRANSACTIONS ON OBJ_TRANSACTIONS.TRANSACTION_ID = OBJ_PAYMENTS.TRANSACTION_ID  " +
            "inner join OBJ_ACCOUNTS ON OBJ_ACCOUNTS.ACCOUNT_ID = OBJ_TRANSACTIONS.ACCOUNT_ID  " +
            "inner join OBJ_PAYMENT_METHODS ON OBJ_PAYMENTS.PAYMENT_METHOD_ID = OBJ_PAYMENT_METHODS.PAYMENT_METHOD_ID  " +
            "inner join OBJ_PAYEES ON OBJ_PAYMENTS.PAYEE_ID = OBJ_PAYEES.PAYEE_ID " +
            "where (OBJ_TRANSACTIONS.TRANSACTION_ID = ?)";

    private TransactionBase loadTransaction(long transactionId,
            PreparedStatement statTransfer, PreparedStatement statSimple, PreparedStatement statSplit) throws SQLException {
        PreparedStatement stat = connection.prepareStatement(SEL_TRANSACTION_TYPE_SQL);
        stat.setLong(1, transactionId);
        ResultSet rs = stat.executeQuery();
        rs.next();
        if (rs.getObject("TRANSFER") != null) {
            statTransfer.setLong(1, transactionId);
            ResultSet rsTx = statTransfer.executeQuery();
            if (rsTx.next()) {
                Transfer tc = new TransferController().getTransfer();
                tc.setToFromAccount(doc.getAccount(rsTx.getString("ACCOUNT_NAME")));
                Transaction t = Transaction.getInstance(connection);
                t.loadTransactionBaseData(transactionId, tc);
                return tc;
            }
        } else if (rs.getObject("SIMPLE") != null) {
            statSimple.setLong(1, transactionId);
            ResultSet rsTx = statSimple.executeQuery();
            if (rsTx.next()) {
                SimplePayment sp = new SimplePaymentController().getPayment();
                sp.setPayee(doc.getPayee(rsTx.getString("PAYEE_NAME")));
                sp.setPaymentMethod(doc.getPaymentType(rsTx.getString("PAYMENT_METHOD_NAME")));
                sp.setCategory(getCategory(rsTx.getString("CATEGORY_NAME"), rsTx.getString("PARENT_CATEGORY_NAME")));
                Transaction t = Transaction.getInstance(connection);
                t.loadTransactionBaseData(transactionId, sp);
                return sp;
            }
        } else if (rs.getObject("SPLIT") != null) {
            statSplit.setLong(1, transactionId);
            ResultSet rsTx = statSplit.executeQuery();
            if (rsTx.next()) {
                SplitPayment sp = new SplitPaymentController().getPayment();
                sp.setPayee(doc.getPayee(rsTx.getString("PAYEE_NAME")));
                sp.setPaymentMethod(doc.getPaymentType(rsTx.getString("PAYMENT_METHOD_NAME")));
                Transaction t = Transaction.getInstance(connection);
                t.loadTransactionBaseData(transactionId, sp);
                loadSplitTransactions(sp);
                return sp;
            }
        }
        throw new IllegalStateException("Unhandled ");
    }

}
