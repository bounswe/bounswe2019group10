package com.example.yallp_android.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.yallp_android.models.SignUpUser;
import com.example.yallp_android.util.RetroClients.UserRetroClient;
import com.example.yallp_android.models.Token;

public class GetStartedActivity extends AppCompatActivity {

    private  EditText username;
    private  EditText email;
    private  EditText password;
    private Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_get_started);

        ImageView backButton = findViewById(R.id.leftArrow);
        Button signUpButton = findViewById(R.id.signUpButton);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNotEmpty(username) && isNotEmpty(email) && isValidEmail(email) && isNotEmpty(password)){
                    signUp();
                }
            }
        });
    }

    public boolean isNotEmpty(EditText editText){
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("This area cannot be blank.");
        editText.requestFocus();
        return false;
    }

    public boolean isValidEmail(EditText editText){
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()){
            editText.setError("Please enter a valid email.");
            editText.requestFocus();
            return false;
        }
        return true;
    }
    public void signUp(){
        final ProgressDialog progressDialog = new ProgressDialog(GetStartedActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<Token> call;

        call = UserRetroClient.getInstance().getUserApi().signup(
                new SignUpUser(username.getText().toString(),email.getText().toString(),password.getText().toString()));
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    SharedPreferences sharedPref = getSharedPreferences("yallp",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token",response.body().getToken());
                    editor.putBoolean("newSession", true);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    context.startActivity(intent);
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(),"There has been an error!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
