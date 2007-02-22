/*
 * PaccmanForm.java
 *
 * Created on 23 juin 2005, 07:33
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.paccman.ui.form;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.paccman.controller.Controller;
/**
 *
 * @author joao
 */
public abstract class PaccmanForm extends JPanel implements PaccmanFormInterface {
    
    protected Controller controller;
    protected enum State { NO_ITEM_SELECTED, EDITING_NEW_N, ITEM_SELECTED, EDITING_NEW_S, EDITING_EXISTING };
    protected State state = State.NO_ITEM_SELECTED;
    
    /** Creates a new instance of PaccmanForm */
    public PaccmanForm() {
    }
    
    public abstract void clearForm();
    public abstract void setForm(Controller controller);
    public abstract void getForm(Controller controller) throws BadInputException;
    public abstract void setEditMode(boolean editing);
    public abstract void registerToDocumentCtrl();
    public abstract Controller getNewController();
    
    protected boolean editing;
    
    public boolean isEditing() {
        return editing;
    }
    
    private void doSetEditMode(boolean editing) {
        this.editing = editing;
        setEditMode(editing);
    }
    
    public void onChange(Controller controller) {
        if (controller == this.controller) {
            if (state == State.ITEM_SELECTED) {
                setForm(controller);
            }
        }
    }
    
    public void removeController() {
        if (this.controller != null) {
            controller.unregisterView(this);
            controller  = null;
        }
    }
    
    public void setController(Controller controller) {
        removeController();
        this.controller = controller;
        controller.registerView(this);
    }
    
    public void onCancel() {
        
        switch (state) {
            
            case EDITING_NEW_N:
                clearForm();
                doSetEditMode(false);
                state = State.NO_ITEM_SELECTED;
                break;
                
            case EDITING_NEW_S:
                setForm(controller);
                doSetEditMode(false);
                state = State.ITEM_SELECTED;
                break;
                
            case EDITING_EXISTING:
                setForm(controller);
                doSetEditMode(false);
                state = State.ITEM_SELECTED;
                break;
                
            default:
                assert(false): "Bad state for form: " + state;
                
        }
        
    }
    
    public void onEdit() {
        
        editingNew = false;
        
        switch (state) {
            
            case ITEM_SELECTED:
                doSetEditMode(true);
                state = State.EDITING_EXISTING;
                break;
                
            default:
                assert(false);
                
        }
    }
    
    public void onNew() {
        
        editingNew = true;
        
        switch (state) {
            
            case NO_ITEM_SELECTED:
                doSetEditMode(true);
                state = State.EDITING_NEW_N;
                break;
                
            case ITEM_SELECTED:
                clearForm();
                doSetEditMode(true);
                state = State.EDITING_NEW_S;
                break;
                
            default:
                assert(false);
                
        }
    }
    
    public void onSelect(Controller controller) {
        
        switch (state) {
            
            case NO_ITEM_SELECTED:
                setController(controller);
                setForm(controller);
                state = State.ITEM_SELECTED;
                break;
                
            case ITEM_SELECTED:
                setController(controller);
                setForm(controller);
                break;
                
            default:
                assert(false);
                
        }
    }
    
    public void onUnselect() {
        
        switch (state) {
            
            case ITEM_SELECTED:
                removeController();
                clearForm();
                state = State.NO_ITEM_SELECTED;
                break;
                
            default:
                assert(false);
                
        }
    }
    
    public Controller onValidate() {
        try {
            Controller retVal = null;
            
            switch (state) {
                
                case EDITING_NEW_N:
                case EDITING_NEW_S:
                    Controller localCtrl = getNewController();
                    getForm(localCtrl);
                    if (localCtrl != null) {
                        state = state.ITEM_SELECTED;
                        doSetEditMode(false);
                        retVal = localCtrl;
                    } else {
                        retVal = null;
                    }
                    break;
                    
                case EDITING_EXISTING:
                    getForm(controller);
                    state = State.ITEM_SELECTED;
                    doSetEditMode(false);
                    retVal = controller;
                    break;
                    
                default:
                    assert(false): "Bad state onValidate: " + state;
                    
            }
            
            org.paccman.ui.main.Main.setDocumentChanged(true);
            
            return retVal;
        } catch (BadInputException bie) {
            JOptionPane.showMessageDialog(this, bie.getMessage(), "Input error", JOptionPane.ERROR_MESSAGE);
            bie.getComponent().requestFocus();
        }
        return null;
    }
    
    /**
     * Holds value of property editingNew.
     */
    protected boolean editingNew;
    
    /**
     * Getter for property editingNew.
     * @return Value of property editingNew.
     */
    public boolean isEditingNew() {
        
        return this.editingNew;
    }
    
    /**
     * Setter for property editingNew.
     * @param editingNew New value of property editingNew.
     */
    public void setEditingNew(boolean editingNew) {
        
        this.editingNew = editingNew;
    }
    
}
