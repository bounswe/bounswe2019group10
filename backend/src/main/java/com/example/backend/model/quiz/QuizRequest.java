package com.example.backend.model.quiz;

import java.util.List;

public class QuizRequest {



    private int quizId;
    private List<QuestionRequest> answers;

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
}
