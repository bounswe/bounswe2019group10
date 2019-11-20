package com.example.yallp_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yallp_android.ExpandableTextView;
import com.example.yallp_android.R;
import com.example.yallp_android.adapters.LanguageListAdapter;
import com.example.yallp_android.adapters.UserLanguageListAdapter;
import com.example.yallp_android.models.Language;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private UserInfo userInfo;
    private ListView listView;
    private UserLanguageListAdapter adapter;
    private ArrayList<String> languageNameList = new ArrayList<>();
    private ArrayList<String> languageLevelList = new ArrayList<>();

    TextView seeFullBio;
    ExpandableTextView expandableTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_profile);

        TextView logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        TextView editButton = findViewById(R.id.editProfileButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        expandableTextView = findViewById(R.id.expandableTextView);
        seeFullBio = findViewById(R.id.seeFullBio);
        seeFullBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableTextView.changeTrim();
                expandableTextView.setText();
                if (seeFullBio.getText() == getResources().getString(R.string.see_full_bio)) {
                    seeFullBio.setText(R.string.hide_bio);
                } else {
                    seeFullBio.setText(R.string.see_full_bio);
                }
            }
        });
        seeFullBio.callOnClick();

        listView = findViewById(R.id.userLanguageListView);

        updateProfileInfo(sharedPref, editor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == languageLevelList.size()) {
                    Intent i = new Intent(getApplicationContext(), AddLangugeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), QuizListActivity.class);
                    i.putExtra("languageId", userInfo.getMemberLanguages()[arg2].getLanguage().getId());
                    i.putExtra("level", userInfo.getMemberLanguages()[arg2].getLanguageLevel());
                    startActivity(i);
                    finish();
                }
            }

        });
    }

    public void updateProfileInfo(final SharedPreferences sharedPref, final SharedPreferences.Editor editor) {
        Call<UserInfo> call;

        String token = sharedPref.getString("token", null);
        call = UserRetroClient.getInstance().getUserApi().getProfileInfo("Bearer " + token);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {

                    userInfo = response.body();

                    editor.putString("username", userInfo.getUsername())
                            .putString("mail", userInfo.getMail())
                            .putString("name", userInfo.getName())
                            .putString("surname", userInfo.getSurname())
                            .putString("bio", userInfo.getBio())
                            .commit();
                    TextView usernameTextView = findViewById(R.id.profileUsername);
                    TextView mailTextView = findViewById(R.id.profileMail);
                    String usernameText = sharedPref.getString("username", "username");
                    if (!(sharedPref.getString("name", "").equals("") && sharedPref.getString("surname", "").equals(""))) {
                        usernameText += " ( ";
                        usernameText += sharedPref.getString("name", "");
                        usernameText += " ";
                        usernameText += sharedPref.getString("surname", "");
                        usernameText += " )";
                    }
                    usernameTextView.setText(usernameText);
                    mailTextView.setText(sharedPref.getString("mail", "mail"));
                    expandableTextView.setText(sharedPref.getString("bio", ""));

                    if (expandableTextView.getText().equals("")) {
                        seeFullBio.setVisibility(View.GONE);
                        expandableTextView.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < userInfo.getMemberLanguages().length; i++) {
                        MemberLanguage lang = userInfo.getMemberLanguages()[i];
                        languageNameList.add(lang.getLanguage().getLanguageName());
                        if (lang.getLevelName() == null)
                            languageLevelList.add(getResources().getString(R.string.not_graded_yet));
                        else languageLevelList.add(lang.getLevelName());
                    }
                    adapter = new UserLanguageListAdapter(getApplicationContext(), languageNameList, languageLevelList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });

    }

    private void logout() {
        clearApplicationData();
        SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


    private void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    private static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

    private void editProfile() {
        Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
        startActivity(i);
        finish();
    }
}
