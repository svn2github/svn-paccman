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

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author jfer
 */
public class LookAndFeelMenu extends JMenu implements ActionListener {
    
    class LookAndFeelMenuItem extends JRadioButtonMenuItem {
        LookAndFeelInfo lafInfo;
        
        public LookAndFeel getLookAndFeel() {
            try {
                return (LookAndFeel)Class.forName(lafInfo.getClassName()).newInstance();
            } catch (ClassNotFoundException cnf) { 
                return null;
            } catch (InstantiationException ie) {
                return null;
            } catch (IllegalAccessException ia) {
                return null;
            }
        }
        
        LookAndFeelMenuItem(LookAndFeelInfo lafInfo, LookAndFeelMenu lafMenu) {
            super(lafInfo.getName());
            this.lafInfo = lafInfo;
            addActionListener(lafMenu);
            lafMenu.add(this);
            String currentLookAndFeelClassName = UIManager.getLookAndFeel().getClass().getName();
            if (lafInfo.getClassName().equals(currentLookAndFeelClassName)) {
                setSelected(true);
            }
        }
    }
    
    /** Creates a new instance of LookAndFeelMenu */
    JFrame rootFrame;
    
    public LookAndFeelMenu(JFrame rootFrame) {
        super("Look and Feel");
        this.rootFrame = rootFrame;
        ButtonGroup btnGrp = new ButtonGroup();
        UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo lafInfo: lafs) {
            JMenuItem item = new LookAndFeelMenuItem(lafInfo, this);
            btnGrp.add(item);
        }
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        LookAndFeelMenuItem lafItem = (LookAndFeelMenuItem)(e.getSource());
        try {
            UIManager.setLookAndFeel(lafItem.getLookAndFeel());
            SwingUtilities.updateComponentTreeUI(rootFrame);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }
    
}

