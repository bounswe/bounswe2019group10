package com.example.backend.service.language;

import com.example.backend.model.language.Language;
import com.example.backend.repository.language.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;


    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}
