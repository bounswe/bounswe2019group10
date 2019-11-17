package com.example.backend.controller.search;

import com.example.backend.model.quiz.Quiz;
import com.example.backend.model.writing.Writing;
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

    @PostMapping("/quiz/{term}")
    @ApiOperation(value = "search quizzes related to search term")
    public ResponseEntity<List<Quiz>> getSearchedQuizzes(@PathVariable String term){
        return ResponseEntity.ok(searchService.quizSearchResult(term));
    }

    @PostMapping("/writing/{term}")
    @ApiOperation(value = "search writings related to search term")
    public ResponseEntity<List<Writing>> getSearchedWritings(@PathVariable String term){
        return ResponseEntity.ok(searchService.writingSearchResult(term));
    }


}
