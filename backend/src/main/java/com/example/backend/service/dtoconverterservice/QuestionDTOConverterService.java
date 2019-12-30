package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.quiz.Question;
import com.example.backend.model.quiz.QuestionDTO;
import org.springframework.stereotype.Component;

@Component
public class QuestionDTOConverterService {

    public QuestionDTO apply(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setCorrectChoiceId(question.getCorrectChoiceId());
        questionDTO.setFirstChoice(question.getFirstChoice());
        questionDTO.setId(question.getId());
        questionDTO.setSecondChoice(question.getSecondChoice());
        questionDTO.setQuestionText(question.getQuestionText());
        questionDTO.setThirdChoice(question.getThirdChoice());
        questionDTO.setQuizId(question.getQuiz().getId());

        return questionDTO;
    }
}
