package com.bookislife.sauce.utils;

import android.content.Context;
import com.bookislife.sauce.BaseTestCase;

/**
 * Created by SidneyXu on 2015/12/28.
 */
public class CertificateHelperTest extends BaseTestCase {

    private Context context;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getInstrumentation().getTargetContext();
    }

    public void testValidateAppSignature() throws Exception {
        String sha1 = "47:BA:EC:DA:AE:2A:C5:A0:60:8C:5A:21:9C:DB:6B:A4:26:DF:00:C4";
        CertificateHelper helper = new CertificateHelper(sha1.replace(":", ""));
        boolean result = helper.validateAppSignature(context);
        System.out.println(result);
    }
}