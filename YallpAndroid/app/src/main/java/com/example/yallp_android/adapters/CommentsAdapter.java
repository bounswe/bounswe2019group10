package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            holder.comment =  v.findViewById(R.id.comment);
            holder.comentatorName =  v.findViewById(R.id.comentatorName);
            holder.date =  v.findViewById(R.id.date);
            holder.rate =  v.findViewById(R.id.rate);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.comment.setText(listData.get(position).getComment());
        holder.comentatorName.setText("-"+listData.get(position).getCommentatorName());
        String date = listData.get(position).getUpdatedAt();

        SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat after = new SimpleDateFormat("d MMM yyyy");
        Date dateToWrite = new Date();
        try {
            dateToWrite = before.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = after.format(dateToWrite);
        holder.date.setText(date);
        float rating = (float) listData.get(position).getRating();
        holder.rate.setRating(rating);

        return v;
    }

    static class ViewHolder {
        TextView comment;
        TextView comentatorName;
        TextView date;
        RatingBar rate;
    }

}
