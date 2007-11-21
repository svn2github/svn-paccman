/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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

        File rootDir = srcDir.getAbsoluteFile();
        for (File f : srcDir.listFiles()) {
            addFileToZip(zos, f, rootDir);
        }

        zos.close();
    }

    private static void addFileToZip(ZipOutputStream zos, File file, File rootDir) throws IOException {
        if (file.isFile()) {
            File relativeFile = new File(file.getAbsolutePath().substring(rootDir.getPath().length() + 1));
            ZipEntry ze = new ZipEntry(relativeFile.getPath());
            zos.putNextEntry(ze);
            int count;
            byte data[] = new byte[BUF_SIZE];
            BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));
            while ((count = origin.read(data, 0, BUF_SIZE)) != -1) {
                zos.write(data, 0, count);
            }
            origin.close();
        } else {
            for (File f : file.listFiles()) {
                addFileToZip(zos, f, rootDir);
            }
        }
    }

    /**
     * Unzip the content of the specified zip into the specified directory. 
     * The destination directory must exists.
     * @param srcZip The zip file.
     * @param destDir The directory to which unzip the file.
     */
    public static void unzipDirectory(File srcZip, File destDir) throws IOException {
        assert destDir.isDirectory();
        String rootDir = destDir.getAbsolutePath();

        ZipInputStream zis = new ZipInputStream(new FileInputStream(srcZip));
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            String fullPath = rootDir + File.separator + new File(ze.getName()).getParent();
            new File(fullPath).mkdirs();
            String fileName = new File(ze.getName()).getName();
            extractFile(zis, fullPath, fileName);
        }
        zis.close();
    }

    private static void extractFile(ZipInputStream zin, String fullPath, String fileName) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fullPath + File.separator + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int count;
        byte data[] = new byte[BUF_SIZE];
        while ((count = zin.read(data, 0, BUF_SIZE)) != -1) {
            bos.write(data, 0, count);
        }
        bos.close();
    }

    private FileUtils() {
    }
}
