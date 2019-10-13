package com.example.backend.controller;

import com.example.backend.model.Quiz;
import com.example.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getById(@PathVariable int quizId) {
        return ResponseEntity.ok(quizService.getById(quizId));
    }
}
