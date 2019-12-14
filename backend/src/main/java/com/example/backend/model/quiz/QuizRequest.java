package com.example.backend.model.quiz;

import java.util.List;

public class QuizRequest {



    private int quizId;
    private List<QuestionRequest> answers;
    private int level;
    private int score;


    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<QuestionRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionRequest> answers) {
        this.answers = answers;
    }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

}
