package com.bookislife.sauce.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.zip.ZipInputStream;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class IOUtils {

    public static boolean deleteFile(final File file) {
        return file.exists() && file.delete();
    }

    public static void recursiveDelete(final File file) {
        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                recursiveDelete(f);
            }
        }
        deleteFile(file);
    }

    public static void deleteChildren(final File root) {
        if (root.isDirectory() && root.exists()) {
            File[] files = root.listFiles();
            for (File f : files) {
                recursiveDelete(f);
            }
        }
    }

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
