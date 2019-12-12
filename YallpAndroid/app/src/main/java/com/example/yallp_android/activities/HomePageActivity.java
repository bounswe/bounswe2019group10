package com.example.yallp_android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.HomePageTabAdapter;
import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.Language;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.CommentRetroClient;
import com.example.yallp_android.util.RetroClients.LanguageRetroClient;
import com.example.yallp_android.util.RetroClients.UserRetroClient;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {
    private int unsubsLangsSize = 1;
    private UserInfo userInfo;
    private ArrayList<String> languageNameList = new ArrayList<>();
    private ArrayList<String> languageLevelList = new ArrayList<>();
    private ArrayList<String> languageAndLevelId = new ArrayList<>();
    private Comment[] comments;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home_page);

        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        checkUnsubsLanguages(sharedPref);
        getComments(sharedPref);
        updateProfileInfo(sharedPref, editor);
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
                    MemberLanguage[] memberLanguages = userInfo.getMemberLanguages();
                    editor.putString("username", userInfo.getUsername())
                            .putString("mail", userInfo.getMail())
                            .putString("name", userInfo.getName())
                            .putString("surname", userInfo.getSurname())
                            .putString("bio", userInfo.getBio())
                            .commit();

                    for (int i = 0; i < userInfo.getMemberLanguages().length; i++) {
                        MemberLanguage lang = userInfo.getMemberLanguages()[i];
                        languageNameList.add(lang.getLanguage().getLanguageName());
                        languageAndLevelId.add(lang.getLanguage().getId() + " " + lang.getLanguageLevel());
                        if (lang.getLevelName() == null)
                            languageLevelList.add(getResources().getString(R.string.not_graded_yet));
                        else languageLevelList.add(lang.getLevelName());
                    }

                    HomePageTabAdapter tabAdapter = new  HomePageTabAdapter(getSupportFragmentManager(),3,languageNameList,languageLevelList,unsubsLangsSize,languageAndLevelId,comments);
                    ViewPager viewPager  = findViewById(R.id.view_pager);
                    viewPager.setAdapter(tabAdapter);
                    TabLayout tabs  = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });

    }
    private void checkUnsubsLanguages(final SharedPreferences sharedPref) {

        Call<Language[]> call;
        call = LanguageRetroClient.getInstance().getLanguageApi().getUnsubsLanguages("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Language[]>() {
            @Override
            public void onResponse(Call<Language[]> call, Response<Language[]> response) {
                if (response.isSuccessful()) {
                    unsubsLangsSize = response.body().length;

                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Language[]> call, Throwable t) {

            }
        });
    }
    private void getComments(final SharedPreferences sharedPref){

        Call<Comment[]> call;
        call = CommentRetroClient.getInstance().getCommentApi().getComments("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Comment[]>() {
            @Override
            public void onResponse(Call<Comment[]> call, Response<Comment[]> response) {
                if (response.isSuccessful()) {
                    comments = response.body();

                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comment[]> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            logout();
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
}
