package com.bookislife.sauce.net;

import java.io.InputStream;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class BaseHttpResponse {

    private InputStream inputStream;
    private int statusCode;
    private long contentLength;
    private String contentType;

    public BaseHttpResponse(InputStream inputStream,
                            int statusCode,
                            long contentLength,
                            String contentType) {
        this.inputStream = inputStream;
        this.statusCode = statusCode;
        this.contentLength = contentLength;
        this.contentType = contentType;
    }

}
