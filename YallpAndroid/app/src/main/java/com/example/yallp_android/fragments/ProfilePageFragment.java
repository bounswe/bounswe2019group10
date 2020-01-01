package com.example.yallp_android.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.activities.WritingSuggestionActivity;
import com.example.yallp_android.helper.PermissionUtil;
import com.example.yallp_android.R;
import com.example.yallp_android.activities.CompletedWritingExerciseActivity;
import com.example.yallp_android.activities.EditProfileActivity;
import com.example.yallp_android.activities.MainActivity;
import com.example.yallp_android.activities.NonCompletedAssignmentsActivity;
import com.example.yallp_android.activities.NotificationActivity;
import com.example.yallp_android.adapters.CommentsAdapter;
import com.example.yallp_android.custom_views.ExpandableTextView;
import com.example.yallp_android.custom_views.ThreeDotsView;
import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.Notification;
import com.example.yallp_android.models.ImageUrl;
import com.example.yallp_android.models.Rating;
import com.example.yallp_android.util.RetroClients.CommentRetroClient;
import com.example.yallp_android.util.RetroClients.NotificationRetroClient;
import com.example.yallp_android.util.RetroClients.UserRetroClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfilePageFragment extends Fragment implements ThreeDotsView.ThreeDotsClickListener {


    TextView seeFullBio;
    ExpandableTextView expandableTextView;
    private ListView listView;
    private CommentsAdapter adapter;
    private Notification[] unreadNotifications;
    View view;
    private SharedPreferences sharedPreferences;
    private String token;
    private ImageView profileImage;
    private Comment[] comments;

    public static ProfilePageFragment newInstance() {
        ProfilePageFragment fragment = new ProfilePageFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        sharedPreferences = getActivity().getSharedPreferences("yallp", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        getComments(token, view);

        TextView usernameTextView = view.findViewById(R.id.profileUsername);
        TextView mailTextView = view.findViewById(R.id.profileMail);
        profileImage = view.findViewById(R.id.profileImage);

        ImageView noCommentsImage = view.findViewById(R.id.shockedImage);
        TextView noCommentsText = view.findViewById(R.id.noCommentText);

        getUnreadNotifications(token, noCommentsImage, noCommentsText);

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
        String imageUrl = sharedPreferences.getString("imageUrl", "");

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

        if (!imageUrl.equals("")) {
            Picasso.with(getContext())
                    .load(imageUrl)
                    .into(profileImage);
        }

        profileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (PermissionUtil.checkReadPermission(getActivity()) && PermissionUtil.checkWritePermission(getActivity())) {
                    openGallery();
                }
            }
        });

        final TextView avgRate = view.findViewById(R.id.avgRate);

        Call<Rating> call;
        call = CommentRetroClient.getInstance().getCommentApi().getRating("Bearer " + sharedPreferences.getString("token", null));
        call.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                if (response.isSuccessful()) {
                    if (response.body().getRating() > 0) {
                        avgRate.setText((response.body().getRating() + "").substring(0, 1) + "." + (response.body().getRating() + "").substring(2, 3));
                    } else {
                        avgRate.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {

            }
        });

        return view;
    }


    private void getUnreadNotifications(String token, final ImageView noCommentsImage, final TextView noCommentsText) {

        Call<Notification[]> call;
        call = NotificationRetroClient.getInstance().getNotificationApi().getUnreadNotifications("Bearer " + token);
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                if (response.isSuccessful()) {
                    unreadNotifications = response.body();
                } else {
                    Toast.makeText(getContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {

            }
        });

    }

    private void getComments(String token, final View view) {

        Call<Comment[]> call;
        call = CommentRetroClient.getInstance().getCommentApi().getComments("Bearer " + token);
        call.enqueue(new Callback<Comment[]>() {
            @Override
            public void onResponse(Call<Comment[]> call, Response<Comment[]> response) {
                if (response.isSuccessful()) {
                    comments = response.body();

                    listView = view.findViewById(R.id.commentList);

                    ArrayList<Comment> commentsMadeForUser = new ArrayList<>();
                    for (Comment comment : comments) {
                        commentsMadeForUser.add(comment);
                    }
                    ImageView shochedImage = view.findViewById(R.id.shockedImage);
                    TextView noCommentText = view.findViewById(R.id.noCommentText);
                    if (commentsMadeForUser.size() != 0) {
                        shochedImage.setVisibility(View.GONE);
                        noCommentText.setVisibility(View.GONE);
                        adapter = new CommentsAdapter(getContext(), commentsMadeForUser);
                        listView.setAdapter(adapter);
                    } else {
                        shochedImage.setVisibility(View.VISIBLE);
                        noCommentText.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        listView.setEnabled(false);
                    }
                } else {
                    Toast.makeText(getContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comment[]> call, Throwable t) {

            }
        });

    }

    public void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
    }

    @Override
    public void itemClick(int item) {
        if (item == -1) {
            placeCorrectNotificationDrawable();
        } else if (item == 0) {
            editProfile();
        } else if (item == 1) {
            notifications();
        } else if (item == 2) {
            completedWritingExercises();
        } else if (item == 3) {
            nonCompletedAssignments();
        } else if (item == 4) {
            suggestWriting();
        } else {
            logout();
        }
    }

    private void placeCorrectNotificationDrawable() {
        TextView notificationText = this.view.findViewById(R.id.notifications);
        if (this.unreadNotifications.length > 0) {
            notificationText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notifications_exist, 0);
        } else {
            notificationText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notifications_none, 0);
        }
    }

    private void notifications() {
        Intent i = new Intent(getActivity(), NotificationActivity.class);
        startActivity(i);
        getActivity().finish();
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
    private void suggestWriting() {
        Intent i = new Intent(getActivity(), WritingSuggestionActivity.class);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            final Uri imageUri = data.getData();
            final String docId = DocumentsContract.getDocumentId(imageUri);
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            final String selection = "_id=?";
            final String[] selectionArgs = {docId.split(":")[1]};

            final String column = "_data";
            final String[] projection = {column};
            String path = null;
            try (Cursor cursor = getActivity().getContentResolver().query(contentUri, projection, selection,
                    selectionArgs, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    path = cursor.getString(index);
                }
            }
            File imageFile = null;
            if (path != null) {
                imageFile = new File(path);
            }
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", imageFile.getName(), fileReqBody);


            Call<ImageUrl> call = UserRetroClient.getInstance().getUserApi().profileImage
                    ("Bearer " + token,
                            part
                    );


            call.enqueue(new Callback<ImageUrl>() {
                @Override
                public void onResponse(Call<ImageUrl> call, Response<ImageUrl> response) {
                    if (response.isSuccessful()) {
                        Picasso.with(getActivity()).invalidate(response.body().getUrl());
                        Picasso.with(getContext())
                                .load(response.body().getUrl())
                                .into(profileImage);

                    } else {
                        Toast.makeText(getContext(), response.message() + "  " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ImageUrl> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
