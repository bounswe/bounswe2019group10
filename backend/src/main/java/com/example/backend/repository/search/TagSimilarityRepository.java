package com.example.backend.repository.search;

import com.example.backend.model.search.TagSimilarity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagSimilarityRepository extends JpaRepository<TagSimilarity, Integer> {
    TagSimilarity findBySearchTermAndTag(String search_term, String tag);
}
