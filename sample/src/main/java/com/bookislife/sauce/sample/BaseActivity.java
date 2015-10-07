package com.bookislife.sauce.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SidneyXu on 2015/10/07.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replace(getFragment());
    }

    protected void replace(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null) {
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, targetFragment, TAG_FRAGMENT).commit();
        } else {
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentContainer, targetFragment, TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
    }

    protected abstract Fragment getFragment();
}
