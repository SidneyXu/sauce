package com.bookislife.sauce.utils;

import android.content.Context;
import com.bookislife.sauce.BaseTestCase;
import com.bookislife.sauce.SauceAndroid;

import java.util.HashMap;
import java.util.Map;

import static com.bookislife.sauce.utils.Prefs.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Created by mrseasons on 2015/09/16.
 */
public class PrefsTest extends BaseTestCase {

    private Context context;
    private String prefsName = "SauceTest";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = SauceAndroid.getInstance().context;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        clear(context, prefsName);
    }

    public void testPutMap() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Peter");
        map.put("age", "10");
        putMap(context, prefsName, map);
        assertThat(getString(context, prefsName, "name", null))
                .isEqualTo("Peter");
        assertThat(getInt(context, prefsName, "age", 0))
                .isEqualTo(10);
    }

    public void testString() throws Exception {
        putString(context, prefsName, "name", "Peter");
        assertThat(getString(context, prefsName, "name", null))
                .isEqualTo("Peter");
        assertThat(getString(context, prefsName, "nick name", "nothing"))
                .isEqualTo("nothing");
    }

    public void testInt() throws Exception {
        putInt(context, prefsName, "x", 10);
        assertThat(getInt(context, prefsName, "x", 0))
                .isEqualTo(10);
        assertThat(getInt(context, prefsName, "y", 20))
                .isEqualTo(20);
    }

    public void testBoolean() throws Exception {
        putBoolean(context, prefsName, "sex", true);
        assertThat(getBoolean(context, prefsName, "sex", false))
                .isEqualTo(true);
        assertThat(getBoolean(context, prefsName, "isMale", false))
                .isEqualTo(false);
    }

    public void testLong() throws Exception {
        putLong(context, prefsName, "height", 180);
        assertThat(getLong(context, prefsName, "height", 0))
                .isEqualTo(180);
        assertThat(getLong(context, prefsName, "weight", -10))
                .isEqualTo(-10);
    }

    public void testFloat() throws Exception {
        putFloat(context, prefsName, "sum", 100.12f);
        assertThat(getFloat(context, prefsName, "sum", 0))
                .isEqualTo(100.12f);
        assertThat(getFloat(context, prefsName, "z", 111.11f))
                .isEqualTo(111.11f);
    }

    public void testRemove() throws Exception {
        putInt(context, prefsName, "x", 1);
        putInt(context, prefsName, "y", 2);
        putInt(context, prefsName, "z", 3);
        remove(context, prefsName, "y");
        assertThat(getInt(context, prefsName, "x", 0))
                .isEqualTo(1);
        assertThat(getInt(context, prefsName, "z", 0))
                .isEqualTo(3);
        assertThat(getInt(context, prefsName, "y", 0))
                .isEqualTo(0);
    }

    public void testClear() throws Exception {
        putInt(context, prefsName, "x", 1);
        putInt(context, prefsName, "y", 2);
        putInt(context, prefsName, "z", 3);
        clear(context, prefsName);
        assertThat(getInt(context, prefsName, "x", 0))
                .isEqualTo(0);
        assertThat(getInt(context, prefsName, "z", 1))
                .isEqualTo(1);
        assertThat(getInt(context, prefsName, "y", 2))
                .isEqualTo(2);
    }

    public void testGetWrongType() throws Exception {
        putInt(context, prefsName, "x", 1);
        try {
            getString(context, prefsName, "x", "foo");
            failBecauseExceptionWasNotThrown(ClassCastException.class);
        } catch (ClassCastException e) {
            assertThat(e).isNotNull();
        }
    }
}