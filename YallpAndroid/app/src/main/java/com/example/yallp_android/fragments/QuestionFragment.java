package com.example.yallp_android.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Question;

public class QuestionFragment extends Fragment{

    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private TextView questionText;
    private Question currentQuestion;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", question);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_question, container, false);
        currentQuestion = (Question) getArguments().getSerializable("question");

        this.option1 = view.findViewById(R.id.option1);
        this.option2 = view.findViewById(R.id.option2);
        this.option3 = view.findViewById(R.id.option3);
        this.questionText = view.findViewById(R.id.questionText);

        this.questionText.setText(currentQuestion.getQuestionText());
        this.option1.setText(currentQuestion.getFirstChoice());
        this.option2.setText(currentQuestion.getSecondChoice());
        this.option3.setText(currentQuestion.getThirdChoice());
        return view;
    }
}
