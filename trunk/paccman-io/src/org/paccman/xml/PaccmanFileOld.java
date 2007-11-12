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

package org.paccman.xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import org.paccman.controller.DocumentController;
import org.paccman.paccman.Document;


/**
 *
 * @author joao
 * @deprecated 
 */
@Deprecated
public class PaccmanFileOld {
    
    // Version of paccman file format
    private static final String  FILE_VERSION      = "1.0.0.0"       ;
    
    // Manifest keys and sections
    private static final String  MANIFEST_DOCUMENT = "Document"      ;
    private static final String  FORMAT_VERSION    = "Format-version";
    
    // Document jar entry name
    private static final String  DOCUMENT_ENTRY_NAME = "document.xml";
    
    public PaccmanFileOld() {
    }
    
    private void read(File filein, DocumentController document, String version) throws PaccmanIOException {
        // Instantiate the loader class
        Loader loader = null;
        String pkgVersion = version.replace('.', '_');
        String classname = getClass().getPackage().getName() + "._" + pkgVersion + ".Loader";
        try {
            Class classDefinition;
            classDefinition = Class.forName(classname);
            loader = (Loader)classDefinition.newInstance();
        } catch (ClassNotFoundException e) {
            throw new PaccmanIOException("Failed to find Loader for this version of file");
        } catch (InstantiationException e) {
            throw new PaccmanIOException("Failed to instantiate Loader");
        } catch (IllegalAccessException e) {
            throw new PaccmanIOException("Could not access Loader");
        }
        
        JarInputStream jis;
        try {
            jis = new JarInputStream(new FileInputStream(filein));
            JarEntry je = jis.getNextJarEntry();
            loader.load(jis, document);
            
        } catch (FileNotFoundException e) {
            throw new PaccmanIOException("File not found");
        } catch (IOException ioe) {
            throw new PaccmanIOException(ioe);
        }
    }
    
    public void read(File filein, DocumentController document) throws PaccmanIOException {
        JarFile jf;
        try {
            jf = new JarFile(filein);
        } catch (IOException ioe) {
            throw new PaccmanIOException(ioe);
        }
        
        // Gets the format version of the PAccMan document
        String formatVersion = readFormatVersion(jf);
        document.setLoadVersion(formatVersion);
        
        // Do read
        read(filein, document, formatVersion);
        
        //:TODO:WORKAROUND. KEEP BUT WILL BE DELETED ANYWAY:
        if (document.getDocument().getCreationDate() == null) {
            document.getDocument().setCreationDate(new GregorianCalendar());
        }
        if (document.getDocument().getLastUpdateDate() == null) {
            document.getDocument().setLastUpdateDate(new GregorianCalendar());
        }
        //:TODO:WORKAROUND. KEEP BUT WILL BE DELETED ANYWAY:
        
    }
    
    public void write(File fileout, DocumentController documentCtrl) throws PaccmanIOException {
        Document document = documentCtrl.getDocument();
        
        try {
            Manifest manifest = new Manifest();
            Attributes attribs = new Attributes();
            attribs.putValue(FORMAT_VERSION, FILE_VERSION);
            
            manifest.getEntries().put(PaccmanFileOld.MANIFEST_DOCUMENT, attribs);
            JarOutputStream jos = new JarOutputStream(new FileOutputStream(fileout), manifest);
            
            JarEntry je = new JarEntry("document.xml");
            jos.putNextEntry(je);
            
            // Instantiate the saver class
            Saver saver = null;
            String fv = FILE_VERSION.replace('.', '_');
            String classname = getClass().getPackage().getName() + "._" + fv + ".Saver";
            try {
                Class classDefinition;
                classDefinition = Class.forName(classname);
                saver = (Saver)classDefinition.newInstance();
            } catch (ClassNotFoundException e) {
                throw new PaccmanIOException("Failed to find Saver for this version of file");
            } catch (InstantiationException e) {
                throw new PaccmanIOException("Failed to instantiate Saver");
            } catch (IllegalAccessException e) {
                throw new PaccmanIOException("Could not access Saver");
            }
            
            saver.save(jos, documentCtrl);
            jos.closeEntry();
            jos.close();
        } catch (IOException e) {
            throw new PaccmanIOException(e);
        }
    }
    
    private String readFormatVersion(JarFile jarFile) throws PaccmanIOException {
        Manifest manifest;
        try {
            manifest = jarFile.getManifest();
        } catch (IOException e) {
            throw new PaccmanIOException(e);
        }
        if (manifest == null) {
            throw new PaccmanIOException("Manifest not found");
        }
        Attributes attribs = manifest.getAttributes(MANIFEST_DOCUMENT);
        String version = attribs.getValue(FORMAT_VERSION);
        return version;
    }
    
}
