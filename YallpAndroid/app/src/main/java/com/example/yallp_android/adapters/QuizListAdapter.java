package com.example.yallp_android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Quiz;

import java.util.ArrayList;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.MyViewHolder> {

    public interface QuizListAdapterClickListener {

        void quizListAdapterClick(String topic, String quizId);
    }

    private ArrayList<Quiz> quizzes;
    private LayoutInflater inflater;
    private QuizListAdapterClickListener quizListAdapterClickListener;

    public QuizListAdapter(Context context, ArrayList<Quiz> quizzes, QuizListAdapterClickListener quizListAdapterClickListener) {
        inflater = LayoutInflater.from(context);
        this.quizzes = quizzes;
        this.quizListAdapterClickListener = quizListAdapterClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.quiz_list, parent, false);
        return new MyViewHolder(view,quizListAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(quizzes.get(position), position);
    }


    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView topicName;
        TextView quizId;
        int position;
        Quiz quiz;
        QuizListAdapterClickListener quizListAdapterClickListener;

        public MyViewHolder(View view,QuizListAdapterClickListener quizListAdapterClickListener) {
            super(view);
            topicName = view.findViewById(R.id.topicName);
            quizId =  view.findViewById(R.id.quizId);
            this.quizListAdapterClickListener = quizListAdapterClickListener;
            view.setOnClickListener(this);
        }

        public void setData(Quiz quiz, int position) {
            this.quiz = quiz;
            this.position = position;
            this.topicName.setText(quiz.getQuizType().substring(0,1).toUpperCase()+quiz.getQuizType().substring(1));
            this.quizId.setText(quiz.getId()+"");
        }

        @Override
        public void onClick(View view) {
            quizListAdapterClickListener.quizListAdapterClick(quiz.getQuizType(), quiz.getId()+"");
        }
    }
}
