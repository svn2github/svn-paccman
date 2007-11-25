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

package org.paccman.xml._1_0_0_0;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.paccman.controller.DocumentController;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.paccman.Category;
import org.paccman.paccman.CategoryBase;
import org.paccman.paccman.PaymentMethod;
import org.paccman.paccman.SplitPayment;
import org.paccman.paccman.SplitTransaction;
import org.paccman.paccman.Document;
import org.paccman.paccman.SimplePayment;
import org.paccman.paccman.SubCategory;
import org.paccman.paccman.Payee;
import org.paccman.paccman.ScheduledTransaction;
import org.paccman.paccman.TransactionBase;
import org.paccman.paccman.Transfer;
import org.paccman.xml.PaccmanIOException;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Accounts;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Banks;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Categories;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Payees;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.PaymentTypes;

/**
 *
 * @author joao
 */
public class Saver extends org.paccman.xml.Saver {

    Paccman paccman;

    /**
     * Creates a new instance of Saver
     */
    public Saver() {
    }

    public void save(OutputStream os, DocumentController documentCtrl) throws PaccmanIOException {
        Document document = documentCtrl.getDocument();

        PaccmanDocument doc = PaccmanDocument.Factory.newInstance();
        paccman = doc.addNewPaccman();

        // Read title
        paccman.setTitle(document.getTitle()); //OK

        saveBanks(document);                   //OK

        saveCategories(document);              //OK

        savePayees(document);                  //OK

        savePaymentTypes(document);            //OK

        saveAccounts(document);                //OK

        saveTransactions(document);            //KO

        saveScheduledTransactions(document);   //KO

        try {

            logger.finest("Saving file");
            doc.save(os);
        } catch (IOException e) {
            throw new PaccmanIOException("Unable to save file");
        }

    }

    private void savePaymentTypes(Document doc) {
        // Write payment types
        PaymentTypes paymentTypes = paccman.addNewPaymentTypes();
        for (PaymentMethod payment : doc.getPaymentMethods()) {
            PaymentTypeType pt = paymentTypes.addNewPaymentType();
            pt.setName(payment.getName());

            logger.finest("Saved payment type: " + pt.getName());
        }
    }

    private void saveScheduledData(Document document, ScheduledTransaction st, ScheduledTransactionType xSt) {
        xSt.setAutomatic(st.isAutomatic());
        xSt.setDescription(st.getDescription());
        xSt.setFixedAmount(st.isFixedAmount());
//        xSt.setIdentifier(st.getIdentifier());
        xSt.setNextOccurence(st.getNextOccurence());
        xSt.setPeriod(st.getPeriod());
        xSt.setPeriodUnit(periodUnitToXmlPeriodUnit(st.getPeriodUnit()));
        xSt.setScheduleDays(st.getScheduleDays());
    }

    private void saveScheduledTransactions(Document document) throws PaccmanIOException {

        Accounts xAccounts = paccman.getAccounts();
        for (AccountType xAccount : xAccounts.getAccountList()) {
            Account account = document.getAccount(xAccount.getName());

            // Transactions
            AccountType.ScheduledTransactions schedTransactions = xAccount.addNewScheduledTransactions();
            for (ScheduledTransaction schedTransaction : account.getScheduledTransactions()) {

                ScheduledTransactionType xSt = schedTransactions.addNewScheduledTransaction();
                TransactionBase transaction = schedTransaction.getTransactionBase();

                saveScheduledData(document, schedTransaction, xSt);

                // Simple Payments
                if (transaction instanceof SimplePayment) {
                    SimplePayment simplePayment = (SimplePayment) transaction;
                    SimplePaymentType xSimplePayment = xSt.addNewSimplePayment();

                    saveTransactionBase(xSimplePayment, simplePayment);

                    Payee payee = simplePayment.getPayee();
                    xSimplePayment.setPayee(payee != null ? payee.getName() : null);
                    PaymentMethod paymentMethod = simplePayment.getPaymentMethod();
                    xSimplePayment.setPaymentType(paymentMethod != null ? paymentMethod.getName() : null);
                    xSimplePayment.setCategoryPath(toCategoryPath(simplePayment.getCategory()));

                } // Split Payments
                else if (transaction instanceof SplitPayment) {
                    SplitPayment splitPayment = (SplitPayment) transaction;
                    SplitPaymentType xSplitPayment = xSt.addNewSplitPayment();

                    saveTransactionBase(xSplitPayment, splitPayment);

                    Payee payee = splitPayment.getPayee();
                    xSplitPayment.setPayee(payee != null ? payee.getName() : null);
                    PaymentMethod paymentMethod = splitPayment.getPaymentMethod();
                    xSplitPayment.setPaymentType(paymentMethod != null ? paymentMethod.getName() : null);
                    saveSplitTransaction(document, splitPayment, xSplitPayment);
                } // Transfers
                else if (transaction instanceof Transfer) {
                    Transfer transfer = (Transfer) transaction;
                    TransferType xTransfer = xSt.addNewTransfer();

                    saveTransactionBase(xTransfer, transfer);

                    xTransfer.setToAccount(transfer.getToFromAccount().getName());
                } // default
                else {
                    assert (false) : "Invalid transactin type";
                }
            }
        }

    }

    private void saveBanks(Document doc) {
        // Write banks
        Banks banks = paccman.addNewBanks();
        for (Bank bank : doc.getBanks()) {
            BankType bt = banks.addNewBank();

            assert (bank.getName() != null) && (bank.getName().length() > 0);
            bt.setName(bank.getName());

            String info;
            if ((info = bank.getAgency()) != null) {
                bt.setAgency(info);
            }
            if ((info = bank.getAddressNumber()) != null) {
                bt.setAddressNumber(info);
            }
            if ((info = bank.getAddressLine1()) != null) {
                bt.setAddressLine1(info);
            }
            if ((info = bank.getAddressLine2()) != null) {
                bt.setAddressLine2(info);
            }
            if ((info = bank.getAddressZip()) != null) {
                bt.setAddressZip(info);
            }
            if ((info = bank.getAddressCity()) != null) {
                bt.setAddressCity(info);
            }
            if ((info = bank.getAddressCountry()) != null) {
                bt.setAddressCountry(info);
            }
            if ((info = bank.getTelephone()) != null) {
                bt.setTelephone(info);
            }
            if ((info = bank.getFax()) != null) {
                bt.setFax(info);
            }
            if ((info = bank.getInternet()) != null) {
                bt.setInternet(info);
            }
            if ((info = bank.getEmail()) != null) {
                bt.setEmail(info);
            }

        }
    }

    private void saveCategories(Document doc) {
        // Write categories
        Categories categories = paccman.addNewCategories();
        for (Category category : doc.getCategories()) {

            CategoryType ct = categories.addNewCategory();
            ct.setName(category.getName());
            ct.setDescription(category.getDescription());
            ct.setIncome(category.isIncome());

            for (SubCategory sc : category.getSubCategories()) {
                SubCategoryType sct = ct.addNewSubcategory();
                sct.setName(sc.getName());
                sct.setDescription(sc.getDescription());
            }
        }
    }

    private void savePayees(Document doc) {
        // Write payees
        Payees payees = paccman.addNewPayees();
        for (Payee payee : doc.getPayees()) {
            PayeeType pt = payees.addNewPayee();
            pt.setName(payee.getName());
        }
    }

    private void saveAccounts(Document doc) {
        // Write accounts
        Accounts accounts = paccman.addNewAccounts();
        for (Account account : doc.getAccounts()) {
            AccountType at = accounts.addNewAccount();

            // Name
            at.setName(account.getName());

            // Bank
            at.setBank(account.getBank().getName());

            // Initial balance
            at.setInitialBalance(account.getInitialBalance());

            // Account number and key
            at.setAccountNumber(account.getAccountNumber());
            at.setAccountNumberKey(account.getAccountNumberKey());

            // Account note
            at.setNote(account.getNote());

            // Holder name and address
            at.setHolderName(account.getHolderName());
            at.setHolderAdress(account.getHolderAddress());

            // Reconciliation data
            at.setLastReconciliationBalance(account.getLastReconciliationBalance());
            at.setLastReconciliationDate(account.getLastReconciliationDate());
            at.setPendingReconciliation(account.isPendingReconciliation());
            at.setPendingReconciliationBalance(account.getPendingReconciliationBalance());
            at.setPendingReconciliationDate(account.getPendingReconciliationDate());

        }
    }

    private ReconciledStatus.Enum reconcileToXmlReconcile(TransactionBase.ReconciliationState state) throws PaccmanIOException {
        if (state == TransactionBase.ReconciliationState.UNRECONCILED) {
            return ReconciledStatus.U;
        } else if (state == TransactionBase.ReconciliationState.MARKED) {
            return ReconciledStatus.M;
        } else if (state == TransactionBase.ReconciliationState.RECONCILED) {
            return ReconciledStatus.R;
        } else {
            throw new PaccmanIOException("Invalid reconciliation state.");
        }
    }

    private void saveTransactionBase(TransactionBaseType transactionType, TransactionBase transaction) throws PaccmanIOException {
        transactionType.setAmount(transaction.getAmount());
        transactionType.setValueDate(transaction.getValueDate());
        transactionType.setTransactionDate(transaction.getTransactionDate());
        transactionType.setNote(transaction.getNote());
        transactionType.setLabel(transaction.getLabel());
        transactionType.setReconciliationState(reconcileToXmlReconcile(transaction.getReconciliationState()));
        transactionType.setReconciliationDate(transaction.getReconciliationDate());
    }

    String toCategoryPath(CategoryBase category) {
        if (category == null) {
            return null;
        } else {
            String categoryPath;
            if (category instanceof Category) {
                categoryPath = category.getName();
            } else {
                assert category instanceof SubCategory : "Should be Category or SubCategory";
                SubCategory sc = (SubCategory) category;
                categoryPath = sc.getParentCategory().getName();
                categoryPath += PaccmanIO.CATEGORY_SUBCATEGORY_SEPARATOR;
                categoryPath += category.getName();
            }
            return categoryPath;
        }
    }

    org.paccman.xml._1_0_0_0.PeriodUnit.Enum periodUnitToXmlPeriodUnit(ScheduledTransaction.PeriodUnit periodUnit) {
        if (periodUnit == ScheduledTransaction.PeriodUnit.DAY) {
            return org.paccman.xml._1_0_0_0.PeriodUnit.DAY;
        } else if (periodUnit == ScheduledTransaction.PeriodUnit.WEEK) {
            return org.paccman.xml._1_0_0_0.PeriodUnit.WEEK;
        } else if (periodUnit == ScheduledTransaction.PeriodUnit.MONTH) {
            return org.paccman.xml._1_0_0_0.PeriodUnit.MONTH;
        } else if (periodUnit == ScheduledTransaction.PeriodUnit.YEAR) {
            return org.paccman.xml._1_0_0_0.PeriodUnit.YEAR;
        } else {
            return null;
        }
    }

    private void saveTransactions(Document document) throws PaccmanIOException {
        // Write accounts
        Accounts xAccounts = paccman.getAccounts();
        for (AccountType xAccount : xAccounts.getAccountList()) {
            Account account = document.getAccount(xAccount.getName());

            logger.finest("Saving transactions for account : " + account.getName());

            // Transactions
            int transactionNo = 0;
            AccountType.Transactions transactions = xAccount.addNewTransactions();
            for (TransactionBase transaction : account.getTransactions()) {

                // Simple Payments
                if (transaction instanceof SimplePayment) {
                    SimplePayment simplePayment = (SimplePayment) transaction;
                    SimplePaymentType xSimplePayment = transactions.addNewSimplePayment();

                    logger.finest("Transaction no: " + transactionNo++);
                    saveTransactionBase(xSimplePayment, simplePayment);

                    Payee payee = simplePayment.getPayee();
                    xSimplePayment.setPayee(payee != null ? payee.getName() : null);
                    PaymentMethod paymentMethod = simplePayment.getPaymentMethod();
                    xSimplePayment.setPaymentType(paymentMethod != null ? paymentMethod.getName() : null);
                    xSimplePayment.setCategoryPath(toCategoryPath(simplePayment.getCategory()));

                } // Split Payments
                else if (transaction instanceof SplitPayment) {
                    SplitPayment splitPayment = (SplitPayment) transaction;
                    SplitPaymentType xSplitPayment = transactions.addNewSplitPayment();

                    logger.finest("Transaction no: " + transactionNo++);
                    saveTransactionBase(xSplitPayment, splitPayment);

                    Payee payee = splitPayment.getPayee();
                    xSplitPayment.setPayee(payee != null ? payee.getName() : null);
                    PaymentMethod paymentMethod = splitPayment.getPaymentMethod();
                    xSplitPayment.setPaymentType(paymentMethod != null ? paymentMethod.getName() : null);
                    saveSplitTransaction(document, splitPayment, xSplitPayment);
                } // Transfers
                else if (transaction instanceof Transfer) {
                    Transfer transfer = (Transfer) transaction;
                    TransferType xTransfer = transactions.addNewTransfer();

                    logger.finest("Transaction no: " + transactionNo++);
                    saveTransactionBase(xTransfer, transfer);

                    xTransfer.setToAccount(transfer.getToFromAccount().getName());
                } // dafault
                else {
                    assert (false) : "Invalid category";
                }
            }
        }
    }

    private void saveSplitTransaction(Document document, SplitPayment payment, SplitPaymentType xPayment) {
        for (SplitTransaction tb : payment.getSplitTransactions()) {
            SplitTransactionType xTb = xPayment.addNewTransaction();
            xTb.setAmount(tb.getAmount());
            xTb.setCategoryPath(toCategoryPath(tb.getCategory()));
        }
    }

    //
    // Logging
    //

    private static Logger logger = Logger.getLogger(Saver.class.getName());
    private static Handler consoleHandler = new ConsoleHandler();
     {
        consoleHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
    }
}
