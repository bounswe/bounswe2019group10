package com.example.backend.repository.writing;

import com.example.backend.model.writing.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
}
