package com.example.backend.repository.annotation;

import com.example.backend.model.annotation.ImageAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageAnnotationRepository extends JpaRepository<ImageAnnotation, Integer> {

    List<ImageAnnotation> findAllByImageUrl(String imageUrl);

}
