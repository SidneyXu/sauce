package com.bookislife.sauce.net;

import java.io.InputStream;
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
        protected static final String PROTOCOL_CHARSET = "utf-8";
        private static final String CONTENT_TYPE_JSON =
                String.format("application/json; charset=%s", PROTOCOL_CHARSET);
        private static final String CONTENT_TYPE_FORM =
                String.format("application/x-www-form-urlencoded; charset=%s", PROTOCOL_CHARSET);


        private String contentType;

        private InputStream inputStream;
        private byte[] data;

        public String getContentType() {
            return contentType;
        }

        public boolean isJson() {
            return contentType.contains(CONTENT_TYPE_JSON);
        }

        public Body fromJson(byte[] data) {
            this.data = data;
            this.contentType = CONTENT_TYPE_JSON;
            return this;
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
