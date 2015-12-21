package com.bookislife.sauce.net;

import com.bookislife.sauce.exception.SauceServerException;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public abstract class BaseHttpClient<ExternalRequest, ExternalResponse> {

    // TODO: 12/21/15  
    protected int awaitTimeout;

    public abstract BaseHttpResponse execute(BaseHttpRequest baseHttpRequest) throws SauceServerException;

    protected abstract ExternalRequest getExternalRequest(BaseHttpRequest baseHttpRequest);

    protected abstract BaseHttpResponse getResponse(ExternalResponse externalResponse) throws SauceServerException;

}
