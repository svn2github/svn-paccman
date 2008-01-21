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
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author joao
 */
public class Document extends PaccmanObject {
    
    private String title;
    private Calendar creationDate;
    private Calendar lastUpdateDate;
    
    /**
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
            
    /**
     * 
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @return
     */
    public Calendar getCreationDate() {
        return creationDate;
    }

    /**
     * 
     * @param creationDate
     */
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * 
     * @return
     */
    public Calendar getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * 
     * @param lastUpdateDate
     */
    public void setLastUpdateDate(Calendar lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
            
    /** Creates a new instance of Document */
    public Document() {
    }
    
    // -- Accounts
    
    Vector<Account>     accounts     = new Vector<Account>   ();

    /**
     * 
     * @param account
     * @return
     */
    public int addAccount(Account account) {
        accounts.add(account);
        return accounts.size() - 1;
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public Account getAccount(int index) {
        return accounts.get(index);
    }
    
    /**
     * 
     * @return
     */
    public Vector<Account> getAccounts() {
        return accounts;
    }
    
    /**
     * 
     * @return
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Account getAccount(String name) {
        for (Account account: accounts) {
            if (account.getName().equals(name)) {
                return account;
            }
        }
        return null;
    }
    
    // -- Categories
    
    ArrayList<Category>    categories   = new ArrayList<Category>  ();
    
    /**
     * 
     * @param category
     * @return
     */
    public int addCategory(Category category) {
        categories.add(category);
        return categories.size() - 1;
    }

    /**
     * 
     * @param index
     * @return
     */
    public Category getCategory(int index) {
        return categories.get(index);
    }
    
    /**
     * 
     * @return
     */
    public int getNumberOfCategories() {
        return categories.size();
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Category> getCategories() {
        return categories;
    }
    
    // -- Payment methods
    
    ArrayList<PaymentMethod>    paymentMethods = new ArrayList<PaymentMethod>  ();
    
    /**
     * 
     * @param paymentMethod
     * @return
     */
    public int addPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethods.add(paymentMethod);
        return paymentMethods.size() - 1;
    }

    /**
     * 
     * @param index
     * @return
     */
    public PaymentMethod getPaymentMethod(int index) {
        return paymentMethods.get(index);
    }
    
    /**
     * 
     * @return
     */
    public int getNumberOfPaymentMethods() {
        return paymentMethods.size();
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
    
    
    // -- Banks 
    
    Vector<Bank>        banks        = new Vector<Bank>      ();
    /**
     * 
     * @param bank
     * @return
     */
    public int addBank(Bank bank) {
        banks.add(bank);
        return banks.size() - 1;
    }

    /**
     * 
     * @return
     */
    public int getNumberOfBanks() {
        return banks.size();
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public Bank getBank(int index) {
        return banks.get(index);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Bank getBank(String name) {
        for (Bank bank: banks) {
            if (bank.getName().equals(name)) {
                return bank;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Category getCategory(String name) {
        for (Category category: categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param categoryName
     * @param subCategoryName
     * @return
     */
    public SubCategory getCategory(String categoryName, String subCategoryName) {
        Category category = getCategory(categoryName);
        if (category != null) {
            return category.getSubCategory(subCategoryName);
        }
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public PaymentMethod getPaymentType(String name) {
        for (PaymentMethod payment: paymentMethods) {
            if (payment.getName().equals(name)) {
                return payment;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Payee getPayee(String name) {
        for (Payee payee: payees) {
            if (payee.getName().equals(name)) {
                return payee;
            }
        }
        return null;
    }
    
    /**
     * 
     * @return
     */
    public Vector<Bank> getBanks() {
        return banks;
    }
    
  
    // -- Payees
    
    Vector<Payee>  payees = new Vector<Payee>();
    /**
     * 
     * @param payee
     * @return
     */
    public int addPayee(Payee payee) {
        payees.add(payee);
        return payees.size() - 1;
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public Payee getPayee(int index) {
        return payees.get(index);
    }
    
    /**
     * 
     * @return
     */
    public int getNumberOfPayees() {
        return payees.size();
    }
    
    /**
     * 
     * @return
     */
    public Vector<Payee> getPayees() {
        return payees;
    }
    
    // -- General
    
    /**
     * 
     * @return
     */
    public BigDecimal getTotalBalance() {
        BigDecimal total = new BigDecimal(0);
        for (Account account: accounts) {
            total = total.add(account.getCurrentBalance());
        }
        return total;
    }
    
    /**
     * 
     * @param date
     * @return
     */
    public BigDecimal getTotalBalance(Calendar date) {
        BigDecimal total = new BigDecimal(0);
        for (Account account: accounts) {
            total = total.add(account.getBalance(date));
        }
        return total;
    }

}
