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

import org.paccman.paccman.PaccmanObject;
import org.paccman.paccman.TransactionBase;

/**
 *
 * @author joao
 */
public class TransactionBaseController extends Controller {
    
    /** Creates a new instance of TransactionController
     * @param paccObj 
     */
    public TransactionBaseController(PaccmanObject paccObj) {
        super(paccObj);
    }
    
    /**
     * 
     * @return
     */
    public TransactionBase getTransactionBase() {
        return (TransactionBase)paccObj;
    }
    
}
