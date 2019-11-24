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
            v = layoutInflater.inflate(R.layout.completed_writing_list_row, null);
            holder = new ViewHolder();
            holder.writingName = (TextView) v.findViewById(R.id.writingName);
            holder.writingStatus = (TextView) v.findViewById(R.id.writingStatus);
            holder.writingScore = (TextView) v.findViewById(R.id.writingScore);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        if(listData.get(position).isScored()){
            holder.writingName.setText(listData.get(position).getWritingName());
            holder.writingStatus.setText("Scored");
            holder.writingScore.setText(listData.get(position).getScore()+"/10");
            holder.writingStatus.setTextColor(Color.parseColor("#56b531"));
        }else{
            holder.writingName.setText(listData.get(position).getWritingName());
            holder.writingStatus.setText("Pending");
            holder.writingScore.setVisibility(View.GONE);
            holder.writingStatus.setTextColor(Color.parseColor("#d1cc2c"));
        }
        return v;
    }

    static class ViewHolder {
        TextView writingName;
        TextView writingStatus;
        TextView writingScore;
    }
}
