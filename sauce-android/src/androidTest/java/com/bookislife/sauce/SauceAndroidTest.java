package com.bookislife.sauce;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Created by SidneyXu on 2015/09/16.
 */
public class SauceAndroidTest extends TestCase {

    public void testNotInitializePlatform() throws Exception {
        try {
            SauceAndroid.getInstance();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (RuntimeException e) {
            assertThat(e).isNotNull().hasMessageContaining("Sauce.initialize()");
        }
    }
}