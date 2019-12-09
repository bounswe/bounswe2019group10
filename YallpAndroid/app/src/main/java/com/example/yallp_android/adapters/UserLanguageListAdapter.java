package com.example.yallp_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLanguageListAdapter extends BaseAdapter {

    private ArrayList<String> languageNameList;
    private ArrayList<String> languageLevelList;
    private LayoutInflater layoutInflater;
    private int unsubsList;
    private SharedPreferences sharedPref;
    private Context context;

    public UserLanguageListAdapter(Context aContext, ArrayList<String> nameList, ArrayList<String> levelList,int unsubsList,SharedPreferences sharedPref) {
        this.languageNameList = nameList;
        this.languageLevelList = levelList;
        this.unsubsList = unsubsList;
        layoutInflater = LayoutInflater.from(aContext);
        this.sharedPref = sharedPref;
        this.context = aContext;
    }

    @Override
    public int getCount() {
        if(this.unsubsList<=0) return languageNameList.size();
        return languageNameList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == languageLevelList.size()) return "addLanguage";
        return languageNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View v, ViewGroup vg) {
        ViewHolderLanguage languageHolder;
        ViewHolderAddLanguage addHolder;

        if (position == languageLevelList.size()) {
            v = layoutInflater.inflate(R.layout.add_language, null);
            addHolder = new ViewHolderAddLanguage();
            v.setTag(addHolder);


        } else {
            v = layoutInflater.inflate(R.layout.user_language, null);
            languageHolder = new ViewHolderLanguage();
            languageHolder.languageName = v.findViewById(R.id.languageName);
            languageHolder.languageLevel = v.findViewById(R.id.languageLevel);
            languageHolder.countryFlag = v.findViewById(R.id.countryFlag);
            v.setTag(languageHolder);

            languageHolder.languageName.setText(languageNameList.get(position));
            languageHolder.languageLevel.setText(languageLevelList.get(position));

            ImageView deleteIcon = v.findViewById(R.id.deleteIcon);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] languagesToRemove = new String[1];
                    languagesToRemove[0] = languageNameList.get(position);
                    Call<UserInfo> call;

                    call = UserRetroClient
                            .getInstance()
                            .getUserApi()
                            .removeLanguage("Bearer " + sharedPref.getString("token", null),languagesToRemove);

                    call.enqueue(new Callback<UserInfo>() {
                        @Override
                        public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                            if (response.isSuccessful()) {
                                languageNameList.remove(position);
                                languageLevelList.remove(position);
                                notifyDataSetChanged();

                            } else {
                                Toast.makeText(context, "There has been an error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserInfo> call, Throwable t) {

                        }
                    });
                }
            });

        }

        return v;
    }

    static class ViewHolderLanguage {
        TextView languageName;
        TextView languageLevel;
        ImageView countryFlag;
    }

    private static class ViewHolderAddLanguage {

    }
}

