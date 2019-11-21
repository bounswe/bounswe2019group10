package com.example.backend.controller.writing;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.writing.*;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.writing.WritingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writing")
@CrossOrigin
public class WritingController {

    @Autowired
    private WritingService writingService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @GetMapping("/{writingId}")
    @ApiOperation(value = "Get Writing by ID. Returns writing plus the recommended usernames.")
    public ResponseEntity<WritingResponse> getById(@PathVariable int writingId) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.getById(writingId, memberUsername));
    }

    @PostMapping("/{writingId}/submit")
    @ApiOperation(value = "Submit the answers to the writing. It requires one selected recommended username.")
    public ResponseEntity<WritingResult> evaluateQuizRequest(@PathVariable int writingId, @RequestBody WritingRequest writingRequest) {
        String memberUname = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.processWritingAnswer(writingRequest, memberUname, writingId));
    }

    @GetMapping("/language/{languageId}")
    @ApiOperation(value = "Get IDs of Writings in a given language")
    public ResponseEntity<List<Integer>> getWritingsInLanguage(@PathVariable int languageId) {
        return ResponseEntity.ok(writingService.getWritingsInLanguage(languageId));
    }

    @GetMapping("/getJson/{languageId}")
    @ApiOperation(value = "Get all Writings in a given language as json. This will return a long output.")
    public ResponseEntity<List<WritingDTO>> getWritingsInLanguageJson(@PathVariable int languageId) {
        return ResponseEntity.ok(writingService.getWritingsInLanguageJson(languageId));
    }

    @GetMapping("/scores")
    @ApiOperation(value = "Get the scores of the corresponding user.")
    public ResponseEntity<List<WritingResultDTO>> getScores() {
        String memberUname = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.getWritingResultsOfMember(memberUname));
    }

    @GetMapping("/completedAssignments")
    @ApiOperation(value = "Get the completed writing grading assignments of the user.")
    public ResponseEntity<List<WritingResultDTO>> getCompleteAssignments() {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.findAllCompleteByAssignedId(memberUsername));
    }

    @GetMapping("/nonCompletedAssignments")
    @ApiOperation(value = "Get the waiting writing grading assignments of the user.")
    public ResponseEntity<List<WritingResultDTO>> getNonCompleteAssignments() {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.findAllNonCompleteByAssignedId(memberUsername));
    }

    @PostMapping("/score/{writingResultId}")
    @ApiOperation(value = "Evaluate/Grade the writing. Body requires integer only. Not json.")
    public ResponseEntity<WritingResultDTO> evaluateWritingRequest(@PathVariable int writingResultId, @RequestBody Integer score) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.evaluateWriting(memberUsername, writingResultId, score));
    }

}
