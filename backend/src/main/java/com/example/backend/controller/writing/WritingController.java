package com.example.backend.controller.writing;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.member.ProfileImageDTO;
import com.example.backend.model.writing.*;
import com.example.backend.service.aws.AmazonClient;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.writing.WritingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private AmazonClient amazonClient;

    @GetMapping("/{writingId}")
    @ApiOperation(value = "Get Writing by ID. Returns writing plus the recommended usernames.")
    public ResponseEntity<WritingResponse> getById(@PathVariable int writingId) {
        return ResponseEntity.ok(writingService.getAndRecommendById(writingId));
    }

    @GetMapping("/read/{writingId}")
    @ApiOperation(value = "Use this when you want to only see the contents of the writing. This is cheaper")
    public ResponseEntity<WritingIsSolvedResponse> getOnlyWritingById(@PathVariable int writingId) {
        return ResponseEntity.ok(writingService.getById(writingId));
    }

    @PostMapping("/{writingId}/submit")
    @ApiOperation(value = "Submit the answers to the writing. It requires one selected recommended username.")
    public ResponseEntity<WritingResultDTO> evaluateQuizRequest(@PathVariable int writingId, @RequestBody WritingRequest writingRequest) {
        String memberUname = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.processWritingAnswer(writingRequest, memberUname, writingId));
    }

    @PostMapping(value = "/{writingId}/submitWithImageURL")
    @ApiOperation(value = "Submit the answers to the writing with an image URL. It requires one selected recommended username.")
    public ResponseEntity<WritingResultDTO> evaluateQuizRequestByImage(@PathVariable int writingId
                                                                      , @RequestBody WritingResultImageRequest writingRequest ) {

        String memberUname = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.processWritingAnswerByImage(writingRequest, memberUname, writingId));
    }

    @PostMapping("/uploadWritingImage")
    @ApiOperation(value = "Upload writing image. Returns the URL of the image.")
    public ResponseEntity<ProfileImageDTO> addWritingImage(@RequestPart(value = "file") MultipartFile file){
        String imageUrl =  amazonClient.uploadFile(file);
        ProfileImageDTO profileImageDTO = new ProfileImageDTO();
        profileImageDTO.setUrl(imageUrl);
        return ResponseEntity.ok(profileImageDTO);
    }

    @GetMapping("/language/{languageId}")
    @ApiOperation(value = "Get IDs of Writings in a given language")
    public ResponseEntity<List<Integer>> getWritingsInLanguage(@PathVariable int languageId) {
        return ResponseEntity.ok(writingService.getWritingsInLanguage(languageId));
    }

    @GetMapping("/getJson/{languageId}")
    @ApiOperation(value = "Get all Writings in a given language as json. This will return a long output.")
    public ResponseEntity<List<WritingIsSolvedResponse>> getWritingsInLanguageJson(@PathVariable int languageId) {
        return ResponseEntity.ok(writingService.getWritingsInLanguageJson(languageId));
    }

    @GetMapping("/scores")
    @ApiOperation(value = "Get the scores of the corresponding user.")
    public ResponseEntity<List<WritingResultDTO>> getScores() {
        return ResponseEntity.ok(writingService.getWritingResultsOfMember());
    }

    @GetMapping("/completedAssignments")
    @ApiOperation(value = "Get the completed writing grading assignments of the user.")
    public ResponseEntity<List<WritingResultDTO>> getCompleteAssignments() {
        return ResponseEntity.ok(writingService.findAllCompleteByAssignedId());
    }

    @GetMapping("/nonCompletedAssignments")
    @ApiOperation(value = "Get the waiting writing grading assignments of the user.")
    public ResponseEntity<List<WritingResultDTO>> getNonCompleteAssignments() {

        return ResponseEntity.ok(writingService.findAllNonCompleteByAssignedId());
    }

    @PostMapping("/score/{writingResultId}")
    @ApiOperation(value = "Evaluate/Grade the writing. Body requires integer only. Not json.")
    public ResponseEntity<WritingResultDTO> evaluateWritingRequest(@PathVariable int writingResultId, @RequestBody Integer score) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.evaluateWriting(memberUsername, writingResultId, score));
    }

    @GetMapping("/resulttext/{writingResultId}")
    @ApiOperation(value = "Get writing results answer text")
    public ResponseEntity<String> getWritingResultAnswerText(@PathVariable int writingResultId) {
        return ResponseEntity.ok(writingService.getWritingResultAnswerText(writingResultId));
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new writing suggestion to the database.  ")
    public ResponseEntity<Suggestion> evaluateWritingRequest( @RequestBody WritingDTO writingDTO) {
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(writingService.addNewWriting(writingDTO, memberUsername));
    }



}
