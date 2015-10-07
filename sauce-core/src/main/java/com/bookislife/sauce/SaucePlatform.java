package com.bookislife.sauce;

import com.bookislife.sauce.files.FileHandles;
import com.bookislife.sauce.providers.Providers;

/**
 * The class represents a specified platform of the sauce Library.
 *
 * @author SidneyXu
 */
public abstract class SaucePlatform {

    /**
     * A util to handle constructor file handle of the specified platform.
     */
    public final FileHandles files;

    public static final String VERSION = "0.0.1-SNAPSHOT";

    /**
     * Construct the sauce platform.
     */
    public SaucePlatform() {
        files = getFiles();
    }

    /**
     * Overrides this to get the FileHandles of the specified platform.
     *
     * @return the FileHandles instance of specified platform
     */
    protected abstract FileHandles getFiles();

    public abstract Providers getProviders(String type);
}
