package com.bookislife.sauce.net.okhttp;

import com.bookislife.sauce.exception.SauceServerException;
import com.bookislife.sauce.net.BaseHttpClient;
import com.bookislife.sauce.net.BaseHttpRequest;
import com.bookislife.sauce.net.BaseHttpResponse;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/02/17.
 */
public class SauceOKHttpClient extends BaseHttpClient<Request, Response> {

    private OkHttpClient client;

    public SauceOKHttpClient(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public BaseHttpResponse execute(BaseHttpRequest baseHttpRequest) throws SauceServerException {
        return null;
    }

    @Override
    protected Request getExternalRequest(BaseHttpRequest baseHttpRequest) {
        Request.Builder okHttpRequestBuilder = new Request.Builder();
        okHttpRequestBuilder.url(baseHttpRequest.getUrl());
        for (Map.Entry<String, String> entry : baseHttpRequest.getHeaders().entrySet()) {
            okHttpRequestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = null;
        if (baseHttpRequest.getBody() != null) {
//            requestBody = RequestBody.create(MediaType.parse(baseHttpRequest.getBody().getContentType()),
//                    baseHttpRequest.getBodyAsString());
        }
        if(baseHttpRequest.getBody().isJson()){

        }

//        return okHttpRequestBuilder.method(baseHttpRequest.getMethodAsString(), requestBody).build();
        return null;
    }

    @Override
    protected BaseHttpResponse getResponse(Response response) throws SauceServerException {
        Map<String, String> headers = new HashMap<String, String>();
        for (String name : response.headers().names()) {
            headers.put(name, response.header(name));
        }
        ResponseBody responseBody = response.body();
        return new BaseHttpResponse(
                responseBody.byteStream(),
                response.code(),
                Long.valueOf(response.header("Content-Length")),
                response.header("Content-Type"));
    }
}
