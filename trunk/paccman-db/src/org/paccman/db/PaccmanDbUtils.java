/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import org.paccman.paccman.ScheduledTransaction.PeriodUnit;
import org.paccman.paccman.TransactionBase;

/**
 *
 * @author joao
 */
public class PaccmanDbUtils {

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
     * Converts the reconcile enum to the value stored in database.
     * @param state The state to be converted.
     * @return The database value.
     */
    static final String reconcileToDbReconcile(TransactionBase.ReconciliationState state) {
        switch (state) {
            case MARKED:
                return "M";
            case RECONCILED:
                return "R";
            case UNRECONCILED:
                return "U";
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
                return "M";
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

    private PaccmanDbUtils() {
    }
}
