/*
 * PaccmanFormInterface.java
 *
 * Created on 23 juin 2005, 07:10
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.form;
import org.paccman.controller.Controller;
import org.paccman.controller.PaccmanView;

/**
 *
 * @author joao
 */
public interface PaccmanFormInterface extends PaccmanView {
    // Controller operations
    void setController(Controller controller);
    void removeController();
    Controller getNewController();
    
    // GUI controls operations
    void setForm(Controller controller);
    void clearForm();
    void setEditMode(boolean editing);
    
    // automaton operations
    void        onNew();
    void        onCancel();
    void        onSelect(Controller controller);
    void        onUnselect();
    Controller  onValidate();
    void        onEdit();
    
    // other operation
    public abstract void registerToDocumentCtrl();

}
