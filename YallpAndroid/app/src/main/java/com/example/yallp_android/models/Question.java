package com.example.yallp_android.models;

import java.io.Serializable;

public class Question implements Serializable {
    private int id;
    private String questionText;
    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;
    private int correctChoiceId;
    private int quizId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(String firstChoice) {
        this.firstChoice = firstChoice;
    }

    public String getSecondChoice() {
        return secondChoice;
    }

    public void setSecondChoice(String secondChoice) {
        this.secondChoice = secondChoice;
    }

    public String getThirdChoice() {
        return thirdChoice;
    }

    public void setThirdChoice(String thirdChoice) {
        this.thirdChoice = thirdChoice;
    }

    public int getCorrectChoiceId() {
        return correctChoiceId;
    }

    public void setCorrectChoiceId(int correctChoiceId) {
        this.correctChoiceId = correctChoiceId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
