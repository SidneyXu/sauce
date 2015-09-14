package com.bookislife.sauce.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.zip.ZipInputStream;

/**
 * The class is used to make easier to handle some io operations.
 *
 * @author SidneyXu
 */
public class IOUtils {

    /**
     * Delete the specified file is possible.
     *
     * @param file the file to be deleted
     * @return True if succeed. False otherwise.
     */
    public static boolean deleteFile(final File file) {
        return file.exists() && file.delete();
    }

    /**
     * Delete the specified file recursively. Both available for file and directory.
     *
     * @param file the file to be deleted
     */
    public static void recursiveDelete(final File file) {
        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    recursiveDelete(f);
                }
            }
        }
        deleteFile(file);
    }

    /**
     * Delete the children of the specified file recursively. Only available for file.
     *
     * @param root the root directory.
     */
    public static void deleteChildren(final File root) {
        if (root.isDirectory() && root.exists()) {
            File[] files = root.listFiles();
            if (files != null) {
                for (File f : files) {
                    recursiveDelete(f);
                }
            }
        }
    }

    /**
     * Copy the data from the inputStream to the outputStream.
     * <p></p>The passed inputStream and outputStream will be closed automatically before the
     * method returned.(Expected the inputStream is an ZipInputStream.)
     *
     * @param in         the inputStream to provide the data
     * @param out        the outputStream to write to
     * @param bufferSize the buffer size when copying
     * @throws IOException
     */
    public static void copyTo(final InputStream in, final OutputStream out,
                              final int bufferSize)
            throws IOException {
        byte[] buffer = new byte[bufferSize];
        int len;
        try {
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                if (!(in instanceof ZipInputStream))
                    in.close();
            }
        }
    }

    /**
     * Copy the data from the fileInputStream to the fileOutputStream by fileChannel.
     * <p></p>The passed inputStream and outputStream will be closed automatically before the
     * method returned.
     *
     * @param in  the inputStream to provide the data
     * @param out the outputStream to write to
     * @throws IOException
     */
    public static void copyLarge(final FileInputStream in, final FileOutputStream out)
            throws IOException {
        FileChannel readChannel = null;
        FileChannel writeChannel = null;
        try {
            readChannel = in.getChannel();
            writeChannel = out.getChannel();
            readChannel.transferTo(0, readChannel.size(), writeChannel);
        } finally {
            if (writeChannel != null) {
                writeChannel.close();
            }
            if (readChannel != null) {
                readChannel.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
