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
import com.example.yallp_android.adapters.NonCompletedAssignmentAdapter;
import com.example.yallp_android.models.NonCompletedAssignments;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NonCompletedAssignmentsActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private ListView writingListToBeScored;
    private NonCompletedAssignmentAdapter adapter;
    ArrayList<NonCompletedAssignments> nonCompletedAssignments = new ArrayList<NonCompletedAssignments>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_completed_writings_or_noncompleted_assignments_list);

        writingListToBeScored = findViewById(R.id.writingList);

        writingListToBeScored.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), GiveScoreForWritingActivity.class);
                i.putExtra("writingId", nonCompletedAssignments.get(position).getWritingId() + "");
                i.putExtra("answerText", nonCompletedAssignments.get(position).getAnswerText());
                i.putExtra("imageUrl", nonCompletedAssignments.get(position).getImageUrl());
                i.putExtra("username", nonCompletedAssignments.get(position).getMemberName());
                i.putExtra("writingResultId", nonCompletedAssignments.get(position).getId()+"");
                startActivity(i);
               finish();
            }
        });

        Call<NonCompletedAssignments[]> call;

        call = WritingRetroClient
                .getInstance()
                .getWritingApi()
                .nonCompletedAssignments("Bearer " + sharedPref.getString("token", null));

        call.enqueue(new Callback<NonCompletedAssignments[]>() {
            @Override
            public void onResponse(Call<NonCompletedAssignments[]> call, Response<NonCompletedAssignments[]> response) {
                if (response.isSuccessful()) {
                    Collections.addAll(nonCompletedAssignments, response.body());
                    adapter = new NonCompletedAssignmentAdapter(getApplicationContext(), nonCompletedAssignments);
                    writingListToBeScored.setAdapter(adapter);
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NonCompletedAssignments[]> call, Throwable t) {

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
