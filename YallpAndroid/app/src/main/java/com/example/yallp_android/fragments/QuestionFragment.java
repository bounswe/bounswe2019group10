package com.example.yallp_android.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.RadioButton;

import com.example.yallp_android.R;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private int questionNumber;
    private String questionDescription;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_question, container, false);
        this.option1 = view.findViewById(R.id.option1);
        this.option2 = view.findViewById(R.id.option2);
        this.option3 = view.findViewById(R.id.option3);

        this.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if(checked){
                    saveAnswer(Integer.parseInt(v.getTag().toString()));
                }
            }
        });

        this.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if(checked){
                    saveAnswer(Integer.parseInt(v.getTag().toString()));
                }
            }
        });
        this.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked();
                if(checked){
                    saveAnswer(Integer.parseInt(v.getTag().toString()));
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view){

    }

    public void saveAnswer(int indexOfAnswer){

    }

}
