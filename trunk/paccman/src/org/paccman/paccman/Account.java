/*
 *
 *  Copyright (C)    2005 Joao F. (joaof@sourceforge.net)
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

package org.paccman.paccman;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author joao
 */
public class Account extends PaccmanObject {

    private String name;
    private BigDecimal initialBalance;
    private Bank bank;
    private String accountNumber;
    private String note;
    private long accountId;

    /**
     * Gets the ID of this account.
     * @return This account's ID.
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Sets the ID of this account.
     * @param accountId The ID to be set.
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    private ArrayList<TransactionBase> transactions = new ArrayList<TransactionBase>();
    private ArrayList<ScheduledTransaction> scheduledTransactions = new ArrayList<ScheduledTransaction>();

    /**
     * 
     * @return 
     */
    public ArrayList<TransactionBase> getTransactions() {
        return transactions;
    }

    /**
     * 
     * @return 
     */
    public Collection<ScheduledTransaction> getScheduledTransactions() {
        return scheduledTransactions;
    }

    /**
     * 
     * @param index 
     * @return 
     */
    public ScheduledTransaction getScheduledTransaction(int index) {
        return scheduledTransactions.get(index);
    }

    class TransactionDateComparator implements Comparator<TransactionBase> {

        public int compare(TransactionBase t1, TransactionBase t2) {
            if (t1.getValueDate().compareTo(t2.getValueDate()) < 0) {
                return -1;
            } else if (t1.getValueDate().compareTo(t2.getValueDate()) == 0) {
                if (t1.getTransactionDate().compareTo(t2.getTransactionDate()) <= 0) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }
    }

    /**
     * Creates a new instance of Account
     */
    public Account() {
    }

    // add transaction base
    private int addTransactionBase(TransactionBase t, boolean updateBalances) {
        int index = Collections.binarySearch(transactions, t, new TransactionDateComparator());
        assert (index < 0);
        index = -index - 1;
        transactions.add(index, t);

        // Updte balance of the transactions after the added one.
        if (updateBalances) {
            BigDecimal balance;
            balance = (index == 0) ? initialBalance : transactions.get(index - 1).getBalance();
            for (int i = index; i < transactions.size(); i++) {
                balance = balance.add(transactions.get(i).getAmount());
                transactions.get(i).setBalance(balance);
            }
        }
        return index;
    }

    /**
     * Adds a split payment to this account.
     * @param p 
     * @param updateBalances 
     * @return 
     */
    public int addSplitPayment(SplitPayment p, boolean updateBalances) {
        return addTransactionBase(p, updateBalances);
    }

    /**
     * Adds a simple payment to this account.
     * @param p 
     * @param updateBalances 
     * @return 
     */
    public int addSimplePayment(SimplePayment p, boolean updateBalances) {
        return addTransactionBase(p, updateBalances);
    }

    // add a transaction. return the index where the transaction has been inserted
    /**
     * Adds a transaction. 
     * @param transfer 
     * @param updateBalances If <code>true</code>, reset the balances of transactions.
     * @return 
     */
    public int addTransfer(Transfer transfer, boolean updateBalances) {
        return addTransactionBase(transfer, updateBalances);
    }

    /**
     * Adds a scheduled transaction.
     * @param scheduledTransaction The scheduled transactin to be added.
     * @return The index of the added scheduled transaction.
     */
    public int addScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        scheduledTransactions.add(scheduledTransaction);
        return scheduledTransactions.size() - 1; // return index (= last element)
    }

    /**
     * 
     * @param t 
     * @param updateBalances 
     * @return 
     */
    public int addTransaction(TransactionBase t, boolean updateBalances) {
        if (t instanceof Transfer) {
            return addTransfer((Transfer) t, updateBalances);
        } else if (t instanceof SplitPayment) {
            return addSplitPayment((SplitPayment) t, updateBalances);
        } else if (t instanceof SimplePayment) {
            return addSimplePayment((SimplePayment) t, updateBalances);
        }
        throw new AssertionError(String.format("Invalid transaction type: %1$",
                t.getClass().getName()));
    }

    /**
     * 
     * @param index 
     * @return 
     */
    public TransactionBase removeTransaction(int index) {
        TransactionBase t = transactions.get(index);
        transactions.remove(index);
        updateBalances(index);
        return t;
    }

    /**
     * 
     * @param index 
     * @return 
     */
    public ScheduledTransaction removeScheduledTransaction(int index) {
        ScheduledTransaction st = scheduledTransactions.get(index);
        scheduledTransactions.remove(index);
        return st;
    }

    /**
     * Updates the current balance of all transactions in this account.
     */
    public void updateBalances() {
        if (transactions.size() == 0) {
            return;
        }
        updateBalances(0);
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * 
     * @return 
     */
    public Bank getBank() {
        return bank;
    }

    /**
     * 
     * @param bank 
     */
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    /**
     * 
     * @return 
     */
    public String getNote() {
        return note;
    }

    /**
     * 
     * @param note 
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 
     * @return 
     */
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    /**
     * 
     * @param accountNumber 
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 
     * @return 
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 
     * @param initialBalance 
     */
    public void setInitialBalance(BigDecimal initialBalance) {
        if ((this.initialBalance == null) || (this.initialBalance.compareTo(initialBalance) != 0)) {
            this.initialBalance = initialBalance;
            updateBalances();
        }
    }

    /**
     * 
     * @return 
     */
    public int getNumberOfTransactions() {
        return transactions.size();
    }

    /**
     * 
     * @param index 
     * @return 
     */
    public TransactionBase getTransaction(int index) {
        return transactions.get(index);
    }

    /**
     * 
     * @return 
     */
    public BigDecimal getCurrentBalance() {
        if (transactions.size() > 0) {
            return transactions.get(transactions.size() - 1).getBalance();
        } else {
            return initialBalance;
        }
    }

    /**
     * 
     * @param date 
     * @return 
     */
    public BigDecimal getBalance(Calendar date) {
        TransactionBase lastTransactionBefore = null;
        for (TransactionBase tb : transactions) {
            if (!tb.getValueDate().after(date)) {
                lastTransactionBefore = tb;
            }
        }
        return lastTransactionBefore != null ? lastTransactionBefore.getBalance() : getInitialBalance();
    }

    // should be called when the transaction date or amount has changed
    // to update the balances of transactions
    /**
     * 
     * @param index 
     * @return 
     */
    public int updateTransaction(int index) {
        TransactionBase t = removeTransaction(index);
        return addTransactionBase(t, true);
    }

    private void updateBalances(int fromIndex) {
        if (fromIndex >= transactions.size()) {
            return;
        }
        BigDecimal balance = fromIndex == 0 ? initialBalance : transactions.get(fromIndex - 1).getBalance();
        for (int i = fromIndex; i < transactions.size(); i++) {
            TransactionBase t = transactions.get(i);
            balance = balance.add(t.getAmount());
            t.setBalance(balance);
        }
    }
    /**
     * Holds value of property holderName.
     */
    private String holderName;

    /**
     * Getter for property holderName.
     * @return Value of property holderName.
     */
    public String getHolderName() {
        return this.holderName;
    }

    /**
     * Setter for property holderName.
     * @param holderName New value of property holderName.
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    /**
     * Holds value of property holderAddress.
     */
    private String holderAddress;

    /**
     * Getter for property holderAddress.
     * @return Value of property holderAddress.
     */
    public String getHolderAddress() {
        return this.holderAddress;
    }

    /**
     * Setter for property holderAddress.
     * @param holderAddress New value of property holderAddress.
     */
    public void setHolderAddress(String holderAddress) {
        this.holderAddress = holderAddress;
    }
    /**
     * Holds value of property accountNumberKey.
     */
    private String accountNumberKey;

    /**
     * Getter for property accountNumberKey.
     * @return Value of property accountNumberKey.
     */
    public String getAccountNumberKey() {
        return this.accountNumberKey;
    }

    /**
     * Setter for property accountNumberKey.
     * @param accountNumberKey New value of property accountNumberKey.
     */
    public void setAccountNumberKey(String accountNumberKey) {
        this.accountNumberKey = accountNumberKey;
    }
    /**
     * Holds value of property lastReconciliationBalance.
     */
    private BigDecimal lastReconciliationBalance;

    /**
     * Getter for property lastReconciliationBalance.
     * @return Value of property lastReconciliationBalance.
     */
    public BigDecimal getLastReconciliationBalance() {

        return this.lastReconciliationBalance;
    }

    /**
     * Setter for property lastReconciliationBalance.
     * @param lastReconciliationBalance New value of property lastReconciliationBalance.
     */
    public void setLastReconciliationBalance(BigDecimal lastReconciliationBalance) {

        this.lastReconciliationBalance = lastReconciliationBalance;
    }
    /**
     * Holds value of property lastReconciliationDate.
     */
    private Calendar lastReconciliationDate;

    /**
     * Getter for property lastReconciliationDate.
     * @return Value of property lastReconciliationDate.
     */
    public Calendar getLastReconciliationDate() {

        return this.lastReconciliationDate;
    }

    /**
     * Setter for property lastReconciliationDate.
     * @param lastReconciliationDate New value of property lastReconciliationDate.
     */
    public void setLastReconciliationDate(Calendar lastReconciliationDate) {

        this.lastReconciliationDate = lastReconciliationDate;
    }
    /**
     * Holds value of property pendingReconciliationDate.
     */
    private Calendar pendingReconciliationDate;

    /**
     * Getter for property pendingReconciliationDate.
     * @return Value of property pendingReconciliationDate.
     */
    public Calendar getPendingReconciliationDate() {

        return this.pendingReconciliationDate;
    }

    /**
     * Setter for property pendingReconciliationDate.
     * @param pendingReconciliationDate New value of property pendingReconciliationDate.
     */
    public void setPendingReconciliationDate(Calendar pendingReconciliationDate) {

        this.pendingReconciliationDate = pendingReconciliationDate;
    }
    /**
     * Holds value of property pendingReconciliationBalance.
     */
    private BigDecimal pendingReconciliationBalance;

    /**
     * Getter for property pendingReconciliationBalance.
     * @return Value of property pendingReconciliationBalance.
     */
    public BigDecimal getPendingReconciliationBalance() {

        return this.pendingReconciliationBalance;
    }

    /**
     * Setter for property pendingReconciliationBalance.
     * @param pendingReconciliationBalance New value of property pendingReconciliationBalance.
     */
    public void setPendingReconciliationBalance(BigDecimal pendingReconciliationBalance) {

        this.pendingReconciliationBalance = pendingReconciliationBalance;
    }
    /**
     * Holds value of property pendingReconciliation.
     */
    private boolean pendingReconciliation;

    /**
     * Getter for property pendingReconciliation.
     * @return Value of property pendingReconciliation.
     */
    public boolean isPendingReconciliation() {

        return this.pendingReconciliation;
    }

    /**
     * Setter for property pendingReconciliation.
     * @param pendingReconciliation New value of property pendingReconciliation.
     */
    public void setPendingReconciliation(boolean pendingReconciliation) {

        this.pendingReconciliation = pendingReconciliation;
    }

    /**
     * 
     * @return 
     */
    public BigDecimal getMarkedAmount() {
        BigDecimal markedAmount = BigDecimal.ZERO;
        for (TransactionBase tb : transactions) {
            if (tb.getReconciliationState() == TransactionBase.ReconciliationState.MARKED) {
                markedAmount = markedAmount.add(tb.getAmount());
            }
        }
        return markedAmount;
    }
}
