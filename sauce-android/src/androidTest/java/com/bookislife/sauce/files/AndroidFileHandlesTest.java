package com.bookislife.sauce.files;

import android.content.Context;
import com.bookislife.sauce.BaseTestCase;
import com.bookislife.sauce.Sauce;
import com.bookislife.sauce.SauceAndroid;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mrseasons on 2015/09/14.
 */
public class AndroidFileHandlesTest extends BaseTestCase {

    private AndroidFileHandles files;

    private String testDirectory = "sauceTest";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        files = (AndroidFileHandles) Sauce.files;
    }

    public void testAbsolute() throws Exception {
        FileHandle handle = files.absolute(testDirectory);
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getName()).isEqualTo(testDirectory);

        File file = handle.toFile();
        FileHandle handle2 = files.absolute(file);
        assertThat(handle2).isNotNull();
        assertThat(handle2.toFile().getAbsolutePath()).isEqualTo(file.getAbsolutePath());

        FileHandle handle3 = files.absolute(handle, "foo");
        assertThat(handle3).isNotNull();
        assertThat(handle3.toFile().getAbsolutePath()).isNotEqualTo(file.getAbsolutePath());

        FileHandle handle4 = files.absolute(testDirectory, "foo");
        assertThat(handle4).isNotNull();
        assertThat(handle4.toFile().getAbsolutePath()).isEqualTo(handle3.toFile().getAbsolutePath());
    }

    public void testInternal() throws Exception {
        FileHandle handle = files.internal(testDirectory);
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getName()).isEqualTo(testDirectory);

        FileHandle handle2 = files.internal(testDirectory, "foo");
        assertThat(handle2).isNotNull();
    }

    public void testExternal() throws Exception {
        FileHandle handle = files.external(testDirectory);
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getName()).isEqualTo(testDirectory);

        FileHandle handle2 = files.external(testDirectory, "foo");
        assertThat(handle2).isNotNull();
    }

    public void testSdcard() throws Exception {
        FileHandle handle = files.sdcard(testDirectory);
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getName()).isEqualTo(testDirectory);

        FileHandle handle2 = files.external(testDirectory);
        assertThat(handle.toFile().getAbsolutePath()).isEqualTo(handle2.toFile().getAbsolutePath());
    }

    public void testAssets() throws Exception {
        FileHandle handle = files.assets(testDirectory);
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getName()).isEqualTo(testDirectory);

        FileHandle handle2 = files.assets(testDirectory, "foo");
        assertThat(handle2).isNotNull();
    }

    public void testFiles() throws Exception {
        FileHandle handle = files.files(testDirectory);
        Context context = SauceAndroid.getInstance().context;
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getAbsolutePath())
                .isEqualTo(context.getFilesDir().getPath()
                        + "/" + testDirectory);
    }

    public void testCaches() throws Exception {
        FileHandle handle = files.caches(testDirectory);
        Context context = SauceAndroid.getInstance().context;
        assertThat(handle).isNotNull();
        assertThat(handle.toFile().getAbsolutePath())
                .isEqualTo(context.getCacheDir().getPath()
                        + "/" + testDirectory);
    }

}