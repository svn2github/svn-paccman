/*
 * ReconcileContext.java
 *
 * Created on 16 novembre 2005, 07:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.transactions.reconcile;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 *
 * @author joao
 */
public class ReconcileContext {
    
    // Constant form data
    public BigDecimal prevBalance ;
    
    // Changeable data
    public BigDecimal newBalance  ;
    public BigDecimal markedAmount;
    
    public Calendar   newDate     ;
    
    // Computed values
    public BigDecimal diffBalance      ;
    public BigDecimal diffMarkedBalance;
    
    /** Creates a new instance of ReconcileContext */
    public ReconcileContext() {
    }
    
}
