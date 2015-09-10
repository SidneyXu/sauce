package com.bookislife.sauce;

import android.test.InstrumentationTestCase;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by mrseasons on 2015/09/08.
 */
public abstract class BaseTestCase extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Sauce.initialize(new SauceAndroid(getInstrumentation().getContext()));
    }

    //    public void test01() throws IOException {
    //        String expected = "hello world";
    //        FileHandle handle = files.sdcard("foo.txt");
    //        handle.writeString(expected);
    //
    //        String actual = handle.readString();
    //        assertThat(actual).isEqualTo(expected);
    //    }
}
