package com.bookislife.sauce.net;

import java.net.URL;
import java.util.Map;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class BaseHttpRequest {

    private final URL url;
    private final int method;
    private final Map<String, String> headers;
    private final Body body;

    public BaseHttpRequest(URL url, int method, Map<String, String> headers, Body body) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public static class Body {

    }
}
