package com.example.backend.controller.writing;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.writing.WritingDTO;
import com.example.backend.model.writing.WritingRequest;
import com.example.backend.model.writing.WritingResultDTO;
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
    @ApiOperation(value = "Get Writing by ID")
    public ResponseEntity<WritingDTO> getById(@PathVariable int writingId) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.getById(writingId, memberUsername));
    }


    @PostMapping("/{writingId}/submit")
    @ApiOperation(value = "Submit the answers to the writing")
    public ResponseEntity<String> evaluateQuizRequest(@PathVariable int writingId, @RequestBody WritingRequest writingRequest) {
        String memberUname = jwtUserDetailsService.getUsername();
        String resp = writingService.processWritingAnswer(writingRequest, memberUname);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{languageId}")
    @ApiOperation(value = "Get IDs of Writings in a given language")
    public ResponseEntity<List<Integer>> getWritingsInLanguage(@PathVariable int languageId) {
        return ResponseEntity.ok(writingService.getWritingsInLanguage(languageId));
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
        return ResponseEntity.ok(writingService.findAllCompleteByAssignedId(memberUsername));
    }

    @GetMapping("assignment/{writing_result_id}")
    @ApiOperation(value = "Get the specific writing grading assignment of the user.")
    public ResponseEntity<WritingResultDTO> getAssignment(@PathVariable int writingResultId) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.findAssignmentById(memberUsername, writingResultId));
    }

    @PostMapping("/evaluate/{writing_result_id}")
    @ApiOperation(value = "Evaluate/Grade the writing.")
    public ResponseEntity<String> evaluateWritingRequest(@PathVariable int writingResultId,  @RequestBody Integer score) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.evaluateWriting(memberUsername, writingResultId, score));
    }

}
