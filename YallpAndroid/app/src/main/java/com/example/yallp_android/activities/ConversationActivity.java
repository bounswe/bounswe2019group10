package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.MessageListAdapter;
import com.example.yallp_android.helper.TabHelper;
import com.example.yallp_android.models.Conversation;
import com.example.yallp_android.models.Message;
import com.example.yallp_android.models.SendMessage;
import com.example.yallp_android.util.RetroClients.MessageRetroClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {

    private ArrayList<String> messageSenderList = new ArrayList<>();
    private ArrayList<String> messageDateList = new ArrayList<>();
    private ArrayList<String> messageContentList = new ArrayList<>();
    private Message[] messages;
    private SharedPreferences sharedPref;
    private ListView messageList;
    private MessageListAdapter adapter;

    private EditText sendMessageContent;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_conversation);

        messageList = findViewById(R.id.messageList);
        TextView conversationName = findViewById(R.id.conversationUserName);
        if (getIntent().getStringExtra("sendTo") != null) {
            username = getIntent().getStringExtra("sendTo");
            conversationName.setText(username);
        }
        conversationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    profileVisitWithName(username);
            }
        });

        sendMessageContent = findViewById(R.id.sendMessageContent);
        ImageButton sendMessageButton = findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendMessageContent.getText().toString().length() > 0)
                    sendMessage();
            }
        });

        if (getIntent().hasExtra("conversationId"))
            getConversation();

    }

    private void getConversation() {

        Call<Conversation> call;

        String token = "Bearer " + sharedPref.getString("token", null);
        call = MessageRetroClient.getInstance()
                .getMessageApi()
                .getConversation(token, getIntent().getIntExtra("conversationId", 0));

        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                messages = response.body().getMessages();
                for (int i = 0; i < messages.length; i++) {
                    messageSenderList.add(messages[i].getSenderUsername() + ":");

                    SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    SimpleDateFormat after = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                    Date date = new Date();
                    try {
                        date = before.parse(messages[i].getMessageTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    messageDateList.add(after.format(date));
                    messageContentList.add(messages[i].getMessageText());
                }

                adapter = new MessageListAdapter(getApplicationContext(), messageSenderList, messageDateList, messageContentList, sharedPref);
                messageList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {

            }
        });

    }

    private void sendMessage() {

        String sendMessageText = sendMessageContent.getText().toString();
        String sendTo = getIntent().getStringExtra("sendTo");
        SendMessage sendMessage = new SendMessage(sendMessageText, sendTo);
        String token = "Bearer " + sharedPref.getString("token", null);
        Call<Message> call = MessageRetroClient.getInstance()
                .getMessageApi()
                .sendMessage(token, sendMessage);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Toast.makeText(getBaseContext(), "Your message has been sent!", Toast.LENGTH_LONG).show();
                refreshPage(response.body().getConversationId());
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    private void profileVisitWithName(String username){
        Intent i = new Intent(getApplicationContext(),ProfileVisitPageActivity.class);
        i.putExtra("userName",username);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomePageActivity.class);
        i.putExtra("tabNumber", TabHelper.Companion.getMESSAGE_TAB_NUMBER());
        startActivity(i);
        finish();
    }

    private void refreshPage(int conversationId) {
        Intent i = new Intent(this, ConversationActivity.class);
        i.putExtra("conversationId", conversationId);
        i.putExtra("sendTo", getIntent().getStringExtra("sendTo"));
        startActivity(i);
        finish();
    }
}
