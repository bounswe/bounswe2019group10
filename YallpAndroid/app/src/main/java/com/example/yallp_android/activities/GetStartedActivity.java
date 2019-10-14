package com.example.yallp_android.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    private  EditText username;
    private  EditText email;
    private  EditText password;
    private Context context = this;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_get_started);

        backButton = findViewById(R.id.leftArrow);
        signUpButton = findViewById(R.id.signUpButton);
        username = findViewById(R.id.username);
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
                if(!isEmpty(username) && !isEmpty(email) && isValidEmail(email) &&  !isEmpty(password)){
                    signUp();
                };
            }
        });
    }

    public boolean isEmpty(EditText editText){
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return true;
    }

    public boolean isValidEmail(EditText editText){
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()){
            editText.setError("Please enter a valid email");
            editText.requestFocus();
            return false;
        }
        return true;
    }
    public void signUp(){
        final ProgressDialog progressDialog = new ProgressDialog(GetStartedActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        Call<ResponseBody> call;

        call = UserRetroClient.getInstance().getUserApi().signup(
                new SignUpUser(username.getText().toString(),email.getText().toString(),password.getText().toString()));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    context.startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(),"There has been an error!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
