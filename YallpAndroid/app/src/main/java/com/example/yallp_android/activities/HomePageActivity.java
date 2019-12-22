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
import com.example.yallp_android.helper.TabHelper;
import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.Conversation;
import com.example.yallp_android.models.Language;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.models.Notification;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.CommentRetroClient;
import com.example.yallp_android.util.RetroClients.LanguageRetroClient;
import com.example.yallp_android.util.RetroClients.MessageRetroClient;
import com.example.yallp_android.util.RetroClients.NotificationRetroClient;
import com.example.yallp_android.util.RetroClients.UserRetroClient;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {
    private int unsubsLangsSize = 1;
    private UserInfo userInfo;
    private Conversation[] conversations;
    private ArrayList<String> languageNameList = new ArrayList<>();
    private ArrayList<String> languageLevelList = new ArrayList<>();
    private ArrayList<Integer> languageProgressList = new ArrayList<>();
    private ArrayList<String> languageAndLevelId = new ArrayList<>();
    private Comment[] comments;
    private ArrayList<String> messageSenderList = new ArrayList<>();
    private ArrayList<String> messageLastDateList = new ArrayList<>();
    private ArrayList<Boolean> newMessageList = new ArrayList<>();
    private ArrayList<Integer> conversationIdList = new ArrayList<>();
    private Notification[] unreadNotifications;
    private Notification[] readNotifications;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home_page);

        final SharedPreferences sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();


        checkUnsubsLanguages(sharedPref, editor);
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
                            .putString("imageUrl", userInfo.getProfileImageUrl())
                            .commit();

                    for (int i = 0; i < userInfo.getMemberLanguages().length; i++) {
                        MemberLanguage lang = userInfo.getMemberLanguages()[i];
                        languageNameList.add(lang.getLanguage().getLanguageName());
                        languageAndLevelId.add(lang.getLanguage().getId() + " " + lang.getLanguageLevel());
                        languageProgressList.add(lang.getProgress());
                        if (lang.getLevelName() == null)
                            languageLevelList.add(getResources().getString(R.string.not_graded_yet));
                        else languageLevelList.add(lang.getLevelName());
                    }

                    getConversations(sharedPref, editor);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });

    }

    private void checkUnsubsLanguages(final SharedPreferences sharedPref, final SharedPreferences.Editor editor) {

        Call<Language[]> call;
        call = LanguageRetroClient.getInstance().getLanguageApi().getUnsubsLanguages("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Language[]>() {
            @Override
            public void onResponse(Call<Language[]> call, Response<Language[]> response) {
                if (response.isSuccessful()) {
                    unsubsLangsSize = response.body().length;
                    getUnreadNotifications(sharedPref, editor);

                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Language[]> call, Throwable t) {

            }
        });
    }

    private void getUnreadNotifications(final SharedPreferences sharedPref, final SharedPreferences.Editor editor) {

        Call<Notification[]> call;
        call = NotificationRetroClient.getInstance().getNotificationApi().getUnreadNotifications("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                if (response.isSuccessful()) {
                    unreadNotifications = response.body();
                    getReadNotifications(sharedPref, editor);
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {

            }
        });

    }

    private void getReadNotifications(final SharedPreferences sharedPref, final SharedPreferences.Editor editor) {

        Call<Notification[]> call;
        call = NotificationRetroClient.getInstance().getNotificationApi().getReadNotifications("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                if (response.isSuccessful()) {
                    readNotifications = response.body();
                    getComments(sharedPref, editor);
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {

            }
        });

    }

    private void getComments(final SharedPreferences sharedPref, final SharedPreferences.Editor editor) {

        Call<Comment[]> call;
        call = CommentRetroClient.getInstance().getCommentApi().getComments("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Comment[]>() {
            @Override
            public void onResponse(Call<Comment[]> call, Response<Comment[]> response) {
                if (response.isSuccessful()) {
                    comments = response.body();
                    updateProfileInfo(sharedPref, editor);
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comment[]> call, Throwable t) {

            }
        });

    }

    private void getConversations(final SharedPreferences sharedPref, final SharedPreferences.Editor editor) {
        Call<Conversation[]> messageCall;

        String token = sharedPref.getString("token", null);
        messageCall = MessageRetroClient.getInstance().getMessageApi().getAllConversations("Bearer " + token);

        messageCall.enqueue(new Callback<Conversation[]>() {
            @Override
            public void onResponse(Call<Conversation[]> call, Response<Conversation[]> response) {
                if (response.isSuccessful()) {
                    conversations = response.body();

                    for (int i = 0; i < conversations.length; i++) {
                        if (conversations[i].getMessages().length > 0) {
                            messageSenderList.add(conversations[i].getOtherUsername());

                            SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                            SimpleDateFormat after = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                            Date date = new Date();
                            try {
                                date = before.parse(conversations[i].getMessages()[conversations[i].getMessages().length - 1].getMessageTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            messageLastDateList.add(after.format(date));

                            newMessageList.add(conversations[i].getRead());
                            conversationIdList.add(conversations[i].getId());
                        }
                    }
                    setUpTabs();
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Conversation[]> call, Throwable t) {

            }
        });
    }


    private void setUpTabs() {
        HomePageTabAdapter tabAdapter = new HomePageTabAdapter(getSupportFragmentManager(), 3, languageNameList, languageLevelList, languageProgressList, unsubsLangsSize, languageAndLevelId, comments,
                messageSenderList, messageLastDateList, newMessageList, conversationIdList, unreadNotifications, readNotifications);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        if (getIntent().getIntExtra("tabNumber", -1) != -1) {
            TabHelper.Companion.scrollToSelectedTab(tabs, getIntent().getIntExtra("tabNumber", -1));
        }

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
