package com.example.yallp_android.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yallp_android.R;
import com.example.yallp_android.adapters.LanguageListAdapter;
import com.example.yallp_android.helper.TabHelper;
import com.example.yallp_android.models.Language;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.util.RetroClients.LanguageRetroClient;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLanguageActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private ListView mListView;
    private LanguageListAdapter aAdapter;
    ArrayList<Language> languages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.dialog_list_of_languages);
        mListView = findViewById(R.id.languageList);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                final Language language = (Language) mListView.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddLanguageActivity.this);
                builder.setTitle("Last one step")
                        .setMessage("Do you want to start learning " + language.getLanguageName() + " ?")
                        .setIcon(R.drawable.penguin)
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<MemberLanguage> call;
                                String[] newLanguages = new String[1];
                                newLanguages[0] = language.getLanguageName().toUpperCase();
                                call = UserRetroClient.getInstance().getUserApi().addNewLanguages("Bearer " + sharedPref.getString("token", null),newLanguages);
                                call.enqueue(new Callback<MemberLanguage>() {
                                    @Override
                                    public void onResponse(Call<MemberLanguage> call, Response<MemberLanguage> response) {
                                        if(response.isSuccessful()){
                                            onBackPressed();
                                        }
                                        else{
                                            Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MemberLanguage> call, Throwable t) {
                                        onBackPressed();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog dialog  = builder.create();
                dialog.show();
            }
        });

        Call<Language[]> call;
        call = LanguageRetroClient.getInstance().getLanguageApi().getUnsubsLanguages("Bearer " + sharedPref.getString("token", null));
        call.enqueue(new Callback<Language[]>() {
            @Override
            public void onResponse(Call<Language[]> call, Response<Language[]> response) {
                if(response.isSuccessful()){
                    Collections.addAll(languages,response.body());
                    if(languages!=null && languages.size()>0){
                        aAdapter = new LanguageListAdapter(getBaseContext(),languages);
                        mListView.setAdapter(aAdapter);
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Language[]> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomePageActivity.class);
        i.putExtra("tabNumber", TabHelper.Companion.getLANGUAGE_TAB_NUMBER());
        startActivity(i);
        finish();
    }
}
