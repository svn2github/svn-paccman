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

import java.beans.*;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author joaof (joaof at sourceforge.net)
 */
public class PaccmanDatabase implements Serializable {

    private PropertyChangeSupport propertySupport;

    /**
     * A Paccman account folder.
     */
    public PaccmanDatabase() {
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

    private UUID uuid;

    /**
     * Get the value of uuid
     *
     * @return the value of uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name.
     */
    public String getName() {
        return name;
    }

    private String description;

    /**
     * Get the value of description
     *
     * @return the value of description.
     */
    public String getDescription() {
        return description;
    }

}
