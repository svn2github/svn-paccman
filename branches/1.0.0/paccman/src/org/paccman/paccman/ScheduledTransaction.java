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

import java.util.Calendar;

/**
 * 
 * @author joao
 */
public class ScheduledTransaction extends PaccmanObject {
    
    /** Creates a new instance of ScheduledTransaction */
    public ScheduledTransaction() {
    }

    /**
     * Holds value of property transactionBase.
     */
    private TransactionBase transactionBase;

    /**
     * Getter for property transactionBase.
     * @return Value of property transactionBase.
     */
    public TransactionBase getTransactionBase() {

        return this.transactionBase;
    }

    /**
     * Setter for property transactionBase.
     * @param transactionBase New value of property transactionBase.
     */
    public void setTransactionBase(TransactionBase transactionBase) {

        this.transactionBase = transactionBase;
    }

    /**
     * 
     */
    public static enum PeriodUnit {
        /**
         * 
         */
        DAY, 
        /**
         * 
         */
        WEEK, 
        /**
         * 
         */
        MONTH, 
        /**
         * 
         */
        YEAR 
    }

    /**
     * Holds value of property PeriodUnit.
     */
    private PeriodUnit periodUnit;

    /**
     * Getter for property PeriodUnit.
     * @return Value of property PeriodUnit.
     */
    public PeriodUnit getPeriodUnit() {

        return this.periodUnit;
    }

    /**
     * Setter for property PeriodUnit.
     * 
     * @param periodUnit New value of property periodUnit
     */
    public void setPeriodUnit(PeriodUnit periodUnit) {

        this.periodUnit = periodUnit;
    }

    /**
     * Holds value of property period.
     */
    private int period;

    /**
     * Getter for property period.
     * @return Value of property period.
     */
    public int getPeriod() {

        return this.period;
    }

    /**
     * Setter for property period.
     * @param period New value of property period.
     */
    public void setPeriod(int period) {
        assert( period > 0): "Period should always be > 0";
        
        this.period = period;
    }

    /**
     * Holds value of property identifier.
     */
    private String identifier;

    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getIdentifier() {

        return this.identifier;
    }

    /**
     * Setter for property name.
     * 
     * @param identifier New value of property identifier
     */
    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    /**
     * Holds value of property nextOccurence.
     */
    private Calendar nextOccurence;

    /**
     * Getter for property nextOccurence.
     * @return Value of property nextOccurence.
     */
    public Calendar getNextOccurence() {

        return this.nextOccurence;
    }

    /**
     * Setter for property nextOccurence.
     * @param nextOccurence New value of property nextOccurence.
     */
    public void setNextOccurence(Calendar nextOccurence) {

        this.nextOccurence = nextOccurence;
    }

    /**
     * Holds value of property automatic.
     */
    private boolean automatic;

    /**
     * Getter for property automatic.
     * @return Value of property automatic.
     */
    public boolean isAutomatic() {

        return this.automatic;
    }

    /**
     * Setter for property automatic.
     * @param automatic New value of property automatic.
     */
    public void setAutomatic(boolean automatic) {

        this.automatic = automatic;
    }

    /**
     * Holds value of property scheduleDays.
     */
    private int scheduleDays;

    /**
     * Getter for property scheduleDays.
     * @return Value of property scheduleDays.
     */
    public int getScheduleDays() {

        return this.scheduleDays;
    }

    /**
     * Setter for property scheduleDays.
     * @param scheduleDays New value of property scheduleDays.
     */
    public void setScheduleDays(int scheduleDays) {

        this.scheduleDays = scheduleDays;
    }

    /**
     * Holds value of property description.
     */
    private String description;

    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public String getDescription() {

        return this.description;
    }

    /**
     * Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * Holds value of property fixedAmount.
     */
    private boolean fixedAmount;

    /**
     * Getter for property fixedAmount.
     * @return Value of property fixedAmount.
     */
    public boolean isFixedAmount() {

        return this.fixedAmount;
    }

    /**
     * Setter for property fixedAmount.
     * @param fixedAmount New value of property fixedAmount.
     */
    public void setFixedAmount(boolean fixedAmount) {

        this.fixedAmount = fixedAmount;
    }

}
