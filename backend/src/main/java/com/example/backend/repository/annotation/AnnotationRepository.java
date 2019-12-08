package com.example.backend.repository.annotation;

import com.example.backend.model.annotation.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Integer> {

    List<Annotation> findAllByWritingResultId(int writingResultId);

}
