package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        if(sharedPref.contains("token")){
            Intent i = new Intent(this,HomePageActivity.class);
            startActivity(i);
            finish();
        }
    }
    public void getStarted(View view){
        Intent i = new Intent(this,GetStartedActivity.class);
        startActivity(i);
        finish();
    }

    public void signIn(View view){
        Intent i = new Intent(this,SignInActivity.class);
        startActivity(i);
        finish();
    }
}
