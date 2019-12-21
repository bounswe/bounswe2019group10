package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Notification;


public class NotificationsAdapter extends BaseAdapter {
    private Notification[] listData;
    private LayoutInflater layoutInflater;


    public NotificationsAdapter(Context aContext, Notification[] listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.length;
    }

    @Override
    public Object getItem(int position) {
        return listData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v == null) {
            v = layoutInflater.inflate(R.layout.notification_list_row, null);
            holder = new ViewHolder();
            holder.notificationText =  v.findViewById(R.id.notificationText);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.notificationText.setText(listData[position].getText());

        return v;
    }

    static class ViewHolder {
        TextView notificationText;
    }
}
