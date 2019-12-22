package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.NonCompletedAssignments;

import java.util.ArrayList;

public class NonCompletedAssignmentAdapter extends BaseAdapter {

    private ArrayList<NonCompletedAssignments> listData;
    private LayoutInflater layoutInflater;


    public NonCompletedAssignmentAdapter(Context aContext, ArrayList<NonCompletedAssignments> listData) {
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
            v = layoutInflater.inflate(R.layout.noncompleted_assignments_list_row, null);
            holder = new ViewHolder();
            holder.writingName = v.findViewById(R.id.writingName);
            holder.writerName = v.findViewById(R.id.writerName);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.writingName.setText(listData.get(position).getWritingName());
        holder.writerName.setText("Written by " + listData.get(position).getMemberName());
        return v;
    }

    static class ViewHolder {
        TextView writingName;
        TextView writerName;
    }
}
