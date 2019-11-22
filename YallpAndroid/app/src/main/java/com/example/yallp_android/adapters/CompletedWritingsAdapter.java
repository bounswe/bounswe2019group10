package com.example.yallp_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.CompletedWritings;

import java.util.ArrayList;

public class CompletedWritingsAdapter extends BaseAdapter {

    private ArrayList<CompletedWritings> listData;
    private LayoutInflater layoutInflater;


    public CompletedWritingsAdapter(Context aContext, ArrayList<CompletedWritings> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v == null) {
            v = layoutInflater.inflate(R.layout.writing_list, null);
            holder = new ViewHolder();
            holder.writingName = (TextView) v.findViewById(R.id.writingName);
            holder.writingStatus = (TextView) v.findViewById(R.id.writingStatus);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        holder.writingName.setText("Effects of consuming alcohol");
        if(listData.get(position).isScored()){
            holder.writingStatus.setText("Scored");
            holder.writingStatus.setTextColor(Color.parseColor("#56b531"));
        }else{
            holder.writingStatus.setText("Pending");
        }
        return v;
    }

    static class ViewHolder {
        TextView writingName;
        TextView writingStatus;
    }
}
