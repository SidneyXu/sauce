package com.bookislife.sauce.net;

import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.URL;

/**
 * Created by SidneyXu on 2015/12/22.
 */
public class AndroidHttpHandle extends HttpHandle {

    AndroidHttpHandle(URL url) {
        // TODO: 12/22/15  
    }

    @Override
    public byte[] readBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public String readString() throws IOException {
        return null;
    }

    @Override
    public String tryReadString() {
        return null;
    }
}
