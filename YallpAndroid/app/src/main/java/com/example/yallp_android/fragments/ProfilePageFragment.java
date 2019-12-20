package com.example.yallp_android.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.internal.http.multipart.MultipartEntity;
import com.example.yallp_android.FileUtils;
import com.example.yallp_android.ImageUtility;
import com.example.yallp_android.PermissionUtil;
import com.example.yallp_android.R;
import com.example.yallp_android.activities.CompletedWritingExerciseActivity;
import com.example.yallp_android.activities.EditProfileActivity;
import com.example.yallp_android.activities.MainActivity;
import com.example.yallp_android.activities.NonCompletedAssignmentsActivity;
import com.example.yallp_android.adapters.CommentsAdapter;
import com.example.yallp_android.custom_views.ExpandableTextView;
import com.example.yallp_android.custom_views.ThreeDotsView;
import com.example.yallp_android.models.Comment;
import com.example.yallp_android.models.UserInfo;
import com.example.yallp_android.util.RetroClients.UserRetroClient;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.WINDOW_SERVICE;

public class ProfilePageFragment extends Fragment implements ThreeDotsView.ThreeDotsClickListener {


    TextView seeFullBio;
    ExpandableTextView expandableTextView;
    private ListView listView;
    private CommentsAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String token;
    private ImageView profileImage;

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

        sharedPreferences = getActivity().getSharedPreferences("yallp", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        TextView usernameTextView = view.findViewById(R.id.profileUsername);
        TextView mailTextView = view.findViewById(R.id.profileMail);
        profileImage = view.findViewById(R.id.profileImage);
        listView = view.findViewById(R.id.commentList);

        ArrayList<Comment> commentsMadeForUser = new ArrayList<>();
        Comment[] comments = (Comment[]) getArguments().getSerializable("comments");
        for (Comment comment : comments) {
            commentsMadeForUser.add(comment);
        }
        if (commentsMadeForUser.size() != 0) {
            ImageView shochedImage = view.findViewById(R.id.shockedImage);
            TextView noCommentText = view.findViewById(R.id.noCommentText);
            shochedImage.setVisibility(View.GONE);
            noCommentText.setVisibility(View.GONE);
            adapter = new CommentsAdapter(getContext(), commentsMadeForUser);
            listView.setAdapter(adapter);
        } else {
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
        } else if (expandableTextView.getText().length() < 20) {
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
                if (PermissionUtil.checkReadPermission(getActivity()) && PermissionUtil.checkWritePermission(getActivity()) ) {
                    openGallery();
                }
            }
        });

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

    private void openGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);

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
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "file");
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", imageFile.getName(), fileReqBody);


       /*     File file = new File(FileUtils.getPath(getContext(), imageUri));
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(getActivity().getContentResolver().getType(imageUri)),
                    file
            );
            MultipartBody.Part part =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);*/

            Call<String> call = UserRetroClient.getInstance().getUserApi().profileImage
                    ("Bearer " + token,
                            part
                    );


            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.e("e", "succ");
                    } else {
                        Toast.makeText(getContext(), response.message() + "  " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "asd    " + t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("e", t.getMessage());
                }
            });

            //  }
            //   }


        }
    }
}
