package com.bookislife.sauce.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by SidneyXu on 2015/10/22.
 */
public class Closer {

    private final Stack<Closeable> targets=new Stack<>();

    public <T extends Closeable> T put(T closeable){
        if(null==closeable)return closeable;
        targets.push(closeable);
        return closeable;
    }

    public void close(){
        while (!targets.isEmpty()){
            Closeable closeable=targets.pop();
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
