package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.NotificationsAdapter;
import com.example.yallp_android.models.Notification;
import com.example.yallp_android.util.RetroClients.NotificationRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private ListView unreadList;
    private ListView readList;
    private NotificationsAdapter unreadAdapter;
    private NotificationsAdapter readAdapter;
    private Notification[] unreadNotifications;
    private Notification[] readNotifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notifications_layout);
        unreadList = findViewById(R.id.unreadNotificationList);
        readList = findViewById(R.id.readNotificationList);
        getUnreadNotifications(sharedPref);


    }

    private void getUnreadNotifications(final SharedPreferences sharedPref){

        Call<Notification[]> call;
        call = NotificationRetroClient.getInstance().getNotificationApi().getUnreadNotifications("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                if (response.isSuccessful()) {
                    unreadNotifications = response.body();
                    unreadAdapter = new NotificationsAdapter(getBaseContext(),unreadNotifications);
                    unreadList.setAdapter(unreadAdapter);
                    getReadNotifications(sharedPref);
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {

            }
        });

    }

    private void getReadNotifications(final SharedPreferences sharedPref){

        Call<Notification[]> call;
        call = NotificationRetroClient.getInstance().getNotificationApi().getReadNotifications("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                if (response.isSuccessful()) {
                    readNotifications = response.body();
                    readAdapter = new NotificationsAdapter(getBaseContext(),readNotifications);
                    readList.setAdapter(readAdapter);
                    if(unreadNotifications.length > 0){
                        makeNotificationsRead(sharedPref);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {

            }
        });

    }
    private void makeNotificationsRead(final SharedPreferences sharedPref){

        Call<Notification[]> call;
        call = NotificationRetroClient.getInstance().getNotificationApi().makeNotificationsRead("Bearer " + sharedPref.getString("token", null),unreadNotifications);
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                if(response.isSuccessful()){

                }
                else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomePageActivity.class);
        startActivity(i);
        finish();
    }

}
