/*
 * MyDateChooser.java
 *
 * Created on 5 juillet 2005, 08:19
 */

package org.paccman.ui.common;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.paccman.ui.common.*;

/**
 * @author joao
 */
public class MyDateChooser extends JDateChooser implements Serializable {
    
    public MyDateChooser() {
    }

    public void setEnabled(boolean enabled) {
        
        for (Component comp: getComponents()) {
            comp.setEnabled(enabled);
        }
        super.setEnabled(enabled);
    }

    public Calendar getCalendarDate() {
        Calendar retVal = new GregorianCalendar();
        retVal.setTime(getDate());
        retVal.set(Calendar.HOUR_OF_DAY, 0);
        retVal.set(Calendar.MINUTE, 0);
        retVal.set(Calendar.SECOND, 0);
        retVal.set(Calendar.MILLISECOND, 0);
        
        return retVal;
    }

}
