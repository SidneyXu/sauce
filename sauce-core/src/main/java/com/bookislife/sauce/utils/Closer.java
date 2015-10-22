package com.bookislife.sauce.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by SidneyXu on 2015/10/22.
 */
public class Closer {

    private final Stack<Closeable> targets = new Stack<>();

    private volatile boolean isClosed = false;

    public Closer() {
    }

    public Closer(Closeable closeable) {
        wrap(closeable);
    }

    public <T extends Closeable> T wrap(T closeable) {
        if (null == closeable) return null;
        targets.push(closeable);
        return closeable;
    }

    public void close() {
        while (!targets.isEmpty()) {
            Closeable closeable = targets.pop();
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
        isClosed = true;
    }

    @Override
    protected void finalize() throws Throwable {
        if (!isClosed) close();
        super.finalize();
    }
}
