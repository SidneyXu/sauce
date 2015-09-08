package com.bookislife.sauce;

import android.test.InstrumentationTestCase;
import com.bookislife.sauce.files.AndroidFileHandles;
import com.bookislife.sauce.files.FileHandle;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class BaseTestCase extends InstrumentationTestCase {

    private AndroidFileHandles files;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Sauce.initialize(new SauceAndroid(getInstrumentation().getContext()));
        files = (AndroidFileHandles) Sauce.files;
    }

    public void test01() throws IOException {
        String expected = "hello world";
        FileHandle handle = files.sdcard("foo.txt");
        handle.writeString(expected);

        String actual = handle.readString();
        assertThat(actual).isEqualTo(expected);
    }
}
