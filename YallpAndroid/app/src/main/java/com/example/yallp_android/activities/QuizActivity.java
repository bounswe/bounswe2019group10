package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.QuestionFragment;

import com.example.yallp_android.models.Quiz;
import com.example.yallp_android.util.RetroClients.QuizRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizActivity extends AppCompatActivity {

    private Quiz currentQuiz;
    private int currentQuestion = 0;
    private Button submitButton;
    private int[] answers;
    private  RadioButton[] options = new RadioButton[3];
    private TextView questionNumberText;
    private boolean isQuestionChecked = false;
    private ProgressBar pbar;
    private ObjectAnimator progressAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        this.submitButton = findViewById(R.id.quizSubmitButton);
        this.questionNumberText = findViewById(R.id.questionNumberText);
        this.pbar = findViewById(R.id.pBar);

        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 0;
                int correctAnswer = currentQuiz.getQuestions()[currentQuestion].getCorrectChoiceId();
                if (!isQuestionChecked) {
                    isQuestionChecked = true;
                    submitButton.setText("Next");
                    options[0] = findViewById(R.id.option1);
                    options[1] = findViewById(R.id.option2);
                    options[2] = findViewById(R.id.option3);
                    options[0].setEnabled(false);
                    options[1].setEnabled(false);
                    options[2].setEnabled(false);

                    if (options[0].isChecked()) {
                        answer = 1;
                    }
                    else if (options[1].isChecked()) {
                        answer = 2;
                    }
                    else if (options[2].isChecked()) {
                        answer = 3;
                    }
                    if (answer == 0) {
                        Toast.makeText(getBaseContext(), "Please answer the question to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (correctAnswer == answer) {
                        Toast.makeText(getBaseContext(), "Correct!!", Toast.LENGTH_SHORT).show();
                        options[correctAnswer-1].setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_check,0);

                    }
                    else{
                        Toast.makeText(getBaseContext(), "Wrong!!", Toast.LENGTH_SHORT).show();
                        options[correctAnswer-1].setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
                        options[answer-1].setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_close,0);
                    }
                }
                else if (isQuestionChecked) {
                    isQuestionChecked = false;
                    submitButton.setText("Check");
                    answers[currentQuestion] = answer;
                    currentQuestion++;
                    progressAnimator = ObjectAnimator.ofInt(pbar, "progress", pbar.getProgress(), currentQuestion * 100);
                    progressAnimator.setDuration(500).setInterpolator(new DecelerateInterpolator());
                    progressAnimator.start();
                    questionNumberText.setText("Question " + (currentQuestion + 1));
                    placeQuestionFragment(currentQuestion);
                }
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);

        Call<Quiz> call;

        call = QuizRetroClient.getInstance().getUserApi().getQuiz("Bearer " + sharedPref.getString("token",null));

        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {
                    currentQuiz = response.body();
                    answers = new int[currentQuiz.getQuestions().length];
                    placeQuestionFragment(0);
                    questionNumberText.setText("Question " + (currentQuestion + 1));
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {

            }
        });
    }

    public void placeQuestionFragment(int indexOfQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = QuestionFragment.newInstance(currentQuiz.getQuestions()[indexOfQuestion]);
        ft.replace(R.id.questionFrameLayout, fragment);
        ft.commit();

    }
}

