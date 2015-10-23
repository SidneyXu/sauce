package com.bookislife.sauce.mock;

import com.bookislife.sauce.files.FileHandle;
import com.bookislife.sauce.files.FileHandles;

import java.io.File;

/**
 * Created by SidneyXu on 2015/10/23.
 */
public class MockFileHandles implements FileHandles {
    @Override
    public FileHandle absolute(final String path) {
        return null;
    }

    @Override
    public FileHandle absolute(final File file) {
        return null;
    }

    @Override
    public FileHandle absolute(final FileHandle dirHandle, final String path) {
        return null;
    }

    @Override
    public FileHandle absolute(final String first, final String... more) {
        return null;
    }

    @Override
    public FileHandle internal(final String path) {
        return null;
    }

    @Override
    public FileHandle internal(final String first, final String... more) {
        return null;
    }

    @Override
    public FileHandle external(final String path) {
        return null;
    }

    @Override
    public FileHandle external(final String first, final String... more) {
        return null;
    }
}
