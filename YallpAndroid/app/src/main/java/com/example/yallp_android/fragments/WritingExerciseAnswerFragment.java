package com.example.yallp_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;
import com.example.yallp_android.models.WritingListElement;
import com.example.yallp_android.util.RetroClients.WritingRetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WritingExerciseAnswerFragment extends Fragment {

    private TextView questionText;
    private TextView answerText;


    public static WritingExerciseAnswerFragment newInstance(int writingId,String answerText, String token) {
        WritingExerciseAnswerFragment fragment = new WritingExerciseAnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("writingId", writingId);
        bundle.putSerializable("answerText", answerText);
        bundle.putSerializable("token", token);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_writing_details, container, false);
        questionText = view.findViewById(R.id.questionText);
        answerText = view.findViewById(R.id.answerText);
        int writingId =(Integer) getArguments().getSerializable("writingId");
        final String answer = (String) getArguments().getSerializable("answerText");
        String token = (String) getArguments().getSerializable("token");

        Call<WritingListElement> call;

        call = WritingRetroClient
                .getInstance()
                .getWritingApi()
                .readDetailsOfOneWriting("Bearer " + token,writingId);

        call.enqueue(new Callback<WritingListElement>() {
            @Override
            public void onResponse(Call<WritingListElement> call, Response<WritingListElement> response) {
                if(response.isSuccessful()){
                    WritingListElement result = response.body();
                    questionText.setText(result.getWritingDTO().getTaskText());
                    answerText.setText(answer);
                }
                else {
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
