package com.example.yallp_android.models;

public class QuizAnswers {

    private int quizId;
    private Answer[] answers;

    public QuizAnswers(int quizId, Answer[] _answers){
        this.quizId = quizId;
        answers = _answers;
    }


    public int getQuizId() {
        return quizId;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] _answers) {
        answers = _answers;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
