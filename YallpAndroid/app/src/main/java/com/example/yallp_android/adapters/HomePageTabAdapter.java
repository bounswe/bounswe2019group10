package com.example.yallp_android.adapters;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.yallp_android.fragments.LanguageListFragment;
import com.example.yallp_android.fragments.MessageFragment;
import com.example.yallp_android.fragments.ProfilePageFragment;

import java.util.ArrayList;


public class HomePageTabAdapter extends FragmentStatePagerAdapter {

    private String[] TAB_TITLES = {"Profile","Languages","Messages"};
    ArrayList<String> languageNameList;
    ArrayList<String> languageLevelList;
    ArrayList<String> languageAndLevelId;
    int unsubsLangsSize;
    int numOfTabs;
    public HomePageTabAdapter(FragmentManager fm, int NoofTabs, ArrayList<String> languageNameList,
                              ArrayList<String> languageLevelList, int unsubsLangsSize,
                              ArrayList<String> languageAndLevelId){
        super(fm);
        this.numOfTabs = NoofTabs;
        this.languageNameList = languageNameList;
        this.languageLevelList = languageLevelList;
        this.unsubsLangsSize = unsubsLangsSize;
        this.languageAndLevelId = languageAndLevelId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
        case 0:
            ProfilePageFragment profilePage = ProfilePageFragment.newInstance();
            return profilePage;
        case 1:
            LanguageListFragment languageList = LanguageListFragment.newInstance(languageNameList,languageLevelList,unsubsLangsSize,languageAndLevelId);
            return languageList;
        case 2:
            MessageFragment message = MessageFragment.newInstance();
            return message;
        default:
            return null;
    }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
