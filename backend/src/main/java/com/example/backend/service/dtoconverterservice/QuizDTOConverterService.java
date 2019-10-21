package com.example.backend.service.dtoconverterservice;


import com.example.backend.model.quiz.*;
import com.example.backend.repository.language.LanguageRepository;
import com.example.backend.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizDTOConverterService {

    @Autowired
    private QuizService quizService;

    @Autowired
    private LanguageRepository languageRepository;

    public QuizDTO apply(Quiz quiz, List<QuestionDTO> questionDTOS) {
        QuizDTO quizDTO = new QuizDTO();

        quizDTO.setId(quiz.getId());
        quizDTO.setLevel(quiz.getLevel());
        quizDTO.setQuizType(quiz.getQuizType());

        Language language = languageRepository.getById(quiz.getLanguageId());
        quizDTO.setLanguageName(language.getLanguageName());

        if (questionDTOS != null)
            quizDTO.setQuestions(questionDTOS);

        return quizDTO;

    }
}
