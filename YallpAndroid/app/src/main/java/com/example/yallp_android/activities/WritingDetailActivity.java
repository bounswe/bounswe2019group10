package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.WritingExerciseAnswerFragment;
import com.example.yallp_android.fragments.WritingExerciseScoreFragment;

public class WritingDetailActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_writing_detail);
        Intent i = getIntent();
        int writingId = Integer.parseInt(i.getStringExtra("writingId"));
        String answerText = i.getStringExtra("answerText");
        String imageUrl = i.getStringExtra("imageUrl");
        String token = sharedPref.getString("token", null);
        String evaluatorName = i.getStringExtra("evaluatorName");
        int score = Integer.parseInt(i.getStringExtra("score"));
        boolean isScored = i.getBooleanExtra("isScored", false);
        placeWritingDetailsFragment(writingId, answerText, token, imageUrl, 0);
        placeScoreFragment(evaluatorName, score, isScored);

    }

    public void placeWritingDetailsFragment(int writingId, String answerText, String token, String imageUrl, int writingResultId) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WritingExerciseAnswerFragment.newInstance(writingId, answerText, token, "user", imageUrl, writingResultId);
        ft.replace(R.id.answerLayout, fragment);
        ft.commit();

    }

    public void placeScoreFragment(String evaluatorName, int score, boolean isScored) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WritingExerciseScoreFragment.newInstance(evaluatorName, score, "user", isScored);
        ft.replace(R.id.scoreLayout, fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), CompletedWritingExerciseActivity.class);
        startActivity(i);
        finish();
    }
}
