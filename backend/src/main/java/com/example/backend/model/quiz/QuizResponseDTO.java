package com.example.backend.model.quiz;

public class QuizResponseDTO {
    private QuizDTO quiz;
    private int score;
    private boolean isSolved;


    public QuizResponseDTO(QuizDTO quiz, int score, boolean isSolved) {
        this.quiz = quiz;
        this.score = score;
        this.isSolved = isSolved;
    }

    public QuizDTO getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizDTO quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
