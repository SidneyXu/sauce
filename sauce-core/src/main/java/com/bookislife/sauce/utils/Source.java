package com.bookislife.sauce.utils;

import com.bookislife.sauce.Sauce;
import com.bookislife.sauce.files.FileHandle;

import java.io.File;

/**
 * Created by mrseasons on 2015/09/10.
 */
public class Source {

    public static FileHandle fromFile(File file) {
        FileHandle handle = Sauce.files.absolute(file);
        return handle;
    }
}
