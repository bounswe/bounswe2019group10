package com.example.backend.repository.writing;

import com.example.backend.model.writing.Writing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WritingRepository  extends JpaRepository<Writing, Integer> {

    //Returns ids of writing tasks that are in specified language
    //@Query("SELECT w.id FROM writing w where w.language_id=:lang_id")
    List<Writing> findAllByLanguageId(@Param("lang_id") Integer langId);

    @Query("SELECT DISTINCT w.taskText FROM Writing w")
    List<String> getDistinctTaskTexts();

    List<Writing> getAllByTaskTextAndLanguageId(String taskText, int languageId);

}
