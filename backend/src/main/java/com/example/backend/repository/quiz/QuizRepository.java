package com.example.backend.repository.quiz;

import com.example.backend.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<String> getDistinctByQuizType();

    List<Quiz> getAllByQuizType(String quizType);

}
