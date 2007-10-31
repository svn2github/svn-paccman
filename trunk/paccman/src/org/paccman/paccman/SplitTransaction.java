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

/**
 *
 * @author joao
 */
public class SplitTransaction extends PaccmanObject {
    
    BigDecimal  amount  ;
    CategoryBase    category;
    
    /**
     * Creates a new instance of SplitTransaction
     */
    public SplitTransaction() {
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CategoryBase getCategory() {
        return category;
    }
    
    public void setCategory(CategoryBase category) {
        this.category = category;
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

}
