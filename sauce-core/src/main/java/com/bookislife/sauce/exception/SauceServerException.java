package com.bookislife.sauce.exception;

/**
 * The class represents the exception occurs when making a remote connection.
 * <p></p>
 *
 * @author SidneyXu
 */
public class SauceServerException extends SauceException {
    public SauceServerException(int code, String msg) {
        super(code, msg);
    }

    public SauceServerException(String msg) {
        super(msg);
    }

    public SauceServerException(Throwable cause) {
        super(cause);
    }
}
