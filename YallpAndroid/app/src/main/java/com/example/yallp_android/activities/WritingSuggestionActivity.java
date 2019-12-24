package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;
import com.example.yallp_android.models.WritingSuggestion;
import com.example.yallp_android.models.WritingSuggestionResponse;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingSuggestionActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private EditText topicName;
    private EditText questionByUser;
    private Spinner spinnerForLanguage;
    private Button suggestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_writing_suggestion);

        topicName = findViewById(R.id.topic_name);
        questionByUser = findViewById(R.id.questionByUser);
        spinnerForLanguage = findViewById(R.id.spinnerForLanguage);
        suggestButton = findViewById(R.id.suggest_button);

        suggestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = topicName.getText().toString();
                String question = questionByUser.getText().toString();
                int langugeId = spinnerForLanguage.getSelectedItemPosition() + 1;

                if(topic.length() == 0 ){
                    topicName.setError("This area cannot be blank.");
                    return;
                }

                if(question.length() == 0 ){
                    questionByUser.setError("This area cannot be blank.");
                    return;
                }

                WritingSuggestion suggestion = new WritingSuggestion(0,langugeId,question,topic);

                Call<WritingSuggestionResponse> call;

                call = WritingRetroClient.getInstance().getWritingApi().suggestWriting("Bearer " + sharedPref.getString("token", null),suggestion);
                call.enqueue(new Callback<WritingSuggestionResponse>() {
                    @Override
                    public void onResponse(Call<WritingSuggestionResponse> call, Response<WritingSuggestionResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getBaseContext(),"Your suggestion is recieved successfully!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getBaseContext(),"There has been an error!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WritingSuggestionResponse> call, Throwable t) {
                    }
                });

            }
        });
    }


}
