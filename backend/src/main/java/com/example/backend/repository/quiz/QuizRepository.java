package com.example.backend.repository.quiz;

import com.example.backend.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<Quiz> getAllByLanguageId(long languageId);

    List<Quiz> getAllByLevel(long level);


    @Query("SELECT DISTINCT Q.quizType FROM Quiz Q")
    List<String> getDistinctQuizTypes();

    List<Quiz> getAllByQuizTypeAndLanguageId(String quizType, int languageId);

    List<Quiz> getAllByLevelAndLanguageId(int level, int languageId);

    List<Quiz> getAllByLanguageIdAndLevelIsLessThanEqual(int languageId, int level);

}
