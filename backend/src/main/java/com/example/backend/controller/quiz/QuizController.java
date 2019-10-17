package com.example.backend.controller.quiz;


import com.example.backend.model.quiz.QuizRequest;
import com.example.backend.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping()
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(quizService.findAll());
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getById(@PathVariable int quizId) {
        return ResponseEntity.ok(quizService.getById(quizId));
    }

    @PostMapping("/{quiz_id}/submit")
    public ResponseEntity<?> evaluateQuizRequest(@PathVariable int quizId, @RequestBody QuizRequest quizRequest) {
        int score;
        QuizRequest request = quizService.evaluateQuiz(quizRequest);
        return ResponseEntity.ok(request);
    }

}
