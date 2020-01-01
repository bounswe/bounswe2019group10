package com.example.yallp_android.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.activities.AddLanguageActivity;
import com.example.yallp_android.activities.LanguageMainActivity;
import com.example.yallp_android.adapters.UserLanguageListAdapter;

import java.util.ArrayList;

public class LanguageListFragment extends Fragment {

    private ListView listView;
    private UserLanguageListAdapter adapter;
    private ArrayList<String> languageNameList = new ArrayList<>();
    private ArrayList<String> languageLevelList = new ArrayList<>();
    private ArrayList<Integer> languageProgressList = new ArrayList<>();
    private int unsubsLangsSize = 0;

    public static LanguageListFragment newInstance(ArrayList<String> languageNameList, ArrayList<String> languageLevelList, ArrayList<Integer> languageProgressList,
                                                   int unsubsLangsSize, ArrayList<String> languageAndLevelId) {
        LanguageListFragment fragment = new LanguageListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("languageNameList", languageNameList);
        bundle.putSerializable("languageLevelList", languageLevelList);
        bundle.putSerializable("unsubsLangsSize", unsubsLangsSize);
        bundle.putSerializable("languageAndLevelId", languageAndLevelId);
        bundle.putSerializable("languageProgressList", languageProgressList);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_language_list, container, false);

        languageNameList = (ArrayList<String>) getArguments().getSerializable("languageNameList");
        languageLevelList = (ArrayList<String>) getArguments().getSerializable("languageLevelList");
        languageProgressList = (ArrayList<Integer>) getArguments().getSerializable("languageProgressList");
        unsubsLangsSize = (int) getArguments().getSerializable("unsubsLangsSize");

        listView = view.findViewById(R.id.userLanguageListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == languageLevelList.size()) {
                    Intent i = new Intent(getActivity(), AddLanguageActivity.class);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    Intent i = new Intent(getActivity(), LanguageMainActivity.class);
                    String languageAndLevelId = (((ArrayList<String>) getArguments().getSerializable("languageAndLevelId")).get(arg2));
                    String[] splitted = languageAndLevelId.split("\\s+");
                    i.putExtra("languageId", Integer.parseInt(splitted[0]));
                    i.putExtra("level", Integer.parseInt(splitted[1]));
                    startActivity(i);
                    getActivity().finish();
                }
            }

        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("yallp", Context.MODE_PRIVATE);

        adapter = new UserLanguageListAdapter(getContext(), languageNameList, languageLevelList, languageProgressList, unsubsLangsSize, sharedPreferences, false);
        listView.setAdapter(adapter);
        return view;
    }

}
