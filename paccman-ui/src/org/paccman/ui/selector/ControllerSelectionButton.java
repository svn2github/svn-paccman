/*
 * AccountSelectionButton.java
 *
 * Created on 5 juin 2005, 01:08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.selector;

import javax.swing.JButton;
import org.paccman.controller.Controller;
import org.paccman.controller.PaccmanView;

/**
 *
 * @author joao
 */
public class ControllerSelectionButton extends JButton implements PaccmanView {
    
    Controller ctrl;
    
    /**
     * Creates a new instance of ControllerSelectionButton
     * @param ctrl 
     */
    public ControllerSelectionButton(Controller ctrl) {
        super(ctrl.toString()); 
        this.ctrl = ctrl;
        ctrl.registerView(this);
    }

    /**
     * 
     * @param controller 
     */
    public void onChange(Controller controller) {
        setText(ctrl.toString());
    }
    
    /**
     * 
     * @return 
     */
    public Controller getController() {
        return ctrl;
    }

}
