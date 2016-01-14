package com.bookislife.sauce;

import com.bookislife.sauce.files.FileHandles;

/**
 * The Sauce class is used to initialize sauce library.
 *
 * @author SidneyXu
 */
public class Sauce {

    public static final String LIBRARY_DIRS = "sauce";

    private static SaucePlatform platform;

    /**
     * A util to handle constructor file handle on the specified platform.
     */
    public static FileHandles files;

    public static void initialize(SaucePlatform platform) {
        Sauce.platform = platform;
        files = platform.files;
    }

    public static SaucePlatform getPlatform() {
        return platform;
    }

    public static <T> T getPlatform(Class<? extends SaucePlatform> clazz) {
        return (T) clazz.cast(getPlatform());
    }
}
