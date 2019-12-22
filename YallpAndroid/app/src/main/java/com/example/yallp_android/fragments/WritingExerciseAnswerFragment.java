package com.example.yallp_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.models.WritingListElement;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WritingExerciseAnswerFragment extends Fragment {

    private TextView answerText;
    private TextView questionText;
    private ScrollView textScrollView;
    private ImageView writingImageView;
    private ConstraintLayout writingImageLayout;

    public static WritingExerciseAnswerFragment newInstance(int writingId, String answerText, String token, String imageUrl) {
        WritingExerciseAnswerFragment fragment = new WritingExerciseAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("writingId", writingId);
        bundle.putSerializable("answerText", answerText);
        bundle.putSerializable("imageUrl", imageUrl);
        bundle.putSerializable("token", token);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_writing_exercise_answer, container, false);
        questionText = view.findViewById(R.id.questionText);
        answerText = view.findViewById(R.id.answerText);
        textScrollView = view.findViewById(R.id.textScrollView);
        writingImageLayout = view.findViewById(R.id.writingImageLayout);
        writingImageView = view.findViewById(R.id.writingImageView);
        int writingId = (Integer) getArguments().getSerializable("writingId");
        final String answer = (String) getArguments().getSerializable("answerText");
        String token = (String) getArguments().getSerializable("token");
        final String imageUrl = (String) getArguments().getSerializable("imageUrl");

        Call<WritingListElement> call;

        call = WritingRetroClient
                .getInstance()
                .getWritingApi()
                .readDetailsOfOneWriting("Bearer " + token, writingId);

        call.enqueue(new Callback<WritingListElement>() {
            @Override
            public void onResponse(Call<WritingListElement> call, Response<WritingListElement> response) {
                if (response.isSuccessful()) {
                    WritingListElement result = response.body();
                    questionText.setText(result.getWritingDTO().getTaskText());
                    if (imageUrl != null && !imageUrl.equals("")) {

                        Picasso.with(getContext())
                                .load(imageUrl)
                                .into(writingImageView);
                        textScrollView.setVisibility(View.GONE);
                        writingImageLayout.setVisibility(View.VISIBLE);

                    } else {
                        answerText.setText(answer);
                        writingImageLayout.setVisibility(View.GONE);
                        textScrollView.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(getContext(), "There has been an error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WritingListElement> call, Throwable t) {

            }
        });
        return view;
    }
}
