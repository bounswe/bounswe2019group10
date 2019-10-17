package com.example.backend.service.quiz;

import com.example.backend.model.quiz.*;
import com.example.backend.repository.quiz.QuestionRepository;
import com.example.backend.repository.quiz.QuizRepository;
import com.example.backend.service.dtoconverterservice.QuestionDTOConverterService;
import com.example.backend.service.dtoconverterservice.QuizDTOConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Autowired
    private QuizDTOConverterService quizDTOConverterService;

    @Autowired
    private QuestionDTOConverterService questionDTOConverterService;

    public QuizDTO getById(int id) {
        Quiz quiz = quizRepository.getOne(id);
        List<Question> questions = questionRepository.getAllByQuiz(quiz);

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        questions.forEach(question -> questionDTOS.add(questionDTOConverterService.apply(question)));

        return quizDTOConverterService.apply(quiz, questionDTOS);
    }

    public List<QuizDTO> findAll() {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizRepository.findAll().forEach(quiz -> quizDTOS.add(quizDTOConverterService.apply(quiz, null)));
        return quizDTOS;
    }

    public QuizRequest evaluateQuiz(QuizRequest quizRequest){
        int quizId = quizRequest.getQuizId();
        questionRepository.getAllByQuiz();
    }


}
