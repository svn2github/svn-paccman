/*
 * Scheduler.java
 *
 * Created on 3 décembre 2005, 09:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import org.paccman.controller.AccountController;
import org.paccman.controller.ControllerManager;
import org.paccman.controller.DocumentController;
import org.paccman.controller.ScheduledTransactionController;
import org.paccman.controller.TransactionBaseController;
import org.paccman.paccman.Account;
import org.paccman.paccman.Document;
import org.paccman.paccman.ScheduledTransaction;

/**
 *
 * @author joao
 */
public class Scheduler {
    
    /** Creates a new instance of Scheduler */
    public Scheduler() {
    }
    
    // Get all the sscheduled transaction that have come to expiration in the account
    private static void getScheduledTransaction(ArrayList<ExpiredScheduledTransaction> expiredSchedTransactionList,
            ScheduledTransaction scheduledTransaction, AccountController account, Calendar date) {
        Calendar actualDate = (Calendar)date.clone();
        actualDate.add(Calendar.DAY_OF_MONTH, scheduledTransaction.getScheduleDays());
        
        Calendar nextOccurence = (Calendar)scheduledTransaction.getNextOccurence().clone();
        if (nextOccurence.compareTo(actualDate) <= 0) {
            
            // New expired scheduled transaction
            ExpiredScheduledTransaction est = new ExpiredScheduledTransaction();
            est.setScheduledTransaction((ScheduledTransactionController) ControllerManager.getController(scheduledTransaction));
            est.setOccurence((Calendar)nextOccurence.clone());
            est.setAccount(account);
            
            // Add to the list
            expiredSchedTransactionList.add(est);
            
            // Compute next occurence date
            computeNextOccurence(nextOccurence, scheduledTransaction.getPeriodUnit(), 
                    scheduledTransaction.getPeriod());
            
        }
    }
    
    public static void computeNextOccurence(Calendar date, ScheduledTransaction.PeriodUnit unit, int period) {
        int field = 0;
        switch (unit) {
            case DAY  : field = Calendar.DATE; break;
            case WEEK : field = Calendar.WEEK_OF_YEAR; break;
            case MONTH: field = Calendar.MONTH; break;
            case YEAR : field = Calendar.YEAR; break;
        }
        date.add(field, period);
    }
    
    // Get all the scheduled transactions that have come to expiration in the account
    private static void getScheduledTransaction(ArrayList<ExpiredScheduledTransaction> expiredSchedTransactionList,
            AccountController account, Calendar date) {
        for (ScheduledTransaction st: account.getAccount().getScheduledTransactions()) {
            getScheduledTransaction(expiredSchedTransactionList, st, account, date);
        }
    }
    
    // Get all the scheduled transactions that have come to expiration in the document
    public static void getScheduledTransaction(ArrayList<ExpiredScheduledTransaction> expiredSchedTransactionList,
            DocumentController controller, Calendar date) {
        Document document = controller.getDocument();
        
        expiredSchedTransactionList.clear();
        
        for (Account acc: document.getAccounts()) {
            getScheduledTransaction(expiredSchedTransactionList, (AccountController)ControllerManager.getController(acc), date);
        }
    }
    
}
