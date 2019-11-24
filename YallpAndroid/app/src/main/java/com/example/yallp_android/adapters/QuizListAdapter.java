package com.example.yallp_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yallp_android.R;
import com.example.yallp_android.models.Quiz;
import com.example.yallp_android.models.QuizListElement;

import java.util.ArrayList;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.MyViewHolder> {

    public interface QuizListAdapterClickListener {

        void quizListAdapterClick(String topic, String quizId);
    }

    private Context context;
    private ArrayList<QuizListElement> quizzes;
    private LayoutInflater inflater;
    private QuizListAdapterClickListener quizListAdapterClickListener;

    public QuizListAdapter(Context context, ArrayList<QuizListElement> quizzes, QuizListAdapterClickListener quizListAdapterClickListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.quizzes = quizzes;
        this.quizListAdapterClickListener = quizListAdapterClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.quiz_list, parent, false);
        return new MyViewHolder(context,view,quizListAdapterClickListener);
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

        Context context;
        TextView isSolvedText;
        TextView scoreText;
        TextView topicName;
        TextView quizLevel;
        int position;
        Quiz quiz;
        QuizListAdapterClickListener quizListAdapterClickListener;

        MyViewHolder(Context context,View view, QuizListAdapterClickListener quizListAdapterClickListener) {
            super(view);
            this.context = context;
            topicName = view.findViewById(R.id.topicName);
            quizLevel =  view.findViewById(R.id.quizLevel);
            isSolvedText = view.findViewById(R.id.isSolvedText);
            scoreText = view.findViewById(R.id.scoreWriting);
            this.quizListAdapterClickListener = quizListAdapterClickListener;
            view.setOnClickListener(this);
        }

        void setData(QuizListElement quizListElement, int position) {
            this.quiz = quizListElement.getQuiz();
            this.position = position;
            this.topicName.setText(quiz.getQuizType().substring(0,1).toUpperCase()+quiz.getQuizType().substring(1));
            this.quizLevel.setText(quiz.getLevelName());
            if(quizListElement.isSolved()){
                this.isSolvedText.setText(context.getResources().getString(R.string.solved));
                this.isSolvedText.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                this.scoreText.setText("" + quizListElement.getScore() + "/10");
                this.isSolvedText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.ic_check), null);
            }else{
                this.isSolvedText.setText(context.getResources().getString(R.string.not_solved));
                this.isSolvedText.setTextColor(Color.BLACK);
                this.isSolvedText.setCompoundDrawablesWithIntrinsicBounds(null, null,  null, null);
                this.scoreText.setText("-");
            }
        }

        @Override
        public void onClick(View view) {
            quizListAdapterClickListener.quizListAdapterClick(quiz.getQuizType(), quiz.getId()+"");
        }
    }
}
