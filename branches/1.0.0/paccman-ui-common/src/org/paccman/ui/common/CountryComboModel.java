/*
 * CountryComboModel.java
 *
 * Created on 2 octobre 2005, 23:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.common;

import javax.swing.DefaultComboBoxModel;
import org.paccman.ui.common.*;

/**
 *
 * @author joao
 */
public class CountryComboModel extends DefaultComboBoxModel {
    
    /** Creates a new instance of CountryComboModel */
    public CountryComboModel() {
        //:TODO:
        addElement(null);
        addElement("Belgium");
        addElement("France");
        addElement("Germany");
        addElement("Luxembourg");
        addElement("Portugal");
        addElement("Spain");
        addElement("United Kingdom");
    }
    
}
