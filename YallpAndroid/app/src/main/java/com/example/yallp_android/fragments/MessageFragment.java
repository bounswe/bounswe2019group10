package com.example.yallp_android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.activities.SearchUserActivity;

public class MessageFragment extends Fragment {

    private Button searchButton ;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
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
              //  getActivity().finish();
            }
        });
        return view;
    }

}
