/*
 * This file is part of paccman.
 *
 * paccman is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * paccman is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with paccman.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package net.sf.paccman.db;

import com.thoughtworks.xstream.XStream;
import java.beans.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class PaccmanDatabaseMgr implements Serializable {

    private PropertyChangeSupport propertySupport;

    private PaccmanDatabaseMgr() {
        propertySupport = new PropertyChangeSupport(this);
    }

    /**
     * Add the specified property change listener.
     * @param listener the listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove the specified property change listener.
     * @param listener the listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    List<PaccmanDatabase> databases = new ArrayList<PaccmanDatabase>();

    private static final String DATABASESDATABASESXML = "/.paccman/databases/databases.xml";

    private static PaccmanDatabaseMgr instance = new PaccmanDatabaseMgr();

    /**
     * Get the singleton instance of {@code PaccmanDbManager}.
     * @return
     */
    public static PaccmanDatabaseMgr getInstance() {
        return instance;
    }

    /**
     * Load the default database list from {@code databases.xml}
     */
    public void load() {
        String s = System.getProperty("user.dir");
        s += DATABASESDATABASESXML;
        XStream xstream = new XStream();
        PaccmanDatabaseMgr pdbmgr = (PaccmanDatabaseMgr) xstream.fromXML(s);
    }

    /**
     * Save the default database list to {@code databases.xml}
     */
    public void save() {
        XStream xstream = new XStream();
        try {
            xstream.toXML(this, new FileWriter(getDatatabaseFileList()));
        } catch (IOException ex) {
            //:TODO:
            Logger.getLogger(PaccmanDatabaseMgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDatatabaseFileList() {
        save();
        return System.getProperty("user.home") + DATABASESDATABASESXML;
    }

    /**
     * Get the list of registered account folders.
     * @return
     */
    public List<PaccmanDatabase> getDatabases() {
        return databases;
    }

}
