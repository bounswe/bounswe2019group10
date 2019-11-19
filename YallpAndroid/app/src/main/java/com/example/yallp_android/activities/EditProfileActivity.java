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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.editProfileName);
        surname = findViewById(R.id.editProfileSurname);
        mail = findViewById(R.id.editProfileMail);
        bio = findViewById(R.id.editProfileBio);
        password = findViewById(R.id.editProfileNewPassword);
        passwordConfirm = findViewById(R.id.editProfileConfirmNewPassword);

        Button confirmButton = findViewById(R.id.saveChangesButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordsMatch()) confirmUpdate();
            }
        });
    }

    public boolean passwordsMatch(){
        if(!password.getText().toString().equals(passwordConfirm.getText().toString())){
            passwordConfirm.setError("Passwords have to match.");
            passwordConfirm.requestFocus();
            return false;
        }
        return true;
    }

    public void confirmUpdate(){
        final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token",null);
        Call<Token> call = UserRetroClient.getInstance().getUserApi().updateProfileInfo
                ("Bearer " + token,
                        new UserInfo(0, bio.getText().toString(), password.getText().toString(), null, mail.getText().toString(), null, name.getText().toString(),
                                surname.getText().toString(), false, true)
                );



        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }
}
