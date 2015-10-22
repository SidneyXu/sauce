package com.bookislife.sauce.utils;

/**
 * Created by mrseasons on 2015/10/22.
 */
public class Capture<T> {

    private T t;

    public Capture() {
    }

    public Capture(T t) {
        this.t = t;
    }

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public boolean isNotNull() {
        return t != null;
    }
}
