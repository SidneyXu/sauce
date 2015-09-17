package com.bookislife.sauce.utils;

import android.Manifest;
import com.bookislife.sauce.BaseTestCase;

import static com.bookislife.sauce.utils.AndroidIOUtils.hasPermission;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by SidneyXu on 2015/09/17.
 */
public class AndroidIOUtilsTest extends BaseTestCase {

    public void testHasPermission() throws Exception {
        assertThat(hasPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE))
                .isTrue();
        assertThat(hasPermission(context, Manifest.permission.BATTERY_STATS))
                .isFalse();
    }

}