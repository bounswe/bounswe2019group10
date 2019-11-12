package com.example.backend.controller.language;

import com.example.backend.model.language.Language;
import com.example.backend.service.language.LanguageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lang")
@CrossOrigin
public class LanguageController {

    @Autowired
    LanguageService languageService;

    @GetMapping()
    @ApiOperation(value = "Get all languages")
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageService.findAll());
    }

}
