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

package org.paccman.controller;

import org.paccman.paccman.ScheduledTransaction;
import org.paccman.paccman.SimplePayment;
import org.paccman.paccman.SplitPayment;
import org.paccman.paccman.Transfer;

/**
 *
 * @author joao
 */
public class ScheduledTransactionController extends Controller {
    
    /** Creates a new instance of DocumentController */
    public ScheduledTransactionController() {
        super(new ScheduledTransaction());
    }
    
    /**
     * 
     * @return
     */
    public ScheduledTransaction getScheduledTransaction() {
        return (ScheduledTransaction)paccObj;
    }
    
    /**
     * 
     * @return
     */
    public TransactionBaseController newInstance() {
        
        TransactionBaseController tbc = null;
        
        // Creation and initialisation of transaction data from scheduled transaction
        if (getScheduledTransaction().getTransactionBase() instanceof SplitPayment) {
            SplitPaymentController spc = new SplitPaymentController();
            getScheduledTransaction().getTransactionBase().copyTo(spc.getPayment());
            tbc = spc;
        } else if (getScheduledTransaction().getTransactionBase() instanceof SimplePayment) {
            SimplePaymentController spc = new SimplePaymentController();
            getScheduledTransaction().getTransactionBase().copyTo(spc.getPayment());
            tbc = spc;
        } else if (getScheduledTransaction().getTransactionBase() instanceof Transfer) {
            TransferController tc = new TransferController();
            getScheduledTransaction().getTransactionBase().copyTo(tc.getTransfer());
            tbc = tc;
        }
        
        // Instance data initialisation
        tbc.getTransactionBase().setTransactionDate(getScheduledTransaction().getNextOccurence());
        tbc.getTransactionBase().setValueDate(getScheduledTransaction().getNextOccurence());
        
        return tbc;
    }
}
