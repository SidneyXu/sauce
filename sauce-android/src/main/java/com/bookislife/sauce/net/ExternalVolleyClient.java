package com.bookislife.sauce.net;

import com.bookislife.sauce.exception.SauceServerException;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class ExternalVolleyClient extends BaseHttpClient {
    @Override
    public BaseHttpResponse execute(BaseHttpRequest baseHttpRequest) throws SauceServerException {
        return null;
    }

    @Override
    protected Object getExternalRequest(BaseHttpRequest baseHttpRequest) {
        return null;
    }

    @Override
    protected BaseHttpResponse getResponse(Object o) throws SauceServerException {
        return null;
    }
}
