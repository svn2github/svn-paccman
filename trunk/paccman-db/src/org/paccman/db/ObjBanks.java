/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
@Entity
@Table(name = "OBJ_BANKS")
@NamedQueries({@NamedQuery(name = "ObjBanks.findAll", query = "SELECT o FROM ObjBanks o"), @NamedQuery(name = "ObjBanks.findByBankId", query = "SELECT o FROM ObjBanks o WHERE o.bankId = :bankId"), @NamedQuery(name = "ObjBanks.findByName", query = "SELECT o FROM ObjBanks o WHERE o.name = :name"), @NamedQuery(name = "ObjBanks.findByAgency", query = "SELECT o FROM ObjBanks o WHERE o.agency = :agency"), @NamedQuery(name = "ObjBanks.findByAddressNumber", query = "SELECT o FROM ObjBanks o WHERE o.addressNumber = :addressNumber"), @NamedQuery(name = "ObjBanks.findByAddressLine1", query = "SELECT o FROM ObjBanks o WHERE o.addressLine1 = :addressLine1"), @NamedQuery(name = "ObjBanks.findByAddressLine2", query = "SELECT o FROM ObjBanks o WHERE o.addressLine2 = :addressLine2"), @NamedQuery(name = "ObjBanks.findByAddressCity", query = "SELECT o FROM ObjBanks o WHERE o.addressCity = :addressCity"), @NamedQuery(name = "ObjBanks.findByAddressZip", query = "SELECT o FROM ObjBanks o WHERE o.addressZip = :addressZip"), @NamedQuery(name = "ObjBanks.findByAddressCountry", query = "SELECT o FROM ObjBanks o WHERE o.addressCountry = :addressCountry"), @NamedQuery(name = "ObjBanks.findByTelephone", query = "SELECT o FROM ObjBanks o WHERE o.telephone = :telephone"), @NamedQuery(name = "ObjBanks.findByFax", query = "SELECT o FROM ObjBanks o WHERE o.fax = :fax"), @NamedQuery(name = "ObjBanks.findByInternet", query = "SELECT o FROM ObjBanks o WHERE o.internet = :internet"), @NamedQuery(name = "ObjBanks.findByEmail", query = "SELECT o FROM ObjBanks o WHERE o.email = :email")})
public class ObjBanks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BANK_ID")
    private Long bankId;

    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    @Column(name = "AGENCY")
    private String agency;

    @Column(name = "ADDRESS_NUMBER")
    private String addressNumber;

    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;

    @Column(name = "ADDRESS_CITY")
    private String addressCity;

    @Column(name = "ADDRESS_ZIP")
    private String addressZip;

    @Column(name = "ADDRESS_COUNTRY")
    private String addressCountry;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "INTERNET")
    private String internet;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankId")
    private Collection<ObjAccounts> objAccountsCollection;

    public ObjBanks() {
    }

    public ObjBanks(Long bankId) {
        this.bankId = bankId;
    }

    public ObjBanks(Long bankId, String name) {
        this.bankId = bankId;
        this.name = name;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(String addressZip) {
        this.addressZip = addressZip;
    }

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<ObjAccounts> getObjAccountsCollection() {
        return objAccountsCollection;
    }

    public void setObjAccountsCollection(Collection<ObjAccounts> objAccountsCollection) {
        this.objAccountsCollection = objAccountsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankId != null ? bankId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjBanks)) {
            return false;
        }
        ObjBanks other = (ObjBanks) object;
        if ((this.bankId == null && other.bankId != null) || (this.bankId != null && !this.bankId.equals(other.bankId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.paccman.db.ObjBanks[bankId=" + bankId + "]";
    }

}
