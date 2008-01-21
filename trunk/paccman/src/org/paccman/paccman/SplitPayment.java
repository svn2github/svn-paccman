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

/**
 *
 * @author joao
 */
public class SplitPayment extends Payment {
    
    ArrayList<SplitTransaction> transactionList = new ArrayList<SplitTransaction>();
    
    /** Creates a new instance of SplitPayment */
    public SplitPayment() {
    }
    
    /**
     * 
     * @return
     */
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (SplitTransaction t: transactionList) {
            total = total.add(t.getAmount());
        }
        return total;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<SplitTransaction> getSplitTransactions() {
        return transactionList;
    }
    
    /**
     * 
     * @param transaction
     */
    public void addSplitTransaction(SplitTransaction transaction) {
        transactionList.add(transaction);
    }

    /**
     * 
     */
    public void clearSplitTransactions() {
        transactionList.clear();
    }
    
    @Override
    public void copyTo(TransactionBase transaction) {
        assert transaction instanceof SplitPayment;
        
        SplitPayment destTransaction = (SplitPayment)transaction;
        super.copyTo(transaction);
        for (SplitTransaction st: transactionList) {
            // Make a clone of the split transactions
            SplitTransaction copySt = new SplitTransaction();
            copySt.setAmount(st.getAmount().add(BigDecimal.ZERO));  
            copySt.setCategory(st.getCategory());
            copySt.setNote(new String(st.getNote()));
            destTransaction.addSplitTransaction(copySt);
        }
    }
    
}
