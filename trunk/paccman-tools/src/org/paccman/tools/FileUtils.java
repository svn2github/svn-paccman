/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author joao
 */
public class FileUtils {

    private static final int BUF_SIZE = 256 * 1024;

    /**
     * 
     * @param is
     * @param os
     * @throws java.io.IOException
     */
    public static void copyFile(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[BUF_SIZE];
        int len;
        while ((len = is.read(buf)) > 0) {
            os.write(buf, 0, len);
        }
        is.close();
        os.close();
    }

    /**
     * Copy <code>inputFile</code> to <code>outputFile</code>
     * @param inputFile The source file.
     * @param outputFile The destination file.
     * @throws java.io.IOException
     */
    public static void copyFile(File inputFile, File outputFile) throws IOException {
        // Create channel on the source
        FileChannel srcChannel = new FileInputStream(inputFile).getChannel();

        // Create channel on the destination
        FileChannel dstChannel = new FileOutputStream(outputFile).getChannel();

        // Copy file contents from source to destination
        dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

        // Close the channels
        srcChannel.close();
        dstChannel.close();
    }

    /**
     * Zip the content of the specified directory to the specified zip file.
     * @param srcDir The directory containing the files to zip.
     * @param destFile The zip file to be created.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException 
     */
    public static void zipDirectory(File srcDir, File destFile) throws FileNotFoundException, IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destFile));
        assert srcDir.isDirectory();

        for (File f : srcDir.listFiles()) {
            addFileToZip(zos, f);
        }

        zos.close();
    }

    private static void addFileToZip(ZipOutputStream zos, File file) throws IOException {
        if (file.isFile()) {
            ZipEntry ze = new ZipEntry(file.getPath());
            zos.putNextEntry(ze);
            int count;
            byte data[] = new byte[BUF_SIZE];
            BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));
            while ((count = origin.read(data, 0, BUF_SIZE)) != -1) {
                zos.write(data, 0, count);
            }
            origin.close();
        } else {
            //:TODO:I WAS HERE
        }
    }

    /**
     * Unzip the content of the specified zip into the specified directory.
     * @param srcZip The zip file.
     * @param destDir The directory to which unzip the file.
     */
    public static void unzipDirectory(String srcZip, String destDir) {
    //:TODO:
    }

    private FileUtils() {
    }
}
