package com.example.yallp_android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallp_android.R;

public class WritingExerciseScoreFragment extends Fragment {
    private TextView evaluatorOrOwnerText;
    private TextView evaluatorOrOwnerName;
    private TextView scoreText;
    private EditText score;

    public static WritingExerciseScoreFragment newInstance(String evaluatorName,int score,String userOrEvaluator,boolean isScored) {
        WritingExerciseScoreFragment fragment = new WritingExerciseScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("evaluatorName", evaluatorName);
        bundle.putSerializable("score", score);
        bundle.putSerializable("userOrEvaluator", userOrEvaluator);
        bundle.putSerializable("isScored", isScored);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_writing_exercise_score, container, false);
        evaluatorOrOwnerText = view.findViewById(R.id.evaluatorOrOwnerText);
        evaluatorOrOwnerName = view.findViewById(R.id.evaluatorOrOwnerName);
        scoreText = view.findViewById(R.id.scoreText);
        score = view.findViewById(R.id.score);
        String evaluatorName =(String) getArguments().getSerializable("evaluatorName");
        int scoreOfUser = (Integer) getArguments().getSerializable("score");
        String userOrEvaluator = (String) getArguments().getSerializable("userOrEvaluator");
        boolean isScored = (boolean) getArguments().getSerializable("isScored");

        if(userOrEvaluator.equals("user")){
            if(isScored){
                evaluatorOrOwnerText.setText("Evaluated by:");
                score.setEnabled(false);
                score.setBackground(null);
                score.setText(scoreOfUser+"/10");
            }else{
                evaluatorOrOwnerText.setText("Waiting for evaluation by:");
                scoreText.setVisibility(View.GONE);
                score.setVisibility(View.GONE);
            }
        }else{
            evaluatorOrOwnerText.setText("Written by:");
            score.setBackground(null);
            score.setHint("...");
            score.setText("");
        }
        evaluatorOrOwnerName.setText(evaluatorName.substring(0,1).toUpperCase() + evaluatorName.substring(1).toLowerCase());



        return view;
    }
}
