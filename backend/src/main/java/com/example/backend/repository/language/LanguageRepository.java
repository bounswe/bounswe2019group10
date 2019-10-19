package com.example.backend.repository.language;

import com.example.backend.model.quiz.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

    Language getById(int languageId);
}
