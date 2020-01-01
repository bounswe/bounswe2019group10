package com.example.yallp_android.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.activities.ConversationActivity;
import com.example.yallp_android.activities.SearchUserActivity;
import com.example.yallp_android.adapters.ConversationListAdapter;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private Button searchButton ;
    private ListView listView;
    private ConversationListAdapter adapter;
    private ArrayList<String> messageSenderList = new ArrayList<>();
    private ArrayList<String> messageLastDateList = new ArrayList<>();
    private ArrayList<Boolean> newMessageList = new ArrayList<>();
    private ArrayList<Integer> conversationIdList = new ArrayList<>();

    public static MessageFragment newInstance(ArrayList<String> messageSenderList, ArrayList<String> messageLastDateList,
                                              ArrayList<Boolean> newMessageList, ArrayList<Integer> conversationIdList) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("messageSenderList", messageSenderList);
        bundle.putSerializable("messageLastDateList", messageLastDateList);
        bundle.putSerializable("newMessageList", newMessageList);
        bundle.putSerializable("conversationIdList", conversationIdList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_message, container, false);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchUserActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        messageSenderList = (ArrayList<String>) getArguments().getSerializable("messageSenderList");
        messageLastDateList = (ArrayList<String>) getArguments().getSerializable("messageLastDateList");
        newMessageList = (ArrayList<Boolean>) getArguments().getSerializable("newMessageList");
        conversationIdList = (ArrayList<Integer>) getArguments().getSerializable("conversationIdList");

        listView = view.findViewById(R.id.conversationsList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                {
                    Intent i = new Intent(getActivity(), ConversationActivity.class);
                    i.putExtra("conversationId", conversationIdList.get(arg2));
                    i.putExtra("sendTo", messageSenderList.get(arg2));
                    startActivity(i);
                    getActivity().finish();
                }
            }

        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("yallp", Context.MODE_PRIVATE);

        adapter = new ConversationListAdapter(getContext(), messageSenderList, messageLastDateList, newMessageList, sharedPreferences);
        listView.setAdapter(adapter);

        return view;
    }

}
