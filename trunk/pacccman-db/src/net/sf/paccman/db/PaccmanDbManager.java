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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class PaccmanDbManager {

    List<PaccmanDatabase> databases = new ArrayList<PaccmanDatabase>();

    private static final String DATABASESDATABASESXML = "/.paccman/databases/databases.xml";

    private static PaccmanDbManager instance = new PaccmanDbManager();

    /**
     * Get the singleton instance of {@code PaccmanDbManager}.
     * @return
     */
    public static PaccmanDbManager getInstance() {
        return instance;
    }

    private PaccmanDbManager() {
    }

    /**
     * Load the default database list from {@code databases.xml}
     */
    public void load() {
        String s = System.getProperty("user.dir");
        s += DATABASESDATABASESXML;
        XStream xstream = new XStream();
        PaccmanDbManager pdbmgr = (PaccmanDbManager) xstream.fromXML(s);
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
            Logger.getLogger(PaccmanDbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDatatabaseFileList() {
        save();
        return System.getProperty("user.home") + DATABASESDATABASESXML;
    }

}
