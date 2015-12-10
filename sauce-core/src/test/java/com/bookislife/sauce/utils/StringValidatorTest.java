package com.bookislife.sauce.utils;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static com.bookislife.sauce.utils.StringValidator.*;

/**
 * Created by SidneyXu on 2015/12/10.
 */
public class StringValidatorTest extends TestCase {

    public void testIsAlphabet() throws Exception {
        assertThat(isAlphabet("abc")).isTrue();
        assertThat(isAlphabet("abc1")).isFalse();
        assertThat(isAlphabet("1abc")).isFalse();
        assertThat(isAlphabet("a1bc")).isFalse();
        assertThat(isAlphabet("123")).isFalse();

    }

    public void testIsNumber() throws Exception {
        assertThat(isNumber("123")).isTrue();
        assertThat(isNumber("123.4")).isTrue();
        assertThat(isNumber("0.123")).isTrue();
        assertThat(isNumber("123a")).isFalse();
        assertThat(isNumber("a123")).isFalse();
        assertThat(isNumber("1a23")).isFalse();
        assertThat(isNumber("abc")).isFalse();
    }

    public void testIsAscii() throws Exception {

    }

    public void testIsMailAddress() throws Exception {

    }

    public void testIsHiragana() throws Exception {

    }

    public void testIsKatakana() throws Exception {

    }

    public void testIsHalfWidth() throws Exception {

    }

    public void testIsFullWidth() throws Exception {

    }

    public void testContainsProhibitedFileName() throws Exception {

    }

    public void testIsShorterThanOrEqualToLength() throws Exception {

    }

    public void testIsLongerThanOrEqualToLength() throws Exception {

    }

    public void testIsLengthInRangeOf() throws Exception {

    }

    public void testIsEmpty() throws Exception {

    }
}