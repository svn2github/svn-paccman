/*
 
    Copyright (C)    2007 Joao F. (joaof@sourceforge.net)
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

package org.paccman.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.paccman.paccman.ScheduledTransaction.PeriodUnit;
import org.paccman.paccman.TransactionBase;

/**
 *
 * @author joao
 */
public class PaccmanDbUtils {
    // Reconcilied status
    static final String DB_MARKED = "M";
    static final String DB_RECONCILIED = "R";
    static final String DB_UNRECONCILIED = "U";
    
    // Period units
    static final String DB_PERIOD_UNIT_DAY = "D";
    static final String DB_PERIOD_UNIT_WEEK = "W";
    static final String DB_PERIOD_UNIT_MONTH = "M";
    static final String DB_PERIOD_UNIT_YEAR = "Y";

    /**
     * Current version of database model.
     */
    static final String CURRENT_VERSION = "1.0.0.0";
    /**
     * Name of key for the title of the document in DOCINFO table.
     */
    static final String DOCUMENT_TITLE_KEY = "title";

    /**
     * Name of key for the creation date of the document in DOCINFO table.
     */
    static final String CREATEDOC_DATE_KEY = "creationdate";
    /**
     * Name of key for the last update date of the document in DOCINFO table.
     */
    static final String UPDATEDOC_DATE_KEY = "lastupdate";
    /**
     * Version of the databsae model.
     */
    static final String DOCVERSION_KEY = "dbversion";

    /**
     * Converts the given calendar to a SQL Timestamp.
     * @param c The calendar to convert.
     * @return The SQL timestamp equivalent of the <code>c</code> Calendar.
     */
    static final Timestamp calendarToSqlDate(Calendar c) {
        return c == null ? null : new Timestamp(c.getTimeInMillis());
    }

    /**
     * Converts the given SQL Date to a Calendar.
     * @param date The SQL date to convert.
     * @return The SQL date equivalent of the <code>c</code> Calendar.
     */
    static final Calendar sqlDateToCalendar(Timestamp date) {
        if (date == null) {
            return null; 
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return gc;
        }
    }

    /**
     * Converts the reconcile enum to the value stored in database.
     * @param state The state to be converted.
     * @return The database value.
     */
    static final String reconcileToDbReconcile(TransactionBase.ReconciliationState state) {
        switch (state) {
            case MARKED:
                return DB_MARKED;
            case RECONCILED:
                return DB_RECONCILIED;
            case UNRECONCILED:
                return DB_UNRECONCILIED;
        }
        throw new AssertionError("Unrecognize reconciliation state");
    }

    /**
     * Converts the value stored in database to the reconcile enum.
     * @param state The state to be converted.
     * @return The reconcilied enum value.
     */
    static final TransactionBase.ReconciliationState dbReconcileToReconcile(String state) {
        if (state.equals(DB_MARKED)) {
            return TransactionBase.ReconciliationState.MARKED;
        } else if (state.equals(DB_RECONCILIED)) {
            return TransactionBase.ReconciliationState.RECONCILED;
        } else if (state.equals(DB_UNRECONCILIED)) {
            return TransactionBase.ReconciliationState.UNRECONCILED;
        }
        throw new AssertionError("Unrecognize reconciliation state");
    }

    /**
     * Converts the period unit enum to the value stored in database.
     * @param periodUnit The period unit to be converted.
     * @return The database value.
     */
    static final String periodUnitToDbPeriodUnit(PeriodUnit periodUnit) {
        switch (periodUnit) {
            case DAY:
                return DB_PERIOD_UNIT_DAY;
            case WEEK:
                return DB_PERIOD_UNIT_WEEK;
            case MONTH:
                return DB_PERIOD_UNIT_MONTH;
            case YEAR:
                return DB_PERIOD_UNIT_YEAR;
        }
        throw new AssertionError("Unrecognize period unit");
    }

    /**
     * Converts the period unit stored in database to the equivalent enum.
     * @param periodUnit The database period unit to be converted.
     * @return The enum value.
     */
    static final PeriodUnit dbPeriodUnitToPeriodUnit(String periodUnit) {
        if (periodUnit.equals(DB_PERIOD_UNIT_DAY)) {
            return PeriodUnit.DAY;
        } else if (periodUnit.equals(DB_PERIOD_UNIT_WEEK)) {
            return PeriodUnit.WEEK;
        } else if (periodUnit.equals(DB_PERIOD_UNIT_MONTH)) {
            return PeriodUnit.MONTH;
        } else if (periodUnit.equals(DB_PERIOD_UNIT_YEAR)) {
            return PeriodUnit.YEAR;
        }
        throw new AssertionError("Unrecognized period unit.");
    }

    /**
     * Returns the value for IDENTITY column returned by the last execution of 
     * the given statement.
     * @param stat The statement executed that performed an insert in a table
     * with an IDENTITY column.
     * @return The value of IDENTITY.
     * @throws java.sql.SQLException
     */
    static final long getIdentity(PreparedStatement stat) throws SQLException {
        ResultSet rs = stat.getGeneratedKeys();
        rs.next();
        return rs.getBigDecimal(1).longValue();
    }

    static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    
    private PaccmanDbUtils() {
    }
}
