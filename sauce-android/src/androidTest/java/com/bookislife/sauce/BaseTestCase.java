package com.bookislife.sauce;

import android.content.Context;
import android.test.InstrumentationTestCase;

/**
 * Created by SidneyXu on 2015/09/08.
 */
public abstract class BaseTestCase extends InstrumentationTestCase {

    protected Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.context = getInstrumentation().getContext();
        Sauce.initialize(new SauceAndroid(context));

    }

}
