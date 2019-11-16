package com.example.backend.controller.search;

import com.example.backend.model.quiz.Quiz;
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
    @ApiOperation(value = "search materials related to search term")
    public ResponseEntity<List<Quiz>> getSearchedQuizzes(@PathVariable String term){
        return ResponseEntity.ok(searchService.quizSearchResult(term));
    }


}
