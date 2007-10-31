/*
 * PaccmanObjectComboBox.java
 *
 * Created on 19 juin 2005, 11:37
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.common;

import javax.swing.JComboBox;
import org.paccman.controller.DocumentController;
import org.paccman.controller.PaccmanView;

/**
 *
 * @author joao
 */
abstract public class PaccmanObjectComboBox extends JComboBox implements PaccmanView {
    
    public void setDocumentController(DocumentController controller) {
        controller.registerView(this);
    }
    
    /** Creates a new instance of PaccmanObjectComboBox */
    public PaccmanObjectComboBox() {
    }
    
    abstract public void onChange(org.paccman.controller.Controller controller);
}
