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

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author joao
 */
public class Category extends CategoryBase {
    
    private ArrayList<SubCategory> subCategories = new ArrayList<SubCategory>();
    
    /** Creates a new instance of Category */
    public Category() {
    }
    
    public void addSubCategory(SubCategory subCategory) {
        subCategories.add(subCategory);
    }
    
    public ArrayList<SubCategory> getSubCategories() {
        return subCategories;
    }
    
    public SubCategory getSubCategoory(String name) {
        for (SubCategory sc: subCategories) {
            if (sc.getName().equals(name)) {
                return sc;
            }
        }
        return null;
    }
    
    /**
     * Holds value of property income.
     */
    private boolean income;

    /**
     * Getter for property income.
     * @return Value of property income.
     */
    public boolean isIncome() {
        return this.income;
    }

    /**
     * Setter for property income.
     * @param income New value of property income.
     */
    public void setIncome(boolean income) {
        this.income = income;
    }

}
