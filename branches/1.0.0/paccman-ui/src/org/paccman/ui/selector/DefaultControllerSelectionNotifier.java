/*
 * DefaultControllerSelectionListener.java
 *
 * Created on 7 octobre 2005, 09:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.selector;

import java.util.ArrayList;

/**
 *
 * @author joao
 */
public class DefaultControllerSelectionNotifier implements ControllerSelectionNotifier {
    
    ArrayList<ControllerSelectionListener> listeners = new ArrayList<ControllerSelectionListener>();
    
    /** Creates a new instance of DefaultControllerSelectionListener */
    public DefaultControllerSelectionNotifier() {
    }

    public void removeListener(ControllerSelectionListener selectionListener) {
        listeners.remove(selectionListener);
    }

    public void addListener(ControllerSelectionListener selectionListener) {
        listeners.add(selectionListener);
    }

    public void notifyListener(org.paccman.controller.Controller selectedController) {
        selectedController.notifyChange();
    }
    
}
