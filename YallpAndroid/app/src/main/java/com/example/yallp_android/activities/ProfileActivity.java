package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView addNewLanguage;

    /*private LinearLayout layout;

    private String userName = "default";
    private String userSurname = "default";
    private String userBio = "default";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();





        String userUsername = sharedPref.getString("username", null);
        String userMail = sharedPref.getString("mail", null);
        //userName = sharedPref.getString("name", null);
        //userSurname = sharedPref.getString("surname", null);
        //userBio = sharedPref.getString("bio", null);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_profile);
        GridLayout englishLayout = findViewById(R.id.englishLayout);
        this.addNewLanguage = findViewById(R.id.addNewLanguage);
        this.addNewLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddLangugeActivity.class);
                startActivity(i);
                finish();
            }
        });


        englishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),QuizActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        updateProfileInfo(sharedPref, editor);
    }

    public void updateProfileInfo(final SharedPreferences sharedPref, final SharedPreferences.Editor editor){
        Call<UserInfo> call;

        String token = sharedPref.getString("token",null);
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
                    TextView usernameTextView = findViewById(R.id.profileUsername);
                    TextView mailTextView =  findViewById(R.id.profileMail);
                    usernameTextView.setText(sharedPref.getString("username", "username"));
                    mailTextView.setText(sharedPref.getString("mail", "mail"));

                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });

        TextView englishLevel = findViewById(R.id.englishLangLevel);
        if(getIntent().getExtras()!=null){
            String levelText = getIntent().getExtras().getString("levelText");

            englishLevel.setText(levelText);
        }

    }

    private void logout(){
        clearApplicationData();
        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }


    private void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    private static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}
