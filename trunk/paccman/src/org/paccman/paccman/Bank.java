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
public class Bank extends PaccmanObject {
    
    private String name;
    
    /** Creates a new instance of Bank */
    public Bank() {
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

    /**
     * Holds value of property addressNumber.
     */
    private String addressNumber;

    /**
     * Getter for property addressNumber.
     * @return Value of property addressNumber.
     */
    public String getAddressNumber() {
        return this.addressNumber;
    }

    /**
     * Setter for property addressNumber.
     * @param addressNumber New value of property addressNumber.
     */
    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    /**
     * Holds value of property addressLine1.
     */
    private String addressLine1;

    /**
     * Getter for property adressLine1.
     * @return Value of property adressLine1.
     */
    public String getAddressLine1()  {
        return this.addressLine1;
    }

    /**
     * Setter for property adressLine1.
     * 
     * @param addressLine1 
     */
    public void setAddressLine1(String addressLine1)  {
        this.addressLine1 = addressLine1;
    }

    /**
     * Holds value of property addressLine2.
     */
    private String addressLine2;

    /**
     * Getter for property addressLine2.
     * @return Value of property addressLine2.
     */
    public String getAddressLine2() {
        return this.addressLine2;
    }

    /**
     * Setter for property addressLine2.
     * @param addressLine2 New value of property addressLine2.
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * Holds value of property telephone.
     */
    private String telephone;

    /**
     * Getter for property telephone.
     * @return Value of property telephone.
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * Setter for property telephone.
     * @param telephone New value of property telephone.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Holds value of property fax.
     */
    private String fax;

    /**
     * Getter for property fax.
     * @return Value of property fax.
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * Setter for property fax.
     * @param fax New value of property fax.
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Holds value of property addressZip.
     */
    private String addressZip;

    /**
     * Getter for property addressZip.
     * @return Value of property addressZip.
     */
    public String getAddressZip() {
        return this.addressZip;
    }

    /**
     * Setter for property addressZip.
     * @param addressZip New value of property addressZip.
     */
    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    /**
     * Holds value of property addressCity.
     */
    private String addressCity;

    /**
     * Getter for property addressCity.
     * @return Value of property addressCity.
     */
    public String getAddressCity() {
        return this.addressCity;
    }

    /**
     * Setter for property addressCity.
     * @param addressCity New value of property addressCity.
     */
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    /**
     * Holds value of property internet.
     */
    private String internet;

    /**
     * Getter for property internet.
     * @return Value of property internet.
     */
    public String getInternet() {
        return this.internet;
    }

    /**
     * Setter for property internet.
     * @param internet New value of property internet.
     */
    public void setInternet(String internet) {
        this.internet = internet;
    }

    /**
     * Holds value of property addressCountry.
     */
    private String addressCountry;

    /**
     * Getter for property addressCountry.
     * @return Value of property addressCountry.
     */
    public String getAddressCountry() {
        return this.addressCountry;
    }

    /**
     * Setter for property addressCountry.
     * @param addressCountry New value of property addressCountry.
     */
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    /**
     * Holds value of property agency.
     */
    private String agency;

    /**
     * Getter for property agency.
     * @return Value of property agency.
     */
    public String getAgency() {
        return this.agency;
    }

    /**
     * Setter for property agency.
     * @param agency New value of property agency.
     */
    public void setAgency(String agency) {
        this.agency = agency;
    }

    /**
     * Holds value of property email.
     */
    private String email;

    /**
     * Getter for property email.
     * @return Value of property email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for property email.
     * @param email New value of property email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}
