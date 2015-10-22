package com.bookislife.sauce.web;

import com.bookislife.sauce.exception.SauceException;
import junit.framework.TestCase;

/**
 * Created by mrseasons on 2015/10/22.
 */
public class HttpHandleTest extends TestCase {

    public void testCore() throws SauceException {
        HttpHandle httpHandle = HttpHandle.from("http://jsonplaceholder.typicode.com")
                .path("/posts/1")
                .query("v", "1.0")
                .queryWithUTF8("q", "Calvin and Hobbes")
                .build();
        String result = httpHandle.readString("UTF-8");
        System.out.println(result);

    }
}