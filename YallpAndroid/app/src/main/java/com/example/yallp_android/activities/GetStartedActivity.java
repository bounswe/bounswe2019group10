package com.example.yallp_android.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.yallp_android.models.SignUpUser;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

public class GetStartedActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button signUpButton;
    private  EditText name;
    private  EditText surname;
    private  EditText email;
    private  EditText password;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_get_started);

        backButton = findViewById(R.id.leftArrow);
        signUpButton = findViewById(R.id.signUpButton);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStartedActivity.super.onBackPressed();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(name) && validate(surname) && validate(email) &&validate(password)){
                    signUp();
                };
            }
        });
    }

    public boolean validate(EditText editText){
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }
    public void signUp(){
        final ProgressDialog progressDialog = new ProgressDialog(GetStartedActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        Call<ResponseBody> call;

        call = UserRetroClient.getInstance().getUserApi().signup(
                new SignUpUser(name.getText().toString(),surname.getText().toString(),
                        email.getText().toString(),password.getText().toString()));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
