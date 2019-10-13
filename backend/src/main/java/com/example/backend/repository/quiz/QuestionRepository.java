package com.example.backend.repository.quiz;

import com.example.backend.model.quiz.Question;
import com.example.backend.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> getAllByQuiz(Quiz quiz);
}
