package com.bookislife.sauce.exception;

/**
 * The class represents the exception occurs when using sauce Library.
 * <p></p>
 *
 * @author SidneyXu
 */
public class SauceException extends Exception {

    protected int code;
    private String msg;

    public SauceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SauceException(String msg) {
        this(-1, msg);
    }

    public SauceException(final Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

}