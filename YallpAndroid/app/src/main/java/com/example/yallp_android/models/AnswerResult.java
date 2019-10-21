package com.example.yallp_android.models;

public class AnswerResult {
    int questionId;
    int choiceId;
    int correctId;
    boolean isTrue;

    public int getQuestionId() {
        return questionId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public int getCorrectId() {
        return correctId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public void setCorrectId(int correctId) {
        this.correctId = correctId;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }
}

