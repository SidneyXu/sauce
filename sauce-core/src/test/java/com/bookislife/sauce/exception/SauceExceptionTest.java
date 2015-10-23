package com.bookislife.sauce.exception;

import junit.framework.TestCase;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/10/23.
 */
public class SauceExceptionTest extends TestCase {

    public void testInitialize() throws Exception {
        SauceException e = new SauceException("foo");
        assertThat(e.getMessage()).isEqualTo("foo");

        e = new SauceException(100, "foo");
        assertThat(e.getMessage()).isEqualTo("foo");
        assertThat(e.getCode()).isEqualTo(100);

        FileNotFoundException fnfe = new FileNotFoundException();
        e = new SauceException(fnfe);
        assertThat(e.getCause()).isEqualTo(fnfe);
    }

    public void testServerException() throws Exception {
        SauceServerException e = new SauceServerException("foo");
        assertThat(e).isInstanceOf(SauceException.class);
        assertThat(e.getMessage()).isEqualTo("foo");

        e = new SauceServerException(100, "foo");
        assertThat(e).isInstanceOf(SauceException.class);
        assertThat(e.getMessage()).isEqualTo("foo");
        assertThat(e.getCode()).isEqualTo(100);

        FileNotFoundException fnfe = new FileNotFoundException();
        e = new SauceServerException(fnfe);
        assertThat(e).isInstanceOf(SauceException.class);
        assertThat(e.getCause()).isEqualTo(fnfe);
    }

    public void testTimeoutException() throws Exception {
        SauceTimeoutException e = new SauceTimeoutException("foo");
        assertThat(e).isInstanceOf(SauceException.class);
        assertThat(e.getMessage()).isEqualTo("foo");

        e = new SauceTimeoutException(100, "foo");
        assertThat(e).isInstanceOf(SauceException.class);
        assertThat(e.getMessage()).isEqualTo("foo");
        assertThat(e.getCode()).isEqualTo(100);

        FileNotFoundException fnfe = new FileNotFoundException();
        e = new SauceTimeoutException(fnfe);
        assertThat(e).isInstanceOf(SauceException.class);
        assertThat(e.getCause()).isEqualTo(fnfe);
    }
}