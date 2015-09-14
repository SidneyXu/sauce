package com.bookislife.sauce.utils;

import com.bookislife.sauce.Sauce;
import com.bookislife.sauce.files.FileHandle;

import java.io.File;

/**
 * The class represents source in the sauce Library.
 * <p></p>The source can be Files, Http Requests or any others.
 *
 * @author SidneyXu
 */
public abstract class Source {

    /**
     * Construct the FileHandle with the specified file.
     *
     * @param file the specified file
     * @return a FileHandle
     */
    public static FileHandle fromFile(File file) {
        FileHandle handle = Sauce.files.absolute(file);
        return handle;
    }
}
