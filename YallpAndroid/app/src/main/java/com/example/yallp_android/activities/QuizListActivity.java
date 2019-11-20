package com.example.yallp_android.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.QuizListAdapter;
import com.example.yallp_android.models.Quiz;
import com.example.yallp_android.util.RetroClients.QuizRetroClient;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizListActivity extends AppCompatActivity implements QuizListAdapter.QuizListAdapterClickListener {
    private SharedPreferences sharedPref;
    private RecyclerView quizList;
    private QuizListAdapter adapter;
    ArrayList<Quiz> quizzes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quiz_list);
        quizList = findViewById(R.id.quizList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        quizList.setLayoutManager(linearLayoutManager);
        adapter = new QuizListAdapter(getApplicationContext(), quizzes, this);

        if (getIntent().getExtras() != null) {

            Call<Quiz[]> call;
            call = QuizRetroClient.getInstance().getQuizApi().getQuizForSpecificLevelAndLanguage("Bearer " + sharedPref.getString("token", null),
                    getIntent().getIntExtra("level", 1),
                    getIntent().getIntExtra("languageId", 1));

            call.enqueue(new Callback<Quiz[]>() {
                @Override
                public void onResponse(Call<Quiz[]> call, Response<Quiz[]> response) {
                    if (response.isSuccessful()) {
                        Collections.addAll(quizzes, response.body());
                        adapter.notifyDataSetChanged();
                        quizList.setAdapter(adapter);
                    } else {
                        Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Quiz[]> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void quizListAdapterClick(String topic, final String quizId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Last one step")
                .setMessage("Do you want to start solving " + topic + " " + quizId + " ?")
                .setIcon(R.drawable.penguin)
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                        intent.putExtra("quizId", quizId);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();
    }
}

