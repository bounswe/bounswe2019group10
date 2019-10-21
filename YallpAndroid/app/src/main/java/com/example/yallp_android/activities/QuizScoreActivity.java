package com.example.yallp_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;

public class QuizScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_score);

        TextView scoreText = findViewById(R.id.scoreText);
        if(getIntent().getExtras()!=null){
            int level = getIntent().getExtras().getInt("level");
            int score = getIntent().getExtras().getInt("score");
            String text = "Your score is: " + score+ " \n Your level is: " + level;
            scoreText.setText(text);
        }

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
