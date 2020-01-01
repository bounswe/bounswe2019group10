package com.example.yallp_android.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yallp_android.R;

import java.util.ArrayList;

public class MessageListAdapter extends BaseAdapter {

    private ArrayList<String> messageSenderList;
    private ArrayList<String> messageDateList;
    private ArrayList<String> messageContentList;
    private LayoutInflater layoutInflater;
    private SharedPreferences sharedPref;

    public MessageListAdapter(Context aContext, ArrayList<String> messageSenderList, ArrayList<String> messageDateList,
                              ArrayList<String> messageContentList, SharedPreferences sharedPref) {
        this.messageSenderList = messageSenderList;
        this.messageDateList = messageDateList;
        this.messageContentList = messageContentList;
        layoutInflater = LayoutInflater.from(aContext);
        this.sharedPref = sharedPref;
    }

    @Override
    public Object getItem(int position) {
        return messageSenderList.get(position);
    }

    @Override
    public int getCount() {
        return messageSenderList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup vg) {
        ViewHolderMessage messageHolder = new ViewHolderMessage();

        String username = sharedPref.getString("username","");

        if(messageSenderList.get(position).equals(username+":")){

            v = layoutInflater.inflate(R.layout.my_message, null);
            messageHolder.messageContent = v.findViewById(R.id.message_body);
            v.setTag(messageHolder);
            messageHolder.messageContent.setText(messageContentList.get(position));

        }else{

            v = layoutInflater.inflate(R.layout.their_message, null);
            messageHolder.sender = v.findViewById(R.id.name);
            messageHolder.messageContent = v.findViewById(R.id.message_body);
            v.setTag(messageHolder);
            if((position != 0 && messageSenderList.get(position-1).equals(username+":"))
                    || (position == 0 && !messageSenderList.get(0).equals(username+":") )){
                messageHolder.sender.setText(messageSenderList.get(position));
            }else{
                messageHolder.sender.setVisibility(View.GONE);
            }
            messageHolder.messageContent.setText(messageContentList.get(position));
        }

        return v;
    }

    static class ViewHolderMessage {
        TextView sender;
        TextView messageContent;
    }

}

