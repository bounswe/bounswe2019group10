package com.example.yallp_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallp_android.R;
import com.example.yallp_android.fragments.LanguageListFragment;
import com.example.yallp_android.models.MemberLanguage;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLanguageListAdapter extends BaseAdapter {

    private ArrayList<String> languageNameList;
    private ArrayList<String> languageLevelList;
    private ArrayList<Integer> progressList;
    private LayoutInflater layoutInflater;
    private int unsubsList;
    private SharedPreferences sharedPref;
    private Context context;
    private boolean isVisiting;

    public UserLanguageListAdapter(Context aContext, ArrayList<String> nameList, ArrayList<String> levelList, ArrayList<Integer> progressList, int unsubsList,SharedPreferences sharedPref, boolean isVisiting) {
        this.languageNameList = nameList;
        this.languageLevelList = levelList;
        this.progressList = progressList;
        this.unsubsList = unsubsList;
        layoutInflater = LayoutInflater.from(aContext);
        this.sharedPref = sharedPref;
        this.context = aContext;
        this.isVisiting = isVisiting;
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
            if(isVisiting) v.findViewById(R.id.deleteIcon).setVisibility(View.INVISIBLE);
            languageHolder.languageName = v.findViewById(R.id.languageName);
            languageHolder.languageLevel = v.findViewById(R.id.languageLevel);
            languageHolder.countryFlag = v.findViewById(R.id.countryFlag);
            languageHolder.progressText = v.findViewById(R.id.progressText);
            languageHolder.progressBar = v.findViewById(R.id.progressBar);
            v.setTag(languageHolder);

            languageHolder.languageName.setText(languageNameList.get(position));
            languageHolder.languageLevel.setText(languageLevelList.get(position));
            languageHolder.progressText.setText("Completed: %" + progressList.get(position));
            switch (languageNameList.get(position)){
                case "ENGLISH":
                    languageHolder.countryFlag.setImageResource(R.drawable.english_flag);
                    break;
                case "TURKISH":
                    languageHolder.countryFlag.setImageResource(R.drawable.turkish_flag);
                    break;
                case "FRENCH":
                    languageHolder.countryFlag.setImageResource(R.drawable.french_flag);
                    break;
                case "SPANISH":
                    languageHolder.countryFlag.setImageResource(R.drawable.spanish_flag);
                    break;
                case "ITALIAN":
                    languageHolder.countryFlag.setImageResource(R.drawable.italian_flag);
                    break;
            }

            if(!isVisiting){
                languageHolder.progressBar.setProgress(progressList.get(position));
            }else{
                languageHolder.progressBar.setVisibility(View.GONE);
            }

            ImageView deleteIcon = v.findViewById(R.id.deleteIcon);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] languagesToRemove = new String[1];
                    languagesToRemove[0] = languageNameList.get(position);
                    Call<MemberLanguage[]> call;

                    call = UserRetroClient
                            .getInstance()
                            .getUserApi()
                            .removeLanguage("Bearer " + sharedPref.getString("token", null),languagesToRemove);

                    call.enqueue(new Callback<MemberLanguage[]>() {
                        @Override
                        public void onResponse(Call<MemberLanguage[]> call, Response<MemberLanguage[]> response) {
                            if (response.isSuccessful()) {
                                languageNameList.remove(position);
                                languageLevelList.remove(position);
                                progressList.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "There has been an error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MemberLanguage[]> call, Throwable t) {

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
        TextView progressText;
        ProgressBar progressBar;
    }

    private static class ViewHolderAddLanguage {

    }
}

