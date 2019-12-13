package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Comment;

import java.util.ArrayList;

public class CommentsAdapter extends BaseAdapter {

    private ArrayList<Comment> listData;
    private LayoutInflater layoutInflater;


    public CommentsAdapter(Context aContext, ArrayList<Comment> listData) {
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
            v = layoutInflater.inflate(R.layout.comment_list_row, null);
            holder = new ViewHolder();
            holder.comment = (TextView) v.findViewById(R.id.comment);
            holder.comentatorName = (TextView) v.findViewById(R.id.comentatorName);
            holder.date = (TextView) v.findViewById(R.id.date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.comment.setText(listData.get(position).getComment());
        holder.comentatorName.setText("-"+listData.get(position).getCommentatorName());
        //holder.date.setVisibility(View.GONE);

        return v;
    }

    static class ViewHolder {
        TextView comment;
        TextView comentatorName;
        TextView date;
    }

}
