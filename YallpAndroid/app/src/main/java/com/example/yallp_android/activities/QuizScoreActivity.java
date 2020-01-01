package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;
import com.example.yallp_android.helper.TabHelper;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizScoreActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    int langId=1;
    int quizId;
    int score;
    String levelName="";
    TextView scoreText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_score);


        scoreText = findViewById(R.id.scoreText);
        if(getIntent().getExtras()!=null){
            langId = getIntent().getExtras().getInt("langId");
            score = getIntent().getExtras().getInt("score");
            quizId = getIntent().getExtras().getInt("quizId");
            getLevelName(sharedPref);
        }

        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
        i.putExtra("tabNumber", TabHelper.Companion.getLANGUAGE_TAB_NUMBER());
        startActivity(i);
        finish();
    }

    public void getLevelName(final SharedPreferences sharedPref) {
        Call<UserInfo> call;

        String token = sharedPref.getString("token", null);
        call = UserRetroClient.getInstance().getUserApi().getProfileInfo("Bearer " + token);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {

                    UserInfo userInfo = response.body();
                    MemberLanguage[] ml = userInfo.getMemberLanguages();
                    for (MemberLanguage memberLanguage : ml) {
                        if (memberLanguage.getLanguage().getId() == langId) {
                            levelName = memberLanguage.getLevelName();
                        }
                    }
                    String text;
                    if(quizId==66){
                        text = "Your score is: " + score + " \n Your level is: " + levelName;
                    }else{
                        text = "Your score is: " + score ;
                    }

                    scoreText.setText(text);

                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });

    }

}
