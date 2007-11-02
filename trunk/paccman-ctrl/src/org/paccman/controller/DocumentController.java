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
import java.io.File;
import org.paccman.paccman.Document;


/**
 *
 * @author joao
 */
public class DocumentController extends Controller {
    
    /** Creates a new instance of DocumentController */
    public DocumentController() {
        super(new Document());
    }
    
    /**
     * 
     * @return
     */
    public Document getDocument() {
        return (Document)super.getObject();
    }

    /**
     * Holds value of property hasChanged.
     */
    private boolean hasChanged;

    /**
     * Getter for property hasChanged.
     * @return Value of property hasChanged.
     */
    public boolean isHasChanged() {

        return this.hasChanged;
    }

    /**
     * Setter for property hasChanged.
     * @param hasChanged New value of property hasChanged.
     */
    public void setHasChanged(boolean hasChanged) {

        this.hasChanged = hasChanged;
    }

    /**
     * Holds value of property filename.
     */
    private File filename;

    /**
     * Getter for property filename.
     * @return Value of property filename.
     */
    public File getFile() {
        return this.filename;
    }

    /**
     * Setter for property filename.
     * @param filename New value of property filename.
     */
    public void setFile(File filename) {
        this.filename = filename;
    }

    /**
     * Holds value of property loadVersion.
     */
    private String loadVersion = "";

    /**
     * Getter for property loadVersion.
     * @return Value of property loadVersion.
     */
    public String getLoadVersion() {
        return this.loadVersion;
    }

    /**
     * Setter for property loadVersion.
     * @param loadVersion New value of property loadVersion.
     */
    public void setLoadVersion(String loadVersion) {
        this.loadVersion = loadVersion;
    }
    
}
