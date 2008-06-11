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

import org.paccman.ui.PropertiesFrame;
import static org.paccman.ui.main.ContextMain.*;

/**
 * Action for "Properties" action.
 * @author jfer
 */
public class PropertiesAction extends PaccmanAction {

    PropertiesAction() {
        super("Properties...", "properties.png", false);
    }

    @Override
    void doReset() {
    }
    
    @Override
    Result doLogic() {
        return Result.OK;
    }

    @Override
    void doProcess() {
        PropertiesFrame pf = new PropertiesFrame();
        getDocumentController().registerView(pf);
        pf.onChange(getDocumentController());
        pf.setVisible(true);
    }

}
