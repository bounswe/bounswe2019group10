package com.example.yallp_android.models;

public class Answer {
    private int questionId;
    private int choiceId;

    public Answer(int questionId,int choiceId){
        this.questionId = questionId;
        this.choiceId = choiceId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
