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

import java.math.BigDecimal;
import org.paccman.paccman.Account;
import org.paccman.paccman.Bank;

/**
 *
 * @author joao
 */
public class AccountController extends Controller {
    
    /** Creates a new instance of DocumentController */
    public AccountController() {
        super(new Account());
    }
    
    public Account getAccount() {
        return (Account)paccObj;
    }
    
    public String toString() {
        return getAccount().getName();
    }
    
}
