package com.example.yallp_android.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;

public class SignInActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button signInButton;
    private  EditText nameOrEmail;
    private  EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin);
        backButton = findViewById(R.id.leftArrow);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nameOrEmail = findViewById(R.id.name);
        password = findViewById(R.id.password);

        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(nameOrEmail) && validate(password)){
                    signIn();
                }

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

    public void signIn(){

    }
}
