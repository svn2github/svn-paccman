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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

abstract class PaccmanAction extends AbstractAction {

    enum Result {

        OK,
        CANCEL
    }

    /**
     * The path to the icon images location.
     */
    private static final String ROOT_PATH_RESSOURCES = "/org/paccman/ui/resources/images/";

    PaccmanAction(String name, String iconFileName, boolean enabled) {
        super(name, new ImageIcon(PaccmanAction.class.getResource(ROOT_PATH_RESSOURCES + iconFileName)));
        setEnabled(enabled);
    }

    abstract Result doLogic();

    abstract void doProcess();

    public void actionPerformed(ActionEvent e) {
        if (Result.CANCEL == doLogic()) {
            return;
        }
        doProcess();
    }
}



