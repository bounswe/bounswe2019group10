package com.example.backend.service;

import com.example.backend.model.Quiz;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Quiz getById(int id) {
        return quizRepository.getOne(id);
    }
}
