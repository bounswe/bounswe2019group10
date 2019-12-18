package com.example.yallp_android.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yallp_android.R;

import java.util.ArrayList;

public class MessageListAdapter extends BaseAdapter {

    private ArrayList<String> messageSenderList;
    private ArrayList<String> messageDateList;
    private ArrayList<String> messageContentList;
    private LayoutInflater layoutInflater;
    private SharedPreferences sharedPref;
    private Context context;

    public MessageListAdapter(Context aContext, ArrayList<String> messageSenderList, ArrayList<String> messageDateList,
                              ArrayList<String> messageContentList, SharedPreferences sharedPref) {
        this.messageSenderList = messageSenderList;
        this.messageDateList = messageDateList;
        this.messageContentList = messageContentList;
        layoutInflater = LayoutInflater.from(aContext);
        this.sharedPref = sharedPref;
        this.context = aContext;
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

    public View getView(final int position, View v, ViewGroup vg) {
        ViewHolderMessage messageHolder;

        v = layoutInflater.inflate(R.layout.user_message, null);
        messageHolder = new ViewHolderMessage();
        messageHolder.sender = v.findViewById(R.id.senderName);
        messageHolder.messageDate = v.findViewById(R.id.messageDate);
        messageHolder.messageContent = v.findViewById(R.id.messageContent);
        v.setTag(messageHolder);

        messageHolder.sender.setText(messageSenderList.get(position));
        messageHolder.messageDate.setText(messageDateList.get(position));
        messageHolder.messageContent.setText(messageContentList.get(position));

        return v;
    }

    static class ViewHolderMessage {
        TextView sender;
        TextView messageDate;
        TextView messageContent;
    }

}

