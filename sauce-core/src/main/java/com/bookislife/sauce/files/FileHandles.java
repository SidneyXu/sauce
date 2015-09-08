package com.bookislife.sauce.files;

import java.io.File;

/**
 * Created by mrseasons on 2015/09/08.
 */
public interface FileHandles {
    FileHandle absolute(String path);

    FileHandle absolute(File file);

    FileHandle absolute(FileHandle dirHandle, String path);

    FileHandle absolute(String first, String... more);

    FileHandle internal(String path);

    FileHandle external(String path);

    FileHandle internal(String first, String... more);
}
