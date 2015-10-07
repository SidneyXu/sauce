package com.bookislife.sauce.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by SidneyXu on 2015/10/07.
 */
public class MainFragment extends ListFragment {

    private List<String> list;
    private String defaultPackage;

    public static MainFragment newInstance(List<String> list, String defaultPackage) {
        MainFragment fragment = new MainFragment();
        fragment.list = list;
        fragment.defaultPackage = defaultPackage;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setListAdapter(new MainAdapter(getActivity(), list));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String s = ((MainAdapter) getListAdapter()).getItem(position);
        try {
            Class clazz = Class.forName(defaultPackage + "." + s);
            Intent it = new Intent();
            it.setClass(getActivity(), clazz);
            startActivity(it);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    class MainAdapter extends ArrayAdapter<String> {

        public MainAdapter(Context context, List<String> objects) {
            super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
        }

        @Override
        public String getItem(int position) {
            return super.getItem(position);
        }
    }
}
