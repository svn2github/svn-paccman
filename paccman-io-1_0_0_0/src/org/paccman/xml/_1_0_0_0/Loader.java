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
import java.io.InputStream;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlbeans.XmlException;
import org.paccman.controller.AccountController;
import org.paccman.controller.BankController;
import org.paccman.controller.CategoryController;
import org.paccman.controller.DocumentController;
import org.paccman.controller.PaymentMethodController;
import org.paccman.controller.ScheduledTransactionController;
import org.paccman.controller.SimplePaymentController;
import org.paccman.controller.SplitPaymentController;
import org.paccman.controller.PayeeController;
import org.paccman.controller.SubCategoryController;
import org.paccman.controller.TransferController;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;
import org.paccman.paccman.Category;
import org.paccman.paccman.CategoryBase;
import org.paccman.paccman.ScheduledTransaction;
import org.paccman.paccman.SplitPayment;
import org.paccman.paccman.SplitTransaction;
import org.paccman.paccman.Document;
import org.paccman.paccman.PaymentMethod;
import org.paccman.paccman.SimplePayment;
import org.paccman.paccman.Payee;
import org.paccman.paccman.TransactionBase;
import org.paccman.paccman.Transfer;
import org.paccman.xml.PaccmanIOException;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Accounts;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Banks;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Categories;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.PaymentTypes;
import org.paccman.xml._1_0_0_0.PaccmanDocument.Paccman.Payees;

/**
 *
 * @author joao
 */
public class Loader extends org.paccman.xml.Loader {
    
    PaccmanDocument pacDocument;
    
    /**
     * Creates a new instance of Loader
     */
    public Loader()  {
    }
    
    public void load(InputStream is, DocumentController documentCtrl) throws PaccmanIOException {
        try {
            logger.finest("Parsing file");
            pacDocument = PaccmanDocument.Factory.parse(is);
        } catch (IOException e) {
            throw new PaccmanIOException("Failed while reading document");
        } catch (XmlException e) {
            throw new PaccmanIOException("Document parse failed");
        }
        
        Document document = documentCtrl.getDocument();
        
        document.setTitle(pacDocument.getPaccman().getTitle());
        
        loadBanks(document);
        
        loadCategories(document);
        
        loadPayees(document);
        
        loadPaymentTypes(document);
        
        loadAccounts(document);
        
        // load transaction after accounts (transfer transactions need the
        // destination account to be created)
        loadTransactions(document);
        
        // load scheduled transaction after accounts (transfer transactions need the
        // destination account to be created)
        loadScheduledTransactions(document);
        
        // update all balances after all transaction have been loaded
        // (including transfers from other accounts !!!)
        updateAllBalances(document);
    }
    
    private void loadBanks(Document document) {
        Banks banks = pacDocument.getPaccman().getBanks();
        for (BankType bt: banks.getBankList()) {
            BankController bankCtrl = new BankController();
            Bank bank = bankCtrl.getBank();
            
            // Get bank properties
            bank.setName(bt.getName());
            bank.setAgency(bt.getAgency());
            bank.setAddressNumber(bt.getAddressNumber());
            bank.setAddressLine1(bt.getAddressLine1());
            bank.setAddressLine2(bt.getAddressLine2());
            bank.setAddressZip(bt.getAddressZip());
            bank.setAddressCity(bt.getAddressCity());
            bank.setAddressCountry(bt.getAddressCountry());
            bank.setTelephone(bt.getTelephone());
            bank.setFax(bt.getFax());
            bank.setInternet(bt.getInternet());
            bank.setEmail(bt.getEmail());
            
            // Add bank to document
            document.addBank(bank);
            
            logger.finest("Loaded Bank: " + bank.getName());
        }
    }
    
    private void loadPaymentTypes(Document document) {
        PaymentTypes paymentTypes = pacDocument.getPaccman().getPaymentTypes();
        for (PaymentTypeType paymentType: paymentTypes.getPaymentTypeList()) {
            PaymentMethodController paymentCtrl = new PaymentMethodController();
            PaymentMethod payment = paymentCtrl.getPaymentMethod();
            
            payment.setName(paymentType.getName());
            document.addPaymentMethod(payment);
            
            logger.finest("Loaded PaymentType: " + payment.getName());
        }
    }
    
    private void loadCategories(Document document) {
        Categories categories = pacDocument.getPaccman().getCategories();
        for (CategoryType ct: categories.getCategoryList()) {
            CategoryController categoryCtrl = new CategoryController();
            Category category = categoryCtrl.getCategory();
            
            category.setName(ct.getName());
            category.setDescription(ct.getDescription());
            category.setIncome(ct.getIncome());
            
            for (SubCategoryType sct: ct.getSubcategoryList()) {
                SubCategoryController scc = new SubCategoryController(category);
                scc.getSubCategory().setName(sct.getName());
                scc.getSubCategory().setDescription(sct.getDescription());
                category.addSubCategory(scc.getSubCategory());
            }
            document.addCategory(category);
            
            logger.finest("Loaded Category: " + category.getName());
            
        }
    }
    
    private void loadPayees(Document document) {
        Payees payees = pacDocument.getPaccman().getPayees();
        for (PayeeType payee: payees.getPayeeList()) {
            PayeeController pc = new PayeeController();
            Payee p = pc.getPayee();
            
            p.setName(payee.getName());
            document.addPayee(p);
            
            logger.finest("Loaded Payee: " + payee.getName());
            
        }
    }
    
    private void loadAccounts(Document document) throws PaccmanIOException {
        Accounts accounts = pacDocument.getPaccman().getAccounts();
        for (AccountType account: accounts.getAccountList()) {
            Bank bank = document.getBank(account.getBank());
            if (bank == null) {
                throw new PaccmanIOException("Bank of account '" + account.getName() + "' not found");
            }
            AccountController newAccountCtrl = new AccountController();
            Account newAccount = newAccountCtrl.getAccount();
            
            newAccount.setName(account.getName());
            newAccount.setBank(bank);
            newAccount.setInitialBalance(account.getInitialBalance());
            newAccount.setAccountNumber(account.getAccountNumber());
            newAccount.setAccountNumberKey(account.getAccountNumberKey());
            newAccount.setNote(account.getNote());
            
            // Holder data
            newAccount.setHolderName(account.getHolderName());
            newAccount.setHolderAddress(account.getHolderAdress());
            
            // Reconciliation data
            newAccount.setLastReconciliationBalance(account.getLastReconciliationBalance());
            newAccount.setLastReconciliationDate(account.getLastReconciliationDate());
            newAccount.setPendingReconciliation(account.getPendingReconciliation());
            if (account.getPendingReconciliation()) {
                newAccount.setPendingReconciliationBalance(account.getPendingReconciliationBalance());
                newAccount.setPendingReconciliationDate(account.getPendingReconciliationDate());
            } else {
                newAccount.setPendingReconciliationBalance(null);
                newAccount.setPendingReconciliationDate(null);
            }
            
            document.addAccount(newAccount);
            
            logger.finest("Loaded Account: " + newAccount.getName());
            
        }
    }
    
    private TransactionBase.ReconciliationState xmlReconcileToReconcile(ReconciledStatus.Enum state) throws PaccmanIOException {
        if (state == ReconciledStatus.U) {
            return TransactionBase.ReconciliationState.UNRECONCILED  ;
        } else if (state == ReconciledStatus.M) {
            return TransactionBase.ReconciliationState.MARKED        ;
        } else if (state == ReconciledStatus.R) {
            return TransactionBase.ReconciliationState.RECONCILED    ;
        } else {
            throw new PaccmanIOException("Invalid reconciliation state.");
        }
    }
    
    private void loadTransactionBase(TransactionBaseType transactionType, TransactionBase transaction) throws PaccmanIOException {
        transaction.setAmount(transactionType.getAmount());
        transaction.setValueDate(transactionType.getValueDate());
        transaction.setTransactionDate(transactionType.getTransactionDate());
        transaction.setNote(transactionType.getNote());
        transaction.setLabel(transactionType.getLabel());
        transaction.setReconciliationState(xmlReconcileToReconcile(transactionType.getReconciliationState()));
        transaction.setReconciliationDate(transactionType.getReconciliationDate());
    }
    
    private CategoryBase getCategory(Document document, String categoryPath) throws PaccmanIOException {
        if (categoryPath == null) {
            return null;
        } else {
            String[] catSubCat = categoryPath.split(new String(new char [] { PaccmanIO.CATEGORY_SUBCATEGORY_SEPARATOR } ));
            if (catSubCat.length == 1) {
                return document.getCategory(catSubCat[0]);
            } else if (catSubCat.length == 2) {
                return document.getCategory(catSubCat[0], catSubCat[1]);
            } else {
                throw new PaccmanIOException("Invalid category path.");
            }
        }
    }
    
    private void loadTransfer(Document document, TransferType xTransfer, Transfer transfer) throws PaccmanIOException {
        loadTransactionBase(xTransfer, transfer);
        
        transfer.setToFromAccount(document.getAccount(xTransfer.getToAccount()));
        
    }
    
    private void loadSimplePayment(Document document, SimplePaymentType xSimplePayment, SimplePayment simplePayement) throws PaccmanIOException {
        loadTransactionBase(xSimplePayment, simplePayement);
        
        simplePayement.setPayee(document.getPayee(xSimplePayment.getPayee()));
        simplePayement.setCategory(getCategory(document, xSimplePayment.getCategoryPath()));
        simplePayement.setPaymentMethod(document.getPaymentType(xSimplePayment.getPaymentType()));
    }
    
    private void loadSplitPayment(Document document, SplitPaymentType xSplitPayment, SplitPayment splitPayment) throws PaccmanIOException {
        loadTransactionBase(xSplitPayment, splitPayment);
        
        splitPayment.setPayee(document.getPayee(xSplitPayment.getPayee()));
        splitPayment.setPaymentMethod(document.getPaymentType(xSplitPayment.getPaymentType()));
        
        loadSplitTransactions(document, xSplitPayment, splitPayment);
    }
    
    private void loadTransactions(Document document) throws PaccmanIOException {
        Accounts accounts = pacDocument.getPaccman().getAccounts();
        for (AccountType xAccount: accounts.getAccountList()) {
            logger.finest("Loading transactions for account : " + xAccount.getName());
            
            Account account = document.getAccount(xAccount.getName());
            account.getTransactions().ensureCapacity(
                    xAccount.getTransactions().getSimplePaymentList().size() +
                    xAccount.getTransactions().getSplitPaymentList().size() +
                    xAccount.getTransactions().getTransferList().size());
            
            // -- Simple Payments
            List<SimplePaymentType> xSimplePayments = xAccount.getTransactions().getSimplePaymentList();
            for (SimplePaymentType xSimplePayment: xSimplePayments) {
                logger.finest("Transaction no: " + account.getTransactions().size());
                SimplePaymentController paymentCtrl = new SimplePaymentController();
                SimplePayment payment = paymentCtrl.getPayment();
                
                loadSimplePayment(document, xSimplePayment, payment);
                
                account.addSimplePayment(payment, false);
            }
            
            // -- Split Payments
            List<SplitPaymentType> xSplitPayments = xAccount.getTransactions().getSplitPaymentList();
            for (SplitPaymentType xSplitPayment: xSplitPayments) {
                logger.finest("Transaction no: " + account.getTransactions().size());
                SplitPaymentController splitPaymentCtrl = new SplitPaymentController();
                SplitPayment splitPayment = splitPaymentCtrl.getPayment();
                
                loadSplitPayment(document, xSplitPayment, splitPayment);
                
                account.addSplitPayment(splitPayment, false);
            }
            
            // -- Transfers
            List<TransferType> xTransfers = xAccount.getTransactions().getTransferList();
            for (TransferType xTransfer: xTransfers) {
                logger.finest("Transaction no: " + account.getTransactions().size());
                TransferController transferCtrl = new TransferController();
                Transfer transfer = transferCtrl.getTransfer();
                
                loadTransfer(document, xTransfer, transfer);
                
                account.addTransfer(transfer, false);
            }
            
        }
    }
    
    ScheduledTransaction.PeriodUnit xmlPeriodUnitToPeriodUnit(org.paccman.xml._1_0_0_0.PeriodUnit.Enum xmlPeriodUnit) {
        if ( xmlPeriodUnit == org.paccman.xml._1_0_0_0.PeriodUnit.DAY) {
            return ScheduledTransaction.PeriodUnit.DAY;
        } else if (xmlPeriodUnit  == org.paccman.xml._1_0_0_0.PeriodUnit.WEEK) {
            return ScheduledTransaction.PeriodUnit.WEEK;
        } else if (xmlPeriodUnit  == org.paccman.xml._1_0_0_0.PeriodUnit.MONTH) {
            return ScheduledTransaction.PeriodUnit.MONTH;
        } else if (xmlPeriodUnit  == org.paccman.xml._1_0_0_0.PeriodUnit.YEAR) {
            return ScheduledTransaction.PeriodUnit.YEAR;
        } else {
            return null;
        }
    }
    
    private void loadScheduledTransactions(Document document) throws PaccmanIOException {
        Accounts accounts = pacDocument.getPaccman().getAccounts();
        for (AccountType xAccount: accounts.getAccountList()) {
            logger.finest("Loading scheduled transactions for account : " + xAccount.getName());
            
            Account account = document.getAccount(xAccount.getName());
            if (xAccount.getScheduledTransactions() == null) {
                continue;
            } else {
                for (ScheduledTransactionType xSt: xAccount.getScheduledTransactions().getScheduledTransactionList()) {
                    ScheduledTransactionController stc = new ScheduledTransactionController();
                    
                    if (xSt.isSetTransfer()) {
                        TransferController transfer = new TransferController();
                        loadTransfer(document, xSt.getTransfer(), transfer.getTransfer());
                        stc.getScheduledTransaction().setTransactionBase(transfer.getTransfer());
                    } else if (xSt.isSetSimplePayment()) {
                        SimplePaymentController simplePayment = new SimplePaymentController();
                        loadSimplePayment(document, xSt.getSimplePayment(), simplePayment.getPayment());
                        stc.getScheduledTransaction().setTransactionBase(simplePayment.getPayment());
                    } else if (xSt.isSetSplitPayment()) {
                        SplitPaymentController splitPayment = new SplitPaymentController();
                        loadSplitPayment(document, xSt.getSplitPayment(), splitPayment.getPayment());
                        stc.getScheduledTransaction().setTransactionBase(splitPayment.getPayment());
                    }
                    
                    stc.getScheduledTransaction().setIdentifier(xSt.getIdentifier());
                    stc.getScheduledTransaction().setPeriod(xSt.getPeriod());
                    stc.getScheduledTransaction().setPeriodUnit(xmlPeriodUnitToPeriodUnit(xSt.getPeriodUnit()));
                    stc.getScheduledTransaction().setNextOccurence(xSt.getNextOccurence());
                    stc.getScheduledTransaction().setAutomatic(xSt.getAutomatic());
                    stc.getScheduledTransaction().setScheduleDays(xSt.getScheduleDays());
                    stc.getScheduledTransaction().setDescription(xSt.getDescription());
                    stc.getScheduledTransaction().setFixedAmount(xSt.getFixedAmount());
                    
                    account.addScheduledTransaction(stc.getScheduledTransaction());
                    
                }
            }
        }
        
    }
    
    private void loadSplitTransactions(Document document, SplitPaymentType xPayment, SplitPayment payment) throws PaccmanIOException {
        for (SplitTransactionType xTb: xPayment.getTransactionList()) {
            SplitTransaction tb = new SplitTransaction();
            tb.setAmount(xTb.getAmount());
            tb.setCategory(getCategory(document, xTb.getCategoryPath()));
            tb.setNote(xTb.getNote());
            payment.addSplitTransaction(tb);
        }
    }
    
    private void updateAllBalances(Document document) {
        for (Account account: document.getAccounts()) {
            logger.finest("Updating balances for account: " + account.getName());
            account.updateBalances();
        }
    }
    
    //
    // Logging
    //
    private static Logger   logger         = Logger.getLogger(Loader.class.getName());
    private static Handler  consoleHandler = new ConsoleHandler();
    {
        consoleHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);
    }
    
}
