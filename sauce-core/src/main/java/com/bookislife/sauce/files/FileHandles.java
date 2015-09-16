package com.bookislife.sauce.files;

import java.io.File;

/**
 * The class is used to construct FileHandle instance.
 *
 * @author SidneyXu
 */
public interface FileHandles {
    FileHandle absolute(String path);

    FileHandle absolute(File file);

    FileHandle absolute(FileHandle dirHandle, String path);

    FileHandle absolute(String first, String... more);

    FileHandle internal(String path);

    FileHandle internal(String first, String... more);

    FileHandle external(String path);

    FileHandle external(String first, String... more);
}
