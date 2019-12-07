package com.example.backend.service.annotation;

import com.example.backend.model.annotation.Annotation;
import com.example.backend.model.annotation.AnnotationDTO;
import com.example.backend.repository.annotation.AnnotationRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotationService {

    @Autowired
    AnnotationRepository annotationRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public List<Annotation> findAllByWriting(int writingResultId){
        return annotationRepository.findAllByWritingResultId(writingResultId);
    }

    public Annotation createAnnotation(AnnotationDTO annotationDTO){

        Annotation annotation = new Annotation();

        annotation.setWritingResultId(annotationDTO.getWritingResultId());
        annotation.setAnnotatorId(jwtUserDetailsService.getUserId());
        annotation.setAnnotationText(annotationDTO.getAnnotationText());
        annotation.setPosStart(annotationDTO.getPosStart());
        annotation.setPosEnd(annotationDTO.getPosEnd());

        annotationRepository.save(annotation);

        return annotationRepository.findByAnnotatorIdAndWritingResultId(annotationDTO.getAnnotatorId(), annotationDTO.getWritingResultId());

    }


}
