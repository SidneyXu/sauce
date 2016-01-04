package com.bookislife.sauce.sample;

import com.bookislife.sauce.sample.future.SimpleListActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SidneyXu on 2016/01/04.
 */
public class TestSimpleListActivity extends SimpleListActivity {


    @Override
    protected List<String> getActivities() {
        return Arrays.asList("ImageActivity",
                "RestActivity");
    }

    @Override
    protected String getDefaultPackage() {
        return "com.bookislife.sauce.sample.activities";
    }
}
