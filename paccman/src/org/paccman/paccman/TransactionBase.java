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

package org.paccman.paccman;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 *
 * @author joao
 */
public abstract class TransactionBase extends PaccmanObject {
    
    protected BigDecimal amount ;
    protected Calendar   valueDate   ;
    protected BigDecimal balance;
    
    /** Creates a new instance of TransactionBase */
    public TransactionBase() {
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public Calendar getValueDate() {

        return valueDate;
    }
    
    public void setValueDate(Calendar date) {

        this.valueDate = date;
    }
    
    public boolean isWithdrawal() {
        return amount.signum() < 0;
    }
    
    public boolean isDeposit() {
        return ! isWithdrawal();
    }
    
    /**
     * Holds value of property note.
     */
    private String note;

    /**
     * Getter for property note.
     * @return Value of property note.
     */
    public String getNote() {

        return this.note;
    }

    /**
     * Setter for property note.
     * @param note New value of property note.
     */
    public void setNote(String note) {

        this.note = note;
    }

    /**
     * Holds value of property label.
     */
    private String label;

    /**
     * Getter for property label.
     * @return Value of property label.
     */
    public String getLabel() {

        return this.label;
    }

    /**
     * Setter for property label.
     * @param label New value of property label.
     */
    public void setLabel(String label) {

        this.label = label;
    }

    public enum ReconciliationState {
	UNRECONCILED,
	MARKED      ,
	RECONCILED  
    };

    /**
     * Holds value of property reconciliationState.
     */
    private ReconciliationState reconciliationState = ReconciliationState.UNRECONCILED;

    /**
     * Getter for property reconciliationState.
     * @return Value of property reconciliationState.
     */
    public ReconciliationState getReconciliationState() {
        return this.reconciliationState;
    }

    /**
     * Setter for property reconciliationState.
     * @param reconciliationState New value of property reconciliationState.
     */
    public void setReconciliationState(ReconciliationState reconciliationState) {
        this.reconciliationState = reconciliationState;
    }

    /**
     * Holds value of property reconciliationDate.
     */
    private Calendar reconciliationDate;

    /**
     * Getter for property reconciliationDate.
     * @return Value of property reconciliationDate.
     */
    public Calendar getReconciliationDate() {
        return this.reconciliationDate;
    }

    /**
     * Setter for property reconciliationDate.
     * @param reconciliationDate New value of property reconciliationDate.
     */
    public void setReconciliationDate(Calendar reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    /**
     * Holds value of property transactionDate.
     */
    private Calendar transactionDate;

    /**
     * Getter for property transactionDate.
     * @return Value of property transactionDate.
     */
    public Calendar getTransactionDate() {

        return this.transactionDate;
    }

    /**
     * Setter for property transactionDate.
     * @param transactionDate New value of property transactionDate.
     */
    public void setTransactionDate(Calendar transactionDate) {

        this.transactionDate = transactionDate;
    }

    public void copyTo(TransactionBase transaction) {
        transaction.setAmount(this.amount);
        transaction.setBalance(this.balance);
        transaction.setLabel(this.label);
        transaction.setNote(this.note);
        transaction.setReconciliationDate(this.reconciliationDate);
        transaction.setReconciliationState(this.reconciliationState);
        transaction.setTransactionDate(this.transactionDate);
        transaction.setValueDate(this.valueDate);
    }
}

