package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.yallp_android.ExpandableTextView;
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
    TextView seeFullBio;
    ExpandableTextView expandableTextView;

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

        expandableTextView = findViewById(R.id.expandableTextView);
        seeFullBio = findViewById(R.id.seeFullBio);
        seeFullBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableTextView.changeTrim();
                expandableTextView.setText();
          //      expandableTextView.requestFocusFromTouch();
                if(seeFullBio.getText() == getResources().getString(R.string.see_full_bio)){
                    seeFullBio.setText(R.string.hide_bio);
                }else{
                    seeFullBio.setText(R.string.see_full_bio);
                }
            }
        });
        seeFullBio.callOnClick();


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

                    Log.e("e",userInfo.getUsername());
                    editor  .putString("username",userInfo.getUsername())
                            .putString("mail", userInfo.getMail())
                            .putString("name", userInfo.getName())
                            .putString("surname", userInfo.getSurname())
                            .putString("bio", userInfo.getBio())
                            .commit();
                    TextView usernameTextView = findViewById(R.id.profileUsername);
                    TextView mailTextView =  findViewById(R.id.profileMail);
                    String usernameText = sharedPref.getString("username", "username");
                    if(!(sharedPref.getString("name", "").equals("") && sharedPref.getString("surname", "").equals("") )){
                        usernameText += " ( " ;
                        usernameText += sharedPref.getString("name", "") ;
                        usernameText += " " ;
                        usernameText += sharedPref.getString("surname", "") ;
                        usernameText += " )" ;
                    }
                    usernameTextView.setText(usernameText);
                    mailTextView.setText(sharedPref.getString("mail", "mail"));
                    expandableTextView.setText(sharedPref.getString("bio", ""));

                    if(expandableTextView.getText().equals("")){
                        seeFullBio.setVisibility(View.GONE);
                        expandableTextView.setVisibility(View.GONE);
                    }
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
