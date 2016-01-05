package com.bookislife.sauce.sample.future;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * Created by SidneyXu on 2016/01/05.
 */
public class PopupWindowHelper {

    public static void popup(Context context, View parent, int listId, int layoutId, BaseAdapter adapter) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        ListView listView = (ListView) view.findViewById(listId);
        listView.setAdapter(adapter);
        listView.setSelected(false);

        final PopupWindow popupWindow = new PopupWindow(view, parent.getWidth() - 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();

        popupWindow.showAsDropDown(parent, 3, -8);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
            }
        });
    }
}
