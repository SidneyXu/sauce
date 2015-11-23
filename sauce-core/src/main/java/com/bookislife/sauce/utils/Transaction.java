package com.bookislife.sauce.utils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by SidneyXu on 2015/11/22.
 */
public class Transaction {

    private Queue<PanicHandler<?>> panicHandlerQueue = new LinkedList<PanicHandler<?>>();
    private Handler handler;

    public static interface Handler {
        void handle(Transaction transaction) throws Exception;

        Object recover(PanicHandler<?> panicHandler, Exception e);
    }

    public static interface PanicHandler<T> {
        T handle() throws Exception;
    }

    public static void with(Handler handler) {
        Transaction transaction = new Transaction();
        transaction.handler = handler;
        try {
            handler.handle(transaction);
        } catch (Exception e) {
            handler.recover(null, e);
        }
    }

    public <T> T panic(PanicHandler<T> panicHandler) {
        panicHandlerQueue.offer(panicHandler);
        try {
            return panicHandler.handle();
        } catch (Exception e) {
            return (T) handler.recover(panicHandler, e);
        }
    }

    public void commit() {
        panicHandlerQueue.poll();
    }
}
