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
public class Payee extends PaccmanObject {
    
    private long payeeId;

    /**
     * Gets the ID of this payee.
     * @return This payee's ID.
     */
    public long getPayeeId() {
        return payeeId;
    }

    /**
     * Sets the ID of this payee.
     * @param payeeId The ID to be set.
     */
    public void setPayeeId(long payeeId) {
        this.payeeId = payeeId;
    }
    
    private String name;
    
    /** Creates a new instance of Payee */
    public Payee() {
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
    
}
