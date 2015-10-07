package com.bookislife.sauce.sample;

import android.support.v4.app.Fragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SidneyXu on 2015/10/07.
 */
public class MainActivity extends BaseActivity {


    @Override
    protected Fragment getFragment() {
        List<String> list = Arrays.asList("ImageActivity");
        Fragment fragment = MainFragment.newInstance(list, "com.bookislife.sauce.activities");
        return fragment;
    }
}
