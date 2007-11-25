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
