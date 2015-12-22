package com.bookislife.sauce.net;

import java.net.URL;
import java.util.Map;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class BaseHttpRequest {

    public static interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
    }

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
        private String contentType;

        public String getContentType() {
            return contentType;
        }
    }

    public URL getUrl() {
        return url;
    }

    public Body getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        switch (method) {
            case Method.DELETE:
                return "DELETE";
            case Method.GET:
                return "GET";
            case Method.POST:
                return "POST";
            case Method.PUT:
                return "PUT";
            default:
                return "UNKNOWN";
        }
    }
}
