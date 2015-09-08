package com.bookislife.sauce.files;

import java.io.File;

/**
 * Created by mrseasons on 2015/09/08.
 */
public final class AndroidFileHandles implements FileHandles {

    enum FileType {
        ASSETS, // assets(read only)
        SDCARD, // sd card
        FILES, // data/data/app/files
        ABSOLUTE, // directory file
        CACHE, // cache
    }

    @Override
    public FileHandle absolute(String path) {
        return new AndroidFileHandle(FileType.ABSOLUTE, path);
    }

    @Override
    public FileHandle absolute(File file) {
        return new AndroidFileHandle(file);
    }

    @Override
    public FileHandle absolute(FileHandle dirHandle, String path) {
        return null;
    }

    @Override
    public FileHandle absolute(String first, String... more) {
        return null;
    }

    @Override
    public FileHandle internal(String path) {
        return new AndroidFileHandle(FileType.ASSETS, path);
    }

    @Override
    public FileHandle external(String path) {
        return new AndroidFileHandle(FileType.SDCARD, path);
    }

    public FileHandle sdcard(String path) {
        return external(path);
    }

    public FileHandle assets(String path) {
        return internal(path);
    }

    @Override
    public FileHandle internal(String first, String... more) {
        return null;
    }
}
