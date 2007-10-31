/*
 
    Copyright (C)    2005 Joao F. (joaof@sourceforge.net)
                     http://paccman.sourceforge.net 

    This program is free software; you can redistribute it and/or modify      
    it under the terms of the GNU General Public License as published by      
    the Free Software Foundation; either version 2 of the License, or         
    (at your option) any later version.                                       

    This program is distributed in the hope that it will be useful,           
    but WITHOUT ANY WARRANTY; without even the implied warranty of            
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             
    GNU General Public License for more details.                              

    You should have received a copy of the GNU General Public License         
    along with this program; if not, write to the Free Software               
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 
*/

package org.paccman.controller;

import java.util.ArrayList;
import java.util.List;
import org.paccman.paccman.PaccmanObject;

/**
 *
 * @author joao
 */
public class Controller {
    
    List<PaccmanView>     viewList = new ArrayList<PaccmanView>();
    PaccmanObject  paccObj;
    
    /**
     * 
     * @return 
     */
    public PaccmanObject getObject() {
        return paccObj;
    }
    
    /** Creates a new instance of Controller 
     * @param paccObj 
     */
    public Controller(PaccmanObject paccObj) {
        this.paccObj = paccObj;
        ControllerManager.addController(paccObj, this);
    }
    
    /**
     * 
     * @param view 
     */
    public void registerView(PaccmanView view) {
        if (! viewList.contains(view)) {
            viewList.add(view);
        }
    }
    
    /**
     * 
     * @param view 
     */
    public void unregisterView(PaccmanView view) {
        viewList.remove(view);
    }
    
    /**
     * 
     */
    public void notifyChange() {
        for (PaccmanView v: viewList) {
            v.onChange(this);
        }
    }
    
}
