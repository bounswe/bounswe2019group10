package com.example.backend.service.quiz;

import com.example.backend.repository.quiz.QuestionRepository;
import com.example.backend.repository.quiz.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;
}
