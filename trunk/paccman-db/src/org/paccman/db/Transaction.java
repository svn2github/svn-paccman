/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.paccman.paccman.TransactionBase;
import static org.paccman.db.PaccmanDbUtils.*;

/**
 *
 * @author joao
 */
public class Transaction {

    Connection connection;
    static Transaction transaction;

    private Transaction(Connection connection) {
        this.connection = connection;
    }

    /**
     * 
     * @param connection
     * @return
     */
    static Transaction getInstance(Connection connection) {
        if (transaction == null) {
            transaction = new Transaction(connection);
        }
        return transaction;
    }

    private String SEL_TRANSACTION_BASE_DATA_SQL =
            "select " +
            "    AMOUNT, VALUE_DATE,TRANSACTION_DATE, NOTE, LABEL," +
            "    RECONCILIATION_STATE, RECONCILIATION_DATE " +
            "from " +
            "    OBJ_TRANSACTIONS " +
            "where " +
            "    (TRANSACTION_ID = ?)";
    PreparedStatement statTransactionBaseData;

    void loadTransactionBaseData(long transactionId, TransactionBase transaction) throws SQLException {
        if (statTransactionBaseData == null) {
            statTransactionBaseData = connection.prepareStatement(SEL_TRANSACTION_BASE_DATA_SQL);
        }
        statTransactionBaseData.setLong(1, transactionId);
        ResultSet rs = statTransactionBaseData.executeQuery();
        if (rs.next()) {
            transaction.setAmount(rs.getBigDecimal("AMOUNT"));
            transaction.setValueDate(sqlDateToCalendar(rs.getTimestamp("VALUE_DATE")));
            transaction.setTransactionDate(sqlDateToCalendar(rs.getTimestamp("TRANSACTION_DATE")));
            transaction.setNote(rs.getString("NOTE"));
            transaction.setLabel(rs.getString("LABEL"));
            transaction.setReconciliationState(dbReconcileToReconcile(rs.getString("RECONCILIATION_STATE")));
            transaction.setReconciliationDate(sqlDateToCalendar(rs.getTimestamp("RECONCILIATION_DATE")));
        }
    }

}
