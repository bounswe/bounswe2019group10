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

public class CompletedWritingExerciseActivity extends AppCompatActivity {
    private ListView writingList;
    private CompletedWritingsAdapter adapter;
    ArrayList<CompletedWritings> completedWritingExercises = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_completed_writings_or_noncompleted_assignments_list);
        writingList = findViewById(R.id.writingList);
        writingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(),WritingDetailActivity.class);
                i.putExtra("writingId",completedWritingExercises.get(position).getWritingId()+"");
                i.putExtra("answerText",completedWritingExercises.get(position).getAnswerText());
                i.putExtra("imageUrl",completedWritingExercises.get(position).getImageUrl());
                i.putExtra("evaluatorName",completedWritingExercises.get(position).getAssignedMemberName());
                i.putExtra("score",completedWritingExercises.get(position).getScore()+"");
                i.putExtra("isScored",completedWritingExercises.get(position).isScored());
                startActivity(i);
                finish();
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
        Intent i = new Intent(this,HomePageActivity.class);
        startActivity(i);
        finish();
    }
}
