/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
@Entity
@Table(name = "OBJ_ACCOUNTS")
@NamedQueries({@NamedQuery(name = "ObjAccounts.findAll", query = "SELECT o FROM ObjAccounts o"), @NamedQuery(name = "ObjAccounts.findByAccountId", query = "SELECT o FROM ObjAccounts o WHERE o.accountId = :accountId"), @NamedQuery(name = "ObjAccounts.findByName", query = "SELECT o FROM ObjAccounts o WHERE o.name = :name"), @NamedQuery(name = "ObjAccounts.findByInitialBalance", query = "SELECT o FROM ObjAccounts o WHERE o.initialBalance = :initialBalance"), @NamedQuery(name = "ObjAccounts.findByAccountNumber", query = "SELECT o FROM ObjAccounts o WHERE o.accountNumber = :accountNumber"), @NamedQuery(name = "ObjAccounts.findByAccountNumberKey", query = "SELECT o FROM ObjAccounts o WHERE o.accountNumberKey = :accountNumberKey"), @NamedQuery(name = "ObjAccounts.findByNote", query = "SELECT o FROM ObjAccounts o WHERE o.note = :note"), @NamedQuery(name = "ObjAccounts.findByHolderName", query = "SELECT o FROM ObjAccounts o WHERE o.holderName = :holderName"), @NamedQuery(name = "ObjAccounts.findByHolderAddress", query = "SELECT o FROM ObjAccounts o WHERE o.holderAddress = :holderAddress"), @NamedQuery(name = "ObjAccounts.findByLastReconciliationDate", query = "SELECT o FROM ObjAccounts o WHERE o.lastReconciliationDate = :lastReconciliationDate"), @NamedQuery(name = "ObjAccounts.findByLastReconciliationBalance", query = "SELECT o FROM ObjAccounts o WHERE o.lastReconciliationBalance = :lastReconciliationBalance"), @NamedQuery(name = "ObjAccounts.findByPendingReconciliation", query = "SELECT o FROM ObjAccounts o WHERE o.pendingReconciliation = :pendingReconciliation"), @NamedQuery(name = "ObjAccounts.findByPendingReconciliationDate", query = "SELECT o FROM ObjAccounts o WHERE o.pendingReconciliationDate = :pendingReconciliationDate"), @NamedQuery(name = "ObjAccounts.findByPendingReconciliationBalance", query = "SELECT o FROM ObjAccounts o WHERE o.pendingReconciliationBalance = :pendingReconciliationBalance")})
public class ObjAccounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "NAME")
    private String name;

    @Basic(optional = false)
    @Column(name = "INITIAL_BALANCE")
    private BigDecimal initialBalance;

    @Basic(optional = false)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "ACCOUNT_NUMBER_KEY")
    private String accountNumberKey;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "HOLDER_NAME")
    private String holderName;

    @Column(name = "HOLDER_ADDRESS")
    private String holderAddress;

    @Column(name = "LAST_RECONCILIATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReconciliationDate;

    @Column(name = "LAST_RECONCILIATION_BALANCE")
    private BigDecimal lastReconciliationBalance;

    @Column(name = "PENDING_RECONCILIATION")
    private Short pendingReconciliation;

    @Column(name = "PENDING_RECONCILIATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pendingReconciliationDate;

    @Column(name = "PENDING_RECONCILIATION_BALANCE")
    private BigDecimal pendingReconciliationBalance;

    @JoinColumn(name = "BANK_ID", referencedColumnName = "BANK_ID")
    @ManyToOne(optional = false)
    private ObjBanks bankId;

    public ObjAccounts() {
    }

    public ObjAccounts(Long accountId) {
        this.accountId = accountId;
    }

    public ObjAccounts(Long accountId, BigDecimal initialBalance, String accountNumber) {
        this.accountId = accountId;
        this.initialBalance = initialBalance;
        this.accountNumber = accountNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumberKey() {
        return accountNumberKey;
    }

    public void setAccountNumberKey(String accountNumberKey) {
        this.accountNumberKey = accountNumberKey;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getHolderAddress() {
        return holderAddress;
    }

    public void setHolderAddress(String holderAddress) {
        this.holderAddress = holderAddress;
    }

    public Date getLastReconciliationDate() {
        return lastReconciliationDate;
    }

    public void setLastReconciliationDate(Date lastReconciliationDate) {
        this.lastReconciliationDate = lastReconciliationDate;
    }

    public BigDecimal getLastReconciliationBalance() {
        return lastReconciliationBalance;
    }

    public void setLastReconciliationBalance(BigDecimal lastReconciliationBalance) {
        this.lastReconciliationBalance = lastReconciliationBalance;
    }

    public Short getPendingReconciliation() {
        return pendingReconciliation;
    }

    public void setPendingReconciliation(Short pendingReconciliation) {
        this.pendingReconciliation = pendingReconciliation;
    }

    public Date getPendingReconciliationDate() {
        return pendingReconciliationDate;
    }

    public void setPendingReconciliationDate(Date pendingReconciliationDate) {
        this.pendingReconciliationDate = pendingReconciliationDate;
    }

    public BigDecimal getPendingReconciliationBalance() {
        return pendingReconciliationBalance;
    }

    public void setPendingReconciliationBalance(BigDecimal pendingReconciliationBalance) {
        this.pendingReconciliationBalance = pendingReconciliationBalance;
    }

    public ObjBanks getBankId() {
        return bankId;
    }

    public void setBankId(ObjBanks bankId) {
        this.bankId = bankId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjAccounts)) {
            return false;
        }
        ObjAccounts other = (ObjAccounts) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.paccman.db.ObjAccounts[accountId=" + accountId + "]";
    }

}
