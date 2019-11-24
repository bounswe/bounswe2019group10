package com.example.yallp_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallp_android.R;

import java.util.ArrayList;

public class UserLanguageListAdapter extends BaseAdapter {

    private ArrayList<String> languageNameList;
    private ArrayList<String> languageLevelList;
    private LayoutInflater layoutInflater;
    private int unsubsList;

    public UserLanguageListAdapter(Context aContext, ArrayList<String> nameList, ArrayList<String> levelList,int unsubsList) {
        this.languageNameList = nameList;
        this.languageLevelList = levelList;
        this.unsubsList = unsubsList;
        layoutInflater = LayoutInflater.from(aContext);
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

    public View getView(int position, View v, ViewGroup vg) {
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
