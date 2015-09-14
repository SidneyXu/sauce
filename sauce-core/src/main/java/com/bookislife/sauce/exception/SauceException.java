package com.bookislife.sauce.exception;

/**
 * The class represents the exception occurs when using sauce Library.
 * <p/>
 *
 * @author SidneyXu
 */
public class SauceException {

    private int code;
    private String msg;

    public SauceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SauceException(String msg) {
        this(-1, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public boolean isError() {
        return code != 0;
    }
}