package com.bookislife.sauce.net;

import com.bookislife.sauce.exception.SauceServerException;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class ExternalOkHttpClient extends BaseHttpClient<Request, Response> {

    private OkHttpClient okHttpClient;

    public ExternalOkHttpClient() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(awaitTimeout, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(awaitTimeout, TimeUnit.MILLISECONDS);

    }

    @Override
    public BaseHttpResponse execute(BaseHttpRequest baseHttpRequest) throws SauceServerException {
        Request request = getExternalRequest(baseHttpRequest);
        Call call = okHttpClient.newCall(request);

        Response response;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new SauceServerException(e);
        }
        return getResponse(response);
    }

    @Override
    protected Request getExternalRequest(BaseHttpRequest baseHttpRequest) {
        Request.Builder builder = new Request.Builder();
        builder.url(baseHttpRequest.getUrl());
        for (Map.Entry<String, String> entry : baseHttpRequest.getHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = null;
        if (baseHttpRequest.getBody() != null) {
//            requestBody = RequestBody.create(MediaType.parse(baseHttpRequest.getBody().getContentType()),
//                    baseHttpRequest.getBodyAsString());
        }
        return builder.method(baseHttpRequest.getMethod(), requestBody).build();
    }

    @Override
    protected BaseHttpResponse getResponse(Response response) throws SauceServerException {
        Map<String, String> headers = new HashMap<String, String>();
        for (String name : response.headers().names()) {
            headers.put(name, response.header(name));
        }
        InputStream inputStream = null;
        ResponseBody responseBody = response.body();
        long contentLength = 0;
        String contentType = null;
        try {

            if (null != responseBody) {
                inputStream = responseBody.byteStream();
                contentLength = responseBody.contentLength();
                contentType = responseBody.contentType().toString();
            }
            return new BaseHttpResponse(inputStream,
                    response.code(),
                    contentLength,
                    contentType);
        } catch (IOException e) {
            throw new SauceServerException(e);
        }
    }
}
