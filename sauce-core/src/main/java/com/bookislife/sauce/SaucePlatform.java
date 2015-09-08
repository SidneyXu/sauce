package com.bookislife.sauce;

import com.bookislife.sauce.files.FileHandles;

/**
 * Created by mrseasons on 2015/09/08.
 */
public abstract class SaucePlatform {

    public final FileHandles files;

    public static final String VERSION = "0.0.1";

    public SaucePlatform() {
        files = getFiles();
    }

    protected abstract FileHandles getFiles();
}
