package com.bookislife.sauce.exception;

/**
 * The class represents the exception occurs when timeout occurs.
 * <p></p>
 *
 * @author SidneyXu
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
