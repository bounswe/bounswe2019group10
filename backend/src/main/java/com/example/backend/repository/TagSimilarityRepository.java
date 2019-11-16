package com.example.backend.repository;

import com.example.backend.model.search.TagSimilarity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagSimilarityRepository extends JpaRepository<TagSimilarity, Integer> {
    TagSimilarity getBySearch_termAndTag(String search_term, String tag);
}
