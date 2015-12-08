package com.bookislife.sauce.sample.future;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;

/**
 * Created by SidneyXu on 12/8/15.
 */
public abstract class SimpleListActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "fragment";
    private static final int id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout container = new FrameLayout(this);
        container.setId(1);
        setContentView(container);

        replace(getFragment());
    }

    protected abstract List<String> getActivities();

    protected abstract String getDefaultPackage();

    protected void replace(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null) {
            fragmentManager.beginTransaction().add(id, targetFragment, TAG_FRAGMENT).commit();
        } else {
            fragmentManager.beginTransaction().
                    replace(id, targetFragment, TAG_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private ListFragment getFragment() {
        return new ListFragment() {
            @Override
            public void onViewCreated(View view, Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                setListAdapter(new ArrayAdapter<String>(
                        SimpleListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1,
                        getActivities()
                ));
            }

            @Override
            public void onListItemClick(ListView l, View v, int position, long id) {
                String s = (String) getListAdapter().getItem(position);
                try {
                    String className = null;
                    if (null == getDefaultPackage()) {
                        className = s;
                    } else {
                        className = getDefaultPackage() + "." + s;
                    }
                    Class clazz = Class.forName(className);

                    Intent it = new Intent();
                    it.setClass(getActivity(), clazz);
                    startActivity(it);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
