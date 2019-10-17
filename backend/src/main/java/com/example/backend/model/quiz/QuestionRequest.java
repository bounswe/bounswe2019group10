package com.example.backend.model.quiz;

class QuestionRequest {
    int questionId;
    int choiceId;
    int correctId;
    boolean isTrue;

    public int getCorrectId() {
        return correctId;
    }

    public void setCorrectId(int correctId) {
        this.correctId = correctId;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }
}
