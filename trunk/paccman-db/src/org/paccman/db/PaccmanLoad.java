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
import org.paccman.controller.BankController;
import org.paccman.controller.CategoryController;
import org.paccman.controller.DocumentController;
import org.paccman.controller.SubCategoryController;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.paccman.Category;
import org.paccman.paccman.Document;
import org.paccman.paccman.SubCategory;
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

        loadPaymentTypes();

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
            "FROM OBJ_CATGEORIES WHERE PARENT_CATEGORY_ID = ?";

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

    private void loadPayees() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadPaymentTypes() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadScheduledTransactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadTransactions() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void loadAccounts() {
        throw new UnsupportedOperationException("Not yet implemented");
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
                    c.setTime(new SimpleDateFormat().parse(value));
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
