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

import javax.swing.AbstractAction;

/**
 *
 * @author joao
 */
public class Actions {

    static CloseAction theCloseAction = new CloseAction();

    static OpenAction theOpenAction = new OpenAction();

    static SaveAction theSaveAction = new SaveAction();

    static SaveAsAction theSaveAsAction = new SaveAsAction();

    /**
     * Provide access to the SaveAsAction.
     * @return The SaveAs action.
     */
    public static AbstractAction getSaveAsAction() {
        return theSaveAsAction;
    }
    
    private Actions() {
    }
}
