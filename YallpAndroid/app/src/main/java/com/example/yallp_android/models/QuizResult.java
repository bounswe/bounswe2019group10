package com.example.yallp_android.models;

public class QuizResult {
    private int quizId;
    private AnswerResult[] answers;
    private int level;
    private int score;

    public AnswerResult[] getAnswers() {
        return answers;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setAnswers(AnswerResult[] _answers) {
        answers = _answers;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }
}