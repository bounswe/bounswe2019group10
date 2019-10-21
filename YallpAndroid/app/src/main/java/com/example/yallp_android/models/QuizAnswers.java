package com.example.yallp_android.models;

public class QuizAnswers {

    private int quizId;
    private Answer[] answers;

    public QuizAnswers(int quizId, Answer[] answers){
        this.quizId = quizId;
        this.answers = answers;
    }


    public int getQuizId() {
        return quizId;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
