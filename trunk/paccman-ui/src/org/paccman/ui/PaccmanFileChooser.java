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

package org.paccman.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import org.paccman.preferences.ui.MainPrefs;

/**
 *
 * @author joao
 */
public class PaccmanFileChooser extends JFileChooser {

    class PaccmanFileFilter extends FileFilter {

        public boolean accept(File f) {
            if (!f.exists() || f.isDirectory()) {
                return true;
            }

            String extension = getExtension(f);
            if (extension != null) {
                return (extension.equals("paccman"));
            }

            return false;
        }

        /**
         * Method to get the extension of the file, in lowercase
         */
        private String getExtension(File f) {
            String s = f.getName();
            int i = s.lastIndexOf('.');
            if (i > 0 && i < s.length() - 1) {
                return s.substring(i + 1).toLowerCase();
            }
            return "";
        }

        public String getDescription() {
            return "Paccman file";
        }

    }

    /** Creates a new instance of PaccmanFileChooser */
    public PaccmanFileChooser() {

        // Set directory from preferences
        super(MainPrefs.getDataDirectory());

        // File selection only
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Add paccman file filter
        addChoosableFileFilter(new PaccmanFileFilter());

    }

    @Override
    public void approveSelection() {

        if (getDialogType() == JFileChooser.SAVE_DIALOG) {

            // If the file exists ask the user if he wants to overwwrite it
            if (getSelectedFile().exists()) {
                int diag = JOptionPane.showConfirmDialog(this, "File already exists. Overwrite ?", "Overwrite file",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (diag == JOptionPane.NO_OPTION) {
                    return;
                }
            }
        }

        super.approveSelection();
    }

}
