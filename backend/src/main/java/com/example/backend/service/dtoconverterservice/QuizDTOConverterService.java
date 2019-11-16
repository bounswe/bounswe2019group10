package com.example.backend.service.dtoconverterservice;


import com.example.backend.model.quiz.QuestionDTO;
import com.example.backend.model.quiz.Quiz;
import com.example.backend.model.quiz.QuizDTO;
import com.example.backend.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizDTOConverterService {

    @Autowired
    private QuizService quizService;

    public QuizDTO apply(Quiz quiz, List<QuestionDTO> questionDTOS) {
        QuizDTO quizDTO = new QuizDTO();

        quizDTO.setId(quiz.getId());
        quizDTO.setLevel(quiz.getLevel());
        quizDTO.setQuizType(quiz.getQuizType());
        quizDTO.setLevelName(quiz.getLevelName().toString());

        if (questionDTOS != null)
            quizDTO.setQuestions(questionDTOS);

        return quizDTO;

    }
}
