package com.bookislife.sauce.exception;

/**
 * Created by mrseasons on 2015/10/08.
 */
public class SauceTimeoutException extends SauceException {
    public SauceTimeoutException(final Throwable cause) {
        super(cause);
    }

    public SauceTimeoutException(final String msg) {
        super(msg);
    }

    public SauceTimeoutException(final int code, final String msg) {
        super(code, msg);
    }

    public SauceTimeoutException() {
        super("Connection timeout.");
    }

}
