package com.example.backend.controller.quiz;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.quiz.QuizDTO;
import com.example.backend.model.quiz.QuizRequest;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.quiz.QuizService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @GetMapping()
    @ApiOperation(value = "Get all quizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.findAll());
    }

    @GetMapping("/{quizId}")
    @ApiOperation(value = "Get quiz by ID")
    public ResponseEntity<QuizDTO> getById(@PathVariable int quizId) {
        return ResponseEntity.ok(quizService.getById(quizId));
    }

    @GetMapping("/level")
    @ApiOperation(value = "Get a placement quiz")
    public ResponseEntity<QuizDTO> getLevelQuiz() {
        return ResponseEntity.ok(quizService.getById(66));
    }

    @PostMapping("/{quizId}/submit")
    @ApiOperation(value = "Submit the answers to the quiz")
    public ResponseEntity<QuizRequest> evaluateQuizRequest(@PathVariable int quizId, @RequestBody QuizRequest quizRequest) {
        int score;
        //TODO remove the following and add a global method that can be used by all classes
        String memberUname = jwtUserDetailsService.getUsername();
        QuizRequest qRequest = quizService.evaluateQuiz(quizRequest, memberUname);
        return ResponseEntity.ok(qRequest);
    }

}
