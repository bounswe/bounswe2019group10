package com.example.backend.controller.search;

import com.example.backend.model.member.MemberDTO;
import com.example.backend.model.quiz.QuizResponseDTO;
import com.example.backend.model.search.SearchRequest;
import com.example.backend.model.writing.WritingIsSolvedResponse;
import com.example.backend.service.search.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping("/quiz/{languageId}")
    @ApiOperation(value = "search quizzes related to search term")
    public ResponseEntity<List<QuizResponseDTO>> getSearchedQuizzes(@PathVariable int languageId, @RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.quizSearchResult(searchRequest.getSearchTerm(), languageId));
    }

    @PostMapping("/writing/{languageId}")
    @ApiOperation(value = "search writings related to search term")
    public ResponseEntity<List<WritingIsSolvedResponse>> getSearchedWritings(@PathVariable int languageId,
                                                                             @RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.writingSearchResult(searchRequest.getSearchTerm(), languageId));
    }

    @GetMapping("/member")
    @ApiOperation(value = "search users includes with username term")
    public ResponseEntity<List<MemberDTO>> getSearchedMembers( @RequestBody SearchRequest searchRequest){
        return ResponseEntity.ok(searchService.memberSearchResult(searchRequest.getSearchTerm()));
    }


}
