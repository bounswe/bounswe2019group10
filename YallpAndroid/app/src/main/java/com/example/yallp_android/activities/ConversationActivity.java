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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.MessageListAdapter;
import com.example.yallp_android.models.Conversation;
import com.example.yallp_android.models.Message;
import com.example.yallp_android.models.SendMessage;
import com.example.yallp_android.util.RetroClients.MessageRetroClient;

import java.util.ArrayList;

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

    private TextView conversationName;
    private EditText sendMessageContent;
    private Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_conversation);

        messageList = findViewById(R.id.messageList);
        conversationName = findViewById(R.id.conversationName);
        conversationName.setText("Conversation with " + getIntent().getStringExtra("sendTo"));
        sendMessageContent = findViewById(R.id.sendMessageContent);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendMessageContent.getText().toString().length()>0)
                    sendMessage();
            }
        });

        Call<Conversation> call;

        String token = "Bearer " + sharedPref.getString("token", null);
        call = MessageRetroClient.getInstance()
                                 .getMessageApi()
                                 .getConversation(token, getIntent().getIntExtra("conversationId", 0));

        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                messages = response.body().getMessages();
                for(int i = 0; i < messages.length; i++){
                    messageSenderList.add(messages[i].getSenderUsername() + ":");
                    messageDateList.add(messages[i].getMessageTime().substring(0,10));
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
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,HomePageActivity.class);
        startActivity(i);
        finish();
    }
}
