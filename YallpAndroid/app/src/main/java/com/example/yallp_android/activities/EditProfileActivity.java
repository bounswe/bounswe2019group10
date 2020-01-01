package com.example.yallp_android.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.models.Token;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText name, surname, mail, bio, password, passwordConfirm;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.editProfileName);
        name.setText(sharedPref.getString("name", null));
        surname = findViewById(R.id.editProfileSurname);
        surname.setText(sharedPref.getString("surname", null));
        mail = findViewById(R.id.editProfileMail);
        mail.setText(sharedPref.getString("mail", null));
        bio = findViewById(R.id.editProfileBio);
        bio.setText(sharedPref.getString("bio", null));
        password = findViewById(R.id.editProfileNewPassword);
        passwordConfirm = findViewById(R.id.editProfileConfirmNewPassword);

        Button confirmButton = findViewById(R.id.saveChangesButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordsMatch()) confirmUpdate();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent i = new Intent(this, HomePageActivity.class);
            startActivity(i);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public boolean passwordsMatch() {
        if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            passwordConfirm.setError("Passwords have to match.");
            passwordConfirm.requestFocus();
            return false;
        }
        return true;
    }

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() <= 0;
    }

    public void confirmUpdate() {
        final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        Call<Token> call = UserRetroClient.getInstance().getUserApi().updateProfileInfo
                ("Bearer " + token,
                        new UserInfo(0,
                                isEmpty(bio) ? null : bio.getText().toString(),
                                isEmpty(password) ? null : password.getText().toString(),
                                null,
                                isEmpty(mail) ? null : mail.getText().toString(),
                                null,
                                isEmpty(name) ? null : name.getText().toString(),
                                isEmpty(surname) ? null : surname.getText().toString(),
                                false,
                                true)
                );


        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }
}
