package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Token;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private LinearLayout layout;

    private String userUsername = "default";
    private String userMail = "default";
    private String userName = "default";
    private String userSurname = "default";
    private String userBio = "default";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Melih_debug", "got to profile page");


        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        getProfileInfo(sharedPref, editor);


        userUsername = sharedPref.getString("username", null);
        userMail = sharedPref.getString("mail", null);
        //userName = sharedPref.getString("name", null);
        //userSurname = sharedPref.getString("surname", null);
        //userBio = sharedPref.getString("bio", null);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_profile);
        GridLayout englishLayout = findViewById(R.id.englishLayout);
        TextView usernameTextView = (TextView) findViewById(R.id.profileUsername);
        TextView mailTextView = (TextView) findViewById(R.id.profileMail);

        usernameTextView.setText(userUsername);

        mailTextView.setText(userMail);

        englishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void getProfileInfo(final SharedPreferences sharedPref, final SharedPreferences.Editor editor){
        if(sharedPref.getBoolean("newSession", false)){

            Call<UserInfo> call;

            String token = sharedPref.getString("token",null);
            Log.i("Melih_debug", token);
            call = UserRetroClient.getInstance().getUserApi().getProfileInfo("Bearer " + token);

            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    if(response.isSuccessful()){

                        UserInfo userInfo = response.body();

                        editor  .putString("username",userInfo.getUsername())
                                .putString("mail", userInfo.getMail())
                                //.putString("name", userInfo.getName())
                                //.putString("surname", userInfo.getSurname())
                                //.putString("bio", userInfo.getBio())
                                .commit();
                    }
                    else{

                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.i("Melih_debug", "profile call didnt work: " + t.getMessage());
                }
            });


            editor.putBoolean("newSession", false);
            editor.apply();
        }
    }
}
