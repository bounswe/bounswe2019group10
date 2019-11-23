package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.CompletedWritingsAdapter;
import com.example.yallp_android.models.CompletedWritings;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedWritingExercises extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private ListView writingList;
    private CompletedWritingsAdapter adapter;
    ArrayList<CompletedWritings> completedWritingExercises = new ArrayList<CompletedWritings>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list_of_writings);
        writingList = findViewById(R.id.writingList);
        writingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

            }
        });

        Call<CompletedWritings[]> call;

        call = WritingRetroClient
                .getInstance()
                .getWritingApi()
                .getCompletedWritingExercises("Bearer " + sharedPref.getString("token", null));

        call.enqueue(new Callback<CompletedWritings[]>() {
            @Override
            public void onResponse(Call<CompletedWritings[]> call, Response<CompletedWritings[]> response) {
                if(response.isSuccessful()){
                    Collections.addAll(completedWritingExercises,response.body());
                    adapter = new CompletedWritingsAdapter(getApplicationContext(),completedWritingExercises);
                    writingList.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CompletedWritings[]> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
        finish();
    }
}
