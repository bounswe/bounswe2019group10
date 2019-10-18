package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Token;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

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

    TextView usernameTextView = (TextView) findViewById(R.id.profileUsername);
    TextView mailTextView = (TextView) findViewById(R.id.profileMail);
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getProfileInfo();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        usernameTextView.setText(userUsername);

        mailTextView.setText(userMail);


        setContentView(R.layout.activity_profile);
        this.layout = findViewById(R.id.englishLayout);

        this.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(i);
            }
        });
    }

    public void getProfileInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("username", null) == null){

            Call<UserInfo> call;

            call = UserRetroClient.getInstance().getUserApi().profileInfo();

            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    if(response.isSuccessful()){
                        SharedPreferences sharedPref = getSharedPreferences("yallp",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();


                        editor.putString("username",response.body().getUserUsername());
                        editor.putString("mail", response.body().getUserMail());
                        editor.putString("name", response.body().getUserName());
                        editor.putString("surname", response.body().getUserSurname());
                        editor.putString("bio", response.body().getUserBio());

                        editor.commit();
                    }
                    else{

                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {

                }
            });

        }

        userUsername = sharedPreferences.getString("username", null);
        userMail = sharedPreferences.getString("mail", null);
        userName = sharedPreferences.getString("name", null);
        userSurname = sharedPreferences.getString("surname", null);
        userBio = sharedPreferences.getString("bio", null);
    }
}
