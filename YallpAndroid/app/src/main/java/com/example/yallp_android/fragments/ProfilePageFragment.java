package com.example.yallp_android.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.activities.CompletedWritingExerciseActivity;
import com.example.yallp_android.activities.EditProfileActivity;
import com.example.yallp_android.activities.MainActivity;
import com.example.yallp_android.activities.NonCompletedAssignmentsActivity;
import com.example.yallp_android.adapters.CommentsAdapter;
import com.example.yallp_android.custom_views.ExpandableTextView;
import com.example.yallp_android.custom_views.ThreeDotsView;
import com.example.yallp_android.models.Comment;

import java.io.File;
import java.util.ArrayList;

public class ProfilePageFragment extends Fragment implements ThreeDotsView.ThreeDotsClickListener{


    TextView seeFullBio;
    ExpandableTextView expandableTextView;
    private ListView listView;
    private CommentsAdapter adapter;

    public static ProfilePageFragment newInstance(Comment[] comments) {
        ProfilePageFragment fragment = new ProfilePageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("comments", comments);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("yallp", Context.MODE_PRIVATE);

        TextView usernameTextView = view.findViewById(R.id.profileUsername);
        TextView mailTextView = view.findViewById(R.id.profileMail);
        listView = view.findViewById(R.id.commentList);

        ArrayList<Comment> commentsMadeForUser = new ArrayList<>();
        for(Comment comment : (Comment[]) getArguments().getSerializable("comments")){
            commentsMadeForUser.add(comment);
        }
        //ArrayList<Comment> commentsMadeForUser = (ArrayList<Comment>) getArguments().getSerializable("comments");
        if(commentsMadeForUser.size() != 0){
            ImageView shochedImage = view.findViewById(R.id.shockedImage);
            TextView noCommentText = view.findViewById(R.id.noCommentText);
            shochedImage.setVisibility(View.GONE);
            noCommentText.setVisibility(View.GONE);
            adapter = new CommentsAdapter(getContext(), commentsMadeForUser);
            listView.setAdapter(adapter);
        }else{
            listView.setVisibility(View.GONE);
            listView.setEnabled(false);
        }

        expandableTextView = view.findViewById(R.id.expandableTextView);
        seeFullBio = view.findViewById(R.id.seeFullBio);
        seeFullBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableTextView.changeTrim();
                expandableTextView.setText();
                if (seeFullBio.getText() == getResources().getString(R.string.see_full_bio)) {
                    seeFullBio.setText(R.string.hide_bio);
                } else {
                    seeFullBio.setText(R.string.see_full_bio);
                }
            }
        });
        seeFullBio.callOnClick();

        ThreeDotsView threeDotsView = view.findViewById(R.id.three_dot_view);
        threeDotsView.setListener(this);

        String username = sharedPreferences.getString("username", "");
        String mail = sharedPreferences.getString("mail", "");
        String name = sharedPreferences.getString("name", "");
        String surname = sharedPreferences.getString("surname", "");
        String bio = sharedPreferences.getString("bio", "");

        if (!(name.equals("") && surname.equals(""))) {
            username += " ( ";
            username += name;
            username += " ";
            username += surname;
            username += " )";
        }
        usernameTextView.setText(username);
        mailTextView.setText(mail);
        expandableTextView.setText(bio);

        if (expandableTextView.getText().equals("")) {
            seeFullBio.setVisibility(View.GONE);
            expandableTextView.setVisibility(View.GONE);
        } else if (expandableTextView.getText().length() < 10) {
            seeFullBio.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void itemClick(int item) {
        if (item == 0) {
            editProfile();
        } else if (item == 1) {
            completedWritingExercises();
        } else if (item == 2) {
            nonCompletedAssignments();
        } else {
            logout();
        }
    }

    private void editProfile() {
        Intent i = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void completedWritingExercises() {
        Intent i = new Intent(getActivity(), CompletedWritingExerciseActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void nonCompletedAssignments() {
        Intent i = new Intent(getActivity(), NonCompletedAssignmentsActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void logout() {
        clearApplicationData();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("yallp", Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }


    private void clearApplicationData() {
        File cacheDirectory = getActivity().getCacheDir();
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
