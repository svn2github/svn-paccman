/*
 * BadInputException.java
 *
 * Created on 25 octobre 2005, 22:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.paccman.ui.form;

import javax.swing.JComponent;

/**
 *
 * @author joao
 */
public class BadInputException extends Exception {
    
    /** Creates a new instance of BadInputException */
    public BadInputException(String message, javax.swing.JComponent component) {
        super(message);
        this.component = component;
    }

    /**
     * Holds value of property component.
     */
    private JComponent component;

    /**
     * Getter for property component.
     * @return Value of property component.
     */
    public JComponent getComponent() {
        return this.component;
    }

    /**
     * Setter for property component.
     * @param component New value of property component.
     */
    public void setComponent(JComponent component) {
        this.component = component;
    }
    
}
