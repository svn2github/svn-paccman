/*
 * ExpiredScheduledTransaction.java
 *
 * Created on 3 décembre 2005, 09:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.scheduling;

import java.util.Calendar;
import org.paccman.controller.AccountController;
import org.paccman.controller.Controller;
import org.paccman.controller.PaccmanView;
import org.paccman.controller.ScheduledTransactionController;
import org.paccman.paccman.PaccmanObject;

/**
 *
 * @author joao
 */
public class ExpiredScheduledTransaction extends Controller {
    
    /** Creates a new instance of ExpiredScheduledTransaction */
    public ExpiredScheduledTransaction() {
        super(null);
    }

    /**
     * Holds value of property scheduledTransaction.
     */
    private ScheduledTransactionController scheduledTransaction;

    /**
     * Getter for property scheduledTransaction.
     * @return Value of property scheduledTransaction.
     */
    public ScheduledTransactionController getScheduledTransaction() {
        return this.scheduledTransaction;
    }

    /**
     * Setter for property scheduledTransaction.
     * @param scheduledTransaction New value of property scheduledTransaction.
     */
    public void setScheduledTransaction(ScheduledTransactionController scheduledTransaction) {
        this.scheduledTransaction = scheduledTransaction;
    }

    /**
     * Holds value of property occurence.
     */
    private Calendar occurence;

    /**
     * Getter for property occurence.
     * @return Value of property occurence.
     */
    public Calendar getOccurence() {
        return this.occurence;
    }

    /**
     * Setter for property occurence.
     * @param occurence New value of property occurence.
     */
    public void setOccurence(Calendar occurence) {
        this.occurence = occurence;
    }

    /**
     * Holds value of property account.
     */
    private AccountController account;

    /**
     * Getter for property account.
     * @return Value of property account.
     */
    public AccountController getAccount() {
        return this.account;
    }

    /**
     * Setter for property account.
     * @param account New value of property account.
     */
    public void setAccount(AccountController account) {
        this.account = account;
    }

    /**
     * Holds value of property registered.
     */
    private boolean registered = false;

    /**
     * Getter for property registered.
     * @return Value of property registered.
     */
    public boolean isRegistered() {
        return this.registered;
    }

    /**
     * Setter for property registered.
     * @param registered New value of property registered.
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

}
