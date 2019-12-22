package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.AreYouSureFragment;
import com.example.yallp_android.fragments.QuestionFragment;

import com.example.yallp_android.models.Answer;
import com.example.yallp_android.models.Quiz;
import com.example.yallp_android.models.QuizAnswers;
import com.example.yallp_android.models.QuizListElement;
import com.example.yallp_android.models.QuizResult;
import com.example.yallp_android.util.RetroClients.QuizRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private Quiz currentQuiz;
    private int currentQuestion = 0;
    private Button submitButton;
    private int[] givenAnswers;
    private int currentAnswer;
    private int nofQuestions;
    private TextView questionNumberText;
    private boolean isQuestionChecked = false;
    private ProgressBar pbar;
    private ObjectAnimator progressAnimator;
    private RadioGroup optionGroup;
    int quizId;
    int langId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_quiz);

        this.submitButton = findViewById(R.id.quizSubmitButton);
        this.questionNumberText = findViewById(R.id.questionNumberText);
        this.pbar = findViewById(R.id.pBar);

        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionGroup = findViewById(R.id.optionGroup);
                if (optionGroup.getCheckedRadioButtonId() != -1) {
                    if (!isQuestionChecked) {
                        int correctAnswer = currentQuiz.getQuestions()[currentQuestion].getCorrectChoiceId();
                        isQuestionChecked = true;
                        if (currentQuestion == nofQuestions - 1) submitButton.setText(R.string.finish);
                        else submitButton.setText(R.string.next);
                        for (int i = 0; i < optionGroup.getChildCount(); i++) {
                            optionGroup.getChildAt(i).setEnabled(false);
                        }
                        switch (optionGroup.getCheckedRadioButtonId()) {
                            case R.id.option1: {
                                currentAnswer = 1;
                                break;
                            }
                            case R.id.option2: {
                                currentAnswer = 2;
                                break;
                            }
                            case R.id.option3: {
                                currentAnswer = 3;
                                break;
                            }
                        }
                        if (correctAnswer == currentAnswer) {
                            ((RadioButton) optionGroup.getChildAt(correctAnswer - 1)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);

                        } else {
                            ((RadioButton) optionGroup.getChildAt(correctAnswer - 1)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                            ((RadioButton) optionGroup.getChildAt(currentAnswer - 1)).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0);
                        }
                    } else {
                        givenAnswers[currentQuestion] = currentAnswer;
                        currentQuestion++;
                        if (currentQuestion == nofQuestions) {
                            getQuizScore();
                        } else {
                            isQuestionChecked = false;
                            submitButton.setText(R.string.check);
                            progressAnimator = ObjectAnimator.ofInt(pbar, "progress", pbar.getProgress(), (currentQuestion + 1) * 100);
                            progressAnimator.setDuration(500).setInterpolator(new DecelerateInterpolator());
                            progressAnimator.start();
                            questionNumberText.setText("Question " + (currentQuestion + 1));
                            placeQuestionFragment(currentQuestion);
                        }
                    }
                }

            }
        });

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);

        Call<QuizListElement> call;
        Intent intent = getIntent();
        quizId = Integer.parseInt(intent.getStringExtra("quizId"));
        langId = intent.getIntExtra("langId",1);
        call = QuizRetroClient.getInstance().getQuizApi().getQuiz("Bearer " + sharedPref.getString("token", null),quizId);

        call.enqueue(new Callback<QuizListElement>() {
            @Override
            public void onResponse(Call<QuizListElement> call, Response<QuizListElement> response) {
                if (response.isSuccessful()) {
                    currentQuiz = response.body().getQuiz();
                    nofQuestions = currentQuiz.getQuestions().length;
                    givenAnswers = new int[nofQuestions];
                    placeQuestionFragment(0);
                    questionNumberText.setText("Question " + (currentQuestion + 1));
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuizListElement> call, Throwable t) {

            }
        });
    }

    public void getQuizScore() {

        Call<QuizResult> call;

        Answer[] answers = new Answer[nofQuestions];
        for (int i = 0; i < nofQuestions; i++) {
            answers[i] = new Answer(currentQuiz.getQuestions()[i].getId(), givenAnswers[i]);
        }

        call = QuizRetroClient.getInstance().getQuizApi().postQuizResult("Bearer " + sharedPref.getString("token", null),
                new QuizAnswers(currentQuiz.getId(), answers),quizId);


        call.enqueue(new Callback<QuizResult>() {

            @Override
            public void onResponse(Call<QuizResult> call, Response<QuizResult> response) {
                if (response.isSuccessful()) {
                    QuizResult result = response.body();
                    createScoreActivity(result.getLevel(), result.getScore());
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuizResult> call, Throwable t) {
            }
        });
    }

    public void placeQuestionFragment(int indexOfQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = QuestionFragment.newInstance(currentQuiz.getQuestions()[indexOfQuestion]);
        ft.replace(R.id.questionFrameLayout, fragment);
        ft.commit();

    }

    private void createScoreActivity(int level, int score) {
        Intent intent = new Intent(getApplicationContext(), QuizScoreActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("score", score);
        intent.putExtra("langId", langId);
        intent.putExtra("quizId", quizId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DialogFragment newFragment = new AreYouSureFragment();
        newFragment.show(getSupportFragmentManager(), "sure");
    }
}
