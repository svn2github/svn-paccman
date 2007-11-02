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

package org.paccman.preferences.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.util.prefs.Preferences;
/**
 *
 * @author joao
 */
public class MainPrefs {
    
    static Preferences prefs = Preferences.userNodeForPackage(MainPrefs.class);
    
    /** Creates a new instance of MainPrefs */
    private MainPrefs() {
    }

    /**
     * 
     * @return 
     */
    public static Point getLocation() {
        int posx = prefs.getInt(POSX, 100);
        int posy = prefs.getInt(POSY, 100);
        
        return new Point(posx, posy);
    }
    
    /**
     * 
     * @return 
     */
    public static Dimension getSize() {
        int height = prefs.getInt(HEIGHT, 600);
        int width  = prefs.getInt(WIDTH, 800);
        
        return new Dimension(width, height);
    }
    
    /**
     * 
     * @param p 
     */
    public static void putLocation(Point p) {
        prefs.putInt(POSX, p.x);
        prefs.putInt(POSY, p.y);
        
    }
    
    /**
     * 
     * @param p 
     */
    public static void putSize(Dimension p) {
        prefs.putInt(HEIGHT, p.height);
        prefs.putInt(WIDTH, p.width);
        
    }
    
    /**
     * 
     * @return 
     */
    public static String getDataDirectory() {
        return prefs.get(DATA_DIRECTORY, System.getProperty("user.home"));
    }
    
    /**
     * 
     * @param dataDirectory 
     */
    public static void putDataDirectory(String dataDirectory) {
        prefs.put(DATA_DIRECTORY, dataDirectory);
    }
    
    /**
     * 
     * @return 
     */
    public static int getMruSize() {
        int size = prefs.getInt(MRU_SIZE, 0);
        return size;
    }
    
    /**
     * 
     * @param index 
     * @return 
     */
    public static String getMruFile(int index) {
        int size = getMruSize();
        return prefs.get(MRU_FILE + Integer.toString(index), null);
    }
    
    /**
     * 
     * @param filename 
     */
    public static void addFilenameToMru(String filename) {
        int size= getMruSize();

        // Checks if file not already in the list
	for (int i = 0 ; i < size ; i++ ) {
            if (getMruFile(i).equals(filename)) {
                return;
            }
        }
        
        prefs.put(MRU_FILE + Integer.toString(size), filename);
        size++;
        prefs.putInt(MRU_SIZE, size);
    }
    
    /**
     * 
     * @return 
     */
    public static boolean getOpenLastSelectedFile() {
        return prefs.getBoolean(OPEN_LAST_SELECTED_FILE, false);
    }

    /**
     * 
     * @param openLastSelectedFile 
     */
    public static void setOpenLastSelectedFile(boolean openLastSelectedFile) {
        prefs.putBoolean(OPEN_LAST_SELECTED_FILE, openLastSelectedFile);
    }
    
    /**
     * 
     * @return 
     */
    public static boolean getShowStartDialog() {
        return prefs.getBoolean(SHOW_START_DIALOG, true);
    }

    /**
     * 
     * @param showStartDialog 
     */
    public static void setShowStartDialog(boolean showStartDialog) {
        prefs.putBoolean(SHOW_START_DIALOG, showStartDialog);
    }
    
    /**
     * 
     * @return 
     */
    public static String getLastSelectedFile() {
        return prefs.get(LAST_SELECTED_FILE, "");
    }

    /**
     * 
     * @param lastSelectedFile 
     */
    public static void setLastSelectedFile(String lastSelectedFile) {
        prefs.put(LAST_SELECTED_FILE, lastSelectedFile);
    }
    
    /**
     * 
     * @return 
     */
    public static boolean isMaximized() {
        return prefs.getBoolean(IS_MAXIMIZED, false);
    }

    /**
     * 
     * @param isMaximized 
     */
    public static void setMaximized(boolean isMaximized) {
        prefs.putBoolean(IS_MAXIMIZED, isMaximized);
    }
    
    // Preference keys for this package
    private static final String POSX = "posx";
    private static final String POSY = "posy";
    
    private static final String IS_MAXIMIZED = "is_maximaized";
    private static final String HEIGHT       = "height"       ;
    private static final String WIDTH        = "width"        ;
    
    private static final String DATA_DIRECTORY = "data_directory";
    
    private static final String MRU_SIZE = "mru_size";
    private static final String MRU_FILE = "mru_file_";
    
    private static final String OPEN_LAST_SELECTED_FILE = "open_last"         ;
    private static final String LAST_SELECTED_FILE      = "last_selected_file";
    
    private static final String SHOW_START_DIALOG       = "show_start_dialog" ;
    
}
