/*
 * AccountSelectionListener.java
 *
 * Created on 7 juin 2005, 08:26
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.selector;
import org.paccman.controller.Controller;


/**
 *
 * @author joao
 */
public interface ControllerSelectionListener {
    void    controllerSelected(Controller ctrl);
    boolean selectionEnabled();
    
}
