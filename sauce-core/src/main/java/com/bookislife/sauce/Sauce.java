package com.bookislife.sauce;

import com.bookislife.sauce.files.FileHandles;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class Sauce {

    public static final String LIBRARY_DIRS = "sauce";

    private static SaucePlatform platform;

    public static FileHandles files;

    public static void initialize(SaucePlatform platform) {
        Sauce.platform = platform;
        files = platform.files;
    }
}
