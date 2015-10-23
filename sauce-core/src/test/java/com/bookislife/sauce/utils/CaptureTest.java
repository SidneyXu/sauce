package com.bookislife.sauce.utils;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/10/23.
 */
public class CaptureTest extends TestCase {

    public void testCore() throws Exception {
        Capture<Integer> integerCapture = new Capture<>();
        assertThat(integerCapture.get()).isNull();
        assertThat(integerCapture.isNotNull()).isFalse();

        integerCapture = new Capture<>(1);
        assertThat(integerCapture.get()).isInstanceOf(Integer.class)
                .isEqualTo(1);
        assertThat(integerCapture.isNotNull());

        Capture<String> stringCapture = new Capture<>();
        assertThat(stringCapture.get()).isNull();
        assertThat(stringCapture.isNotNull()).isFalse();

        stringCapture = new Capture<>("foo");
        assertThat(stringCapture.get()).isInstanceOf(String.class)
                .isEqualTo("foo");
        assertThat(stringCapture.isNotNull());
    }
}