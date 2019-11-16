package com.example.backend.repository.writing;

import com.example.backend.model.writing.WritingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WritingResultRepository extends JpaRepository<WritingResult, Integer> {
}
