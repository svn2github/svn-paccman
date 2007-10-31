/*
 * CountryComboBox.java
 *
 * Created on 2 octobre 2005, 23:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.common;

import javax.swing.JComboBox;
import org.paccman.ui.common.*;

/**
 *
 * @author joao
 */
public class CountryComboBox extends JComboBox {
    
    /** Creates a new instance of CountryComboBox */
    public CountryComboBox() {
        setModel(new CountryComboModel());
    }
    
}
