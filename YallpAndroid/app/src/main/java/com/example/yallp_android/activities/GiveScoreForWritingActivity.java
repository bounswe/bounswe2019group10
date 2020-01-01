package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.WritingExerciseAnswerFragment;
import com.example.yallp_android.fragments.WritingExerciseScoreFragment;
import com.example.yallp_android.models.CompletedWritings;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiveScoreForWritingActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private EditText score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_give_score_for_writing);

        Intent i = getIntent();
        int writingId = Integer.parseInt(i.getStringExtra("writingId"));
        final int writingResultId = Integer.parseInt(i.getStringExtra("writingResultId"));
        String answerText = i.getStringExtra("answerText");
        String imageUrl = i.getStringExtra("imageUrl");
        String token = sharedPref.getString("token", null);
        String username = i.getStringExtra("username");

        placeWritingDetailsFragment(writingId, answerText, token, imageUrl, writingResultId);
        placeScoreFragment(username);


        Button giveScoreButton = findViewById(R.id.giveScoreButton);

        giveScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = findViewById(R.id.score);
                int value = Integer.parseInt(score.getText().toString());

                if (value < 0 || value > 10) {
                    score.setText("");
                    Toast.makeText(getApplicationContext(), "Score must be between 0 and 10", Toast.LENGTH_SHORT).show();
                } else {

                    Call<CompletedWritings> call;

                    call = WritingRetroClient
                            .getInstance()
                            .getWritingApi()
                            .giveScoreForWriting("Bearer " + sharedPref.getString("token", null), writingResultId, value);

                    call.enqueue(new Callback<CompletedWritings>() {
                        @Override
                        public void onResponse(Call<CompletedWritings> call, Response<CompletedWritings> response) {
                            if (response.isSuccessful()) {
                                onBackPressed();
                            } else {
                                Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CompletedWritings> call, Throwable t) {

                        }
                    });

                }
            }
        });

    }

    public void placeWritingDetailsFragment(int writingId, String answerText, String token, String imageUrl, int writingResultId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WritingExerciseAnswerFragment.newInstance(writingId, answerText, token, "evaluator", imageUrl, writingResultId);
        ft.replace(R.id.answerLayout, fragment);
        ft.commit();

    }

    public void placeScoreFragment(String evaluatorName) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WritingExerciseScoreFragment.newInstance(evaluatorName, 0, "evaluator", false);
        ft.replace(R.id.scoreLayout, fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), NonCompletedAssignmentsActivity.class);
        startActivity(i);
        finish();
    }
}
