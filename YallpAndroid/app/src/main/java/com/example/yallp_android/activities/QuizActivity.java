package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.QuestionFragment;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuestionFragment nextFrag= new QuestionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.questionFrameLayout, nextFrag, "questionFragment")
                .commit();
    }
}

