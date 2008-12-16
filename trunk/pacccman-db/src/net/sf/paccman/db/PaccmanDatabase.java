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

import java.util.UUID;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class PaccmanDatabase {

    UUID uuid;

    String name;

    String description;
    
    /**
     * Craete a new database.
     * @param name the name of the database.
     * @param description the description of the database.
     */
    public void create(String name, String description) {
        //:TODO:
    }

    /**
     * Open the given database.
     * @param name the database to open.
     */
    public void open(String name) {
        //:TODO:
    }

    /**
     * Close the database.
     */
    public void close() {
        //:TODO:
    }

    /**
     * Save the current change to the database (i.e. performs a {@code commit})
     */
    public void save() {
        //:TODO:
    }
}
