/*
 *
 *  Copyright (C)    2008 Joao F. (joaof@sourceforge.net)
 *                   http://paccman.sourceforge.net 
 *
 *  This file is part of PAccMan.
 *
 *  PAccMan is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PAccMan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PAccMan.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.paccman.ui.main.actions;

/**
 *
 * @author joao
 */
public class Actions {

    static CloseAction theCloseAction = new CloseAction();

    static OpenAction theOpenAction = new OpenAction();

    static SaveAction theSaveAction = new SaveAction(false);

    static SaveAction theSaveAsAction = new SaveAction(true);

    static QuitAction theQuitAction = new QuitAction();

    static PropertiesAction thePropertiesAction = new PropertiesAction();

    static NewAction theNewAction = new NewAction();

    /**
     * Provides access to the CloseAction.
     * @return The Close action.
     */
    public static CloseAction getCloseAction() {
        return theCloseAction;
    }

    /**
     * Provides access to the OpenAction.
     * @return The Open action.
     */
    public static OpenAction getOpenAction() {
        return theOpenAction;
    }

    /**
     * Provides access to the NewAction.
     * @return The New action.
     */
    public static NewAction getNewAction() {
        return theNewAction;
    }

    /**
     * Provides access to the SaveAction.
     * @return The SaveAs action.
     */
    public static SaveAction getSaveAsAction() {
        return theSaveAsAction;
    }

    /**
     * Provides access to the SaveAction.
     * @return The Save action.
     */
    public static SaveAction getSaveAction() {
        return theSaveAction;
    }

    /**
     * Provides access to the SaveAction.
     * @return The Save action.
     */
    public static QuitAction getQuitAction() {
        return theQuitAction;
    }

    /**
     * Provides access to the PropertiesAction.
     * @return The Save action.
     */
    public static PropertiesAction getPropertiesAction() {
        return thePropertiesAction;
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------
    private Actions() {
    }

}
