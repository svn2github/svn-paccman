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

/**
 *
 * @author joao
 */
public class Transfer extends TransactionBase {
    
    Account   toFromAccount;
    
    /** Creates a new instance of Transfer */
    public Transfer() {
    }
    
    /**
     * 
     * @return
     */
    public Account getToFromAccount() {
        return toFromAccount;
    }
    
    /**
     * 
     * @param toFromAccount
     */
    public void setToFromAccount(Account toFromAccount) {
        this.toFromAccount = toFromAccount;
    }
    
    /**
     * 
     * @return true if it is a transfer from <i>this</i> account to another
     */
    public boolean isSource() {
        return isWithdrawal();
    }
    
    @Override
    public void copyTo(TransactionBase transaction) {
        assert transaction instanceof Transfer;
        
        Transfer destTransaction = (Transfer)transaction;
        super.copyTo(transaction);
        destTransaction.setToFromAccount(this.toFromAccount);
    }
}
