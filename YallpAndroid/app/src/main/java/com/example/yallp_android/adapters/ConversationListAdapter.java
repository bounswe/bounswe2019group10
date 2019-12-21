package com.example.yallp_android.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationListAdapter extends BaseAdapter {

    private ArrayList<String> messageSenderList;
    private ArrayList<String> messageLastDateList;
    private ArrayList<Boolean> newMessageList;
    private LayoutInflater layoutInflater;
    private SharedPreferences sharedPref;
    private Context context;

    public ConversationListAdapter(Context aContext, ArrayList<String> messageSenderList, ArrayList<String> messageLastDateList,
                                   ArrayList<Boolean> newMessageList,SharedPreferences sharedPref) {
        this.messageSenderList = messageSenderList;
        this.messageLastDateList = messageLastDateList;
        this.newMessageList = newMessageList;
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
        ViewHolderConversation conversationHolder;

        v = layoutInflater.inflate(R.layout.user_conversation, null);
        conversationHolder = new ViewHolderConversation();
        conversationHolder.sender = v.findViewById(R.id.senderName);
        conversationHolder.lastMessageDate = v.findViewById(R.id.conversationDate);
        conversationHolder.newMessage = v.findViewById(R.id.newMessage);
        v.setTag(conversationHolder);

        conversationHolder.sender.setText(messageSenderList.get(position));
        conversationHolder.lastMessageDate.setText(messageLastDateList.get(position));
        if(!newMessageList.get(position))
        {
            conversationHolder.sender.setTypeface(null, Typeface.BOLD);
            conversationHolder.lastMessageDate.setTypeface(null, Typeface.BOLD);
            conversationHolder.newMessage.setText("New Message!");
            conversationHolder.newMessage.setTypeface(null, Typeface.BOLD);
        }else
        {
            conversationHolder.sender.setTypeface(null, Typeface.NORMAL);
            conversationHolder.lastMessageDate.setTypeface(null, Typeface.NORMAL);
            conversationHolder.newMessage.setText("");
            conversationHolder.newMessage.setTypeface(null, Typeface.NORMAL);
        }

        return v;
    }

    static class ViewHolderConversation {
        TextView sender;
        TextView lastMessageDate;
        TextView newMessage;
    }

}

