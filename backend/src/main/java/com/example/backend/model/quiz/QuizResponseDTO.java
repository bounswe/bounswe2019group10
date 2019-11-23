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

}
