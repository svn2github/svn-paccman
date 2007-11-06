/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.sql.Date;
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
    public static final String DB_MARKED = "M";
    public static final String DB_RECONCILIED = "R";
    public static final String DB_UNRECONCILIED = "U";

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
     * Converts the given calendar to a SQL Date.
     * @param c The calendar to convert.
     * @return The SQL date equivalent of the <code>c</code> Calendar.
     */
    static final Date calendarToSqlDate(Calendar c) {
        return c == null ? null : new Date(c.getTimeInMillis());
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
     * Converts the value stored in datebase to the reconcile enum.
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
    static final String periodUnitToDbUnit(PeriodUnit periodUnit) {
        switch (periodUnit) {
            case DAY:
                return "D";
            case WEEK:
                return "W";
            case MONTH:
                return DB_MARKED;
            case YEAR:
                return "Y";
        }
        throw new AssertionError("Unrecognize period unit");
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
