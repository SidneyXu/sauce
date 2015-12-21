package com.bookislife.sauce.net;

import com.bookislife.sauce.exception.SauceServerException;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
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
        // TODO: 12/21/15
        return null;
    }

    @Override
    protected BaseHttpResponse getResponse(Response response) throws SauceServerException {
        return null;
    }
}
