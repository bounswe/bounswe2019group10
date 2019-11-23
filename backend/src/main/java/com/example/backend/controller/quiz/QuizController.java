package com.example.backend.controller.quiz;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.quiz.QuestionDTO;
import com.example.backend.model.quiz.QuizDTO;
import com.example.backend.model.quiz.QuizRequest;
import com.example.backend.model.quiz.QuizResponseDTO;
import com.example.backend.service.dtoconverterservice.MemberDTOConverterService;
import com.example.backend.service.dtoconverterservice.QuizResponseDTOConverterService;
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

    @Autowired
    private QuizResponseDTOConverterService quizResponseDTOConverterService;

    @GetMapping()
    @ApiOperation(value = "Get all quizzes")
    public ResponseEntity<List<QuizResponseDTO>> getAllQuizzes() {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.applyAll(quizService.findAll())
        );
    }

    @GetMapping("/{quizId}")
    @ApiOperation(value = "Get quiz by ID")
    public ResponseEntity<QuizResponseDTO> getById(@PathVariable int quizId) {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.apply(quizService.getById(quizId))
        );
    }

    @GetMapping("/level")
    @ApiOperation(value = "Get a placement quiz")
    public ResponseEntity<QuizResponseDTO> getLevelQuiz() {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.apply(quizService.getById(66))
        );
    }

    @PostMapping("/{quizId}/submit")
    @ApiOperation(value = "Submit the answers to the quiz")
    public ResponseEntity<QuizRequest> evaluateQuizRequest(@PathVariable int quizId, @RequestBody QuizRequest quizRequest) {
        int score;
        String memberUname = jwtUserDetailsService.getUsername();
        QuizRequest qRequest = quizService.evaluateQuiz(quizRequest, memberUname);
        return ResponseEntity.ok(qRequest);
    }

    @GetMapping("/language/{languageId}")
    @ApiOperation(value = "Get quiz by language ID")
    public ResponseEntity<List<QuizResponseDTO>> getByLanguageId(@PathVariable int languageId) {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.applyAll(quizService.getAllQuizzesByLanguageId(languageId))
        );
    }

    @GetMapping("/level/{levelId}")
    @ApiOperation(value = "Get quiz by level")
    public ResponseEntity<List<QuizResponseDTO>> getByLevelId(@PathVariable int levelId) {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.applyAll(quizService.getAllQuizzesByLevelId(levelId))
        );
    }

    @GetMapping("/level/{level}/language/{languageId}")
    @ApiOperation(value = "Get quiz by level and languageId")
    public ResponseEntity<List<QuizResponseDTO>> getByLevelId(@PathVariable int level, @PathVariable int languageId) {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.applyAll(quizService.getAllQuizzesByLevelandLanguageId(level, languageId))
        );
    }

    @GetMapping("/levelorlower/{level}/language/{languageId}")
    @ApiOperation(value = "Get quiz by level and languageId")
    public ResponseEntity<List<QuizResponseDTO>> getAllByLevelIdorLower(@PathVariable int level, @PathVariable int languageId) {
        return ResponseEntity.ok(
                quizResponseDTOConverterService.applyAll(quizService.getAllQuizzesByLanguageIdandLevelLess(level, languageId))
        );
    }

}
