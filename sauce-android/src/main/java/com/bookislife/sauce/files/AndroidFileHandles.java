package com.bookislife.sauce.files;

import com.bookislife.sauce.utils.IOUtils;

import java.io.File;

/**
 * The class is used to construct AndroidFileHandles instance.
 *
 * @author SidneyXu
 */
public final class AndroidFileHandles implements FileHandles {

    /**
     * Defines the file type on the Android platform.
     */
    enum FileType {
        /**
         * Represents files in the assets directory.
         * The files are read only.
         */
        ASSETS, // assets(read only)
        /**
         * Represents files in the sdcard.
         * Before using sdcard, you must always check if the sdcard is mounted
         * has defined the correct permission in the AndroidManifest.xml.
         */
        SDCARD, // sd card
        /**
         * Represents files in the /data/data/yourPackageName/files.
         */
        FILES, // data/data/app/files
        /**
         * Represents the file in the specified absolute path.
         */
        ABSOLUTE, // directory file
        /**
         * Represents the file in the /data/data/yourPackageName/caches.
         */
        CACHE, // cache
    }

    /**
     * Construct the FileHandle with the specified path.
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    @Override
    public FileHandle absolute(String path) {
        return new AndroidFileHandle(FileType.ABSOLUTE, path);
    }

    /**
     * Construct the FileHandle with the specified file.
     *
     * @param file the file to be wrapped
     * @return a FileHandle instance
     */
    @Override
    public FileHandle absolute(File file) {
        return new AndroidFileHandle(file);
    }

    /**
     * Construct the FileHandle with the path in the specified directory.
     *
     * @param dirHandle the root directory
     * @param path      the file path
     * @return a FileHandle instance
     */
    @Override
    public FileHandle absolute(FileHandle dirHandle, String path) {
        File file = new File(dirHandle.toFile(), path);
        return new AndroidFileHandle(file);
    }

    /**
     * Construct the FileHandle with the specified path (first + more).
     *
     * @param first the first file path
     * @param more  the more file path
     * @return a FileHandle instance
     */
    @Override
    public FileHandle absolute(String first, String... more) {
        return absolute(IOUtils.constructorPath(first, more));
    }

    /**
     * Construct the FileHandle with the specified path.
     * <p></p>File locates in "assets/path".
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    @Override
    public FileHandle internal(String path) {
        return new AndroidFileHandle(FileType.ASSETS, path);
    }

    /**
     * Construct the FileHandle with the specified path.
     * <p></p>File locates in "sdcard/path".
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    @Override
    public FileHandle external(String path) {
        return new AndroidFileHandle(FileType.SDCARD, path);
    }

    /**
     * Construct the FileHandle with the specified path (first + more).
     * <p></p>File locates in "sdcard/first/more...".
     * @param first the first file path
     * @param more  the more file path
     * @return a FileHandle instance
     */
    public FileHandle external(String first, String... more) {
        return external(IOUtils.constructorPath(first, more));
    }

    /**
     * Construct the FileHandle with the specified path.
     * <p></p>File locates in "sdcard/path".
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    public FileHandle sdcard(String path) {
        return external(path);
    }

    /**
     * Construct the FileHandle with the specified path.
     * <p></p>File locates in "assets/path".
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    public FileHandle assets(String path) {
        return internal(path);
    }

    /**
     * Construct the FileHandle with the specified path (first + more).
     * <p></p>File locates in "assets/first/more...".
     *
     * @param first the first file path
     * @param more  the more file path
     * @return a FileHandle instance
     */
    public FileHandle assets(String first, String... more) {
        return internal(first, more);
    }

    /**
     * Construct the FileHandle with the specified path (first + more).
     * <p></p>File locates in "assets/first/more...".
     *
     * @param first the first file path
     * @param more  the more file path
     * @return a FileHandle instance
     */
    @Override
    public FileHandle internal(String first, String... more) {
        return internal(IOUtils.constructorPath(first, more));
    }

    /**
     * Construct the FileHandle with the specified path.
     * <p></p>File locates in "/data/data/yourPackageName/files/path".
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    public FileHandle files(String path) {
        return new AndroidFileHandle(FileType.FILES, path);
    }


    /**
     * Construct the FileHandle with the specified path (first + more).
     * <p></p>File locates in "/data/data/yourPackageName/files/first/more...".
     *
     * @param first the first file path
     * @param more  the more file path
     * @return a FileHandle instance
     */
    public FileHandle files(String first, String... more) {
        return files(IOUtils.constructorPath(first, more));
    }

    /**
     * Construct the FileHandle with the specified path.
     * <p></p>File locates in "/data/data/yourPackageName/caches/path".
     *
     * @param path the file path
     * @return a FileHandle instance
     */
    public FileHandle caches(String path) {
        return new AndroidFileHandle(FileType.CACHE, path);
    }


    /**
     * Construct the FileHandle with the specified path (first + more).
     * <p></p>File locates in "/data/data/yourPackageName/caches/first/more...".
     *
     * @param first the first file path
     * @param more  the more file path
     * @return a FileHandle instance
     */
    public FileHandle caches(String first, String... more) {
        return caches(IOUtils.constructorPath(first, more));
    }
}
