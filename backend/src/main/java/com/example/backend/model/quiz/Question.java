package com.example.backend.model.quiz;


import javax.persistence.*;

@Entity
@Table(name = "quiz")
public class Question {

    @Column(name = "id")
    private int id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "first_choice")
    private String firstChoice;

    @Column(name = "second_choice")
    private String secondChoice;

    @Column(name = "third_choice")
    private String thirdChoice;

    @Column(name = "correct_choice_id")
    private int correctChoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
