package com.example.yallp_android.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.AreYouSureFragment;
import com.example.yallp_android.helper.FileUtils;
import com.example.yallp_android.helper.GalleryHelper;
import com.example.yallp_android.helper.PermissionUtil;
import com.example.yallp_android.models.ImageUrl;
import com.example.yallp_android.models.Token;
import com.example.yallp_android.models.WritingExerciseElement;
import com.example.yallp_android.models.WritingRequest;
import com.example.yallp_android.util.RetroClients.UserRetroClient;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingActivity extends AppCompatActivity {

    private String writingName;
    private String writingTaskText;
    private String[] users;
    private int chosenUserIndex;
    private int writingId;
    private TextView writingNameView;
    private TextView writingTaskTextView;
    private Button chooseEvaluatorButton;
    private EditText writingSubmission;
    private ConstraintLayout writingImageLayout;
    private ImageView writingImageView;
    private TextView chooseFileText;
    private Activity activity;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_writing);
        activity = this;

        writingNameView = findViewById(R.id.writingExerciseName);
        writingTaskTextView = findViewById(R.id.writingTaskText);
        chooseEvaluatorButton = findViewById(R.id.chooseEvaluatorButton);
        writingSubmission = findViewById(R.id.writingExerciseSubmission);
        writingImageLayout = findViewById(R.id.writingImageLayout);
        writingImageView = findViewById(R.id.writingImageView);
        chooseFileText = findViewById(R.id.chooseFileText);

        getWritingInfo();

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    writingImageLayout.setVisibility(View.GONE);
                    writingSubmission.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    writingSubmission.setVisibility(View.GONE);
                    writingImageLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        writingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PermissionUtil.checkReadPermission(activity) && PermissionUtil.checkWritePermission(activity)) {
                    GalleryHelper.openGallery(activity);
                }
            }
        });


    }

    private void getWritingInfo() {
        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        Call<WritingExerciseElement> call;
        Intent intent = getIntent();
        writingId = Integer.parseInt(intent.getStringExtra("writingId"));
        call = WritingRetroClient.getInstance().getWritingApi().getWritingExercise("Bearer " + sharedPref.getString("token", null), writingId);

        call.enqueue(new Callback<WritingExerciseElement>() {
            @Override
            public void onResponse(Call<WritingExerciseElement> call, Response<WritingExerciseElement> response) {
                if (response.isSuccessful()) {
                    writingName = response.body().getWritingDTO().getWritingName();
                    writingTaskText = response.body().getWritingDTO().getTaskText();
                    users = response.body().getUsernames();

                    writingNameView.setText(writingName);

                    writingTaskTextView.setText(writingTaskText);

                    chooseEvaluatorButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!isEmpty(writingSubmission))
                                showUserList();
                        }
                    });


                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WritingExerciseElement> call, Throwable t) {

            }
        });
    }

    private void showUserList() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a user to evaluate your writing");
        builder.setSingleChoiceItems(users, chosenUserIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chosenUserIndex = which;
            }
        });
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitWriting();
                Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void submitWriting() {
        final ProgressDialog progressDialog = new ProgressDialog(WritingActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);

        String submission = writingSubmission.getText().toString();
        String evaluatorUsername = users[chosenUserIndex];

        WritingRequest wr = new WritingRequest();
        wr.setAnswerText(submission);
        wr.setEvaluatorUsername(evaluatorUsername);
        wr.setWritingId(writingId);

        Call<Token> call = WritingRetroClient.getInstance().getWritingApi().submitWriting("Bearer " + sharedPref.getString("token", null), writingId, wr);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Writing successfully submitted!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Unexpected error occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DialogFragment newFragment = new AreYouSureFragment();
        newFragment.show(getSupportFragmentManager(), "sure");
    }

    public boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().length() <= 0) {
            editText.setError("Writing cannot be empty");
            editText.requestFocus();
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            chooseFileText.setVisibility(View.GONE);
            imageUri = data.getData();

            File f = new File(FileUtils.getPath(this,imageUri));

            Picasso.with(this).load(f).into(writingImageView);

        }
    }


}
