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

    public String deleteAnnotation(int id){
        Annotation annotation = annotationRepository.findById(id).orElse(null);
        if (annotation == null){
            return "No such Annotation exists!";
        }

        if(annotation.getAnnotatorId() != jwtUserDetailsService.getUserId()){
            return "This annotation belongs to someone else!";
        }

        annotationRepository.delete(annotation);

        return "Annotation is deleted successfully!";

    }

    public Annotation updateAnnotation(AnnotationDTO annotationDTO){
        Annotation annotation = annotationRepository.findById(annotationDTO.getId()).orElse(null);
        if (annotation == null){
            return null;
        }

        if(annotation.getAnnotatorId() != jwtUserDetailsService.getUserId()){
            return null;
        }

        if(annotationDTO.getAnnotationText() != null)
            annotation.setAnnotationText(annotationDTO.getAnnotationText());

        if(annotationDTO.getPosStart() != null)
            annotation.setPosStart(annotationDTO.getPosStart());

        if(annotationDTO.getPosEnd() != null)
            annotation.setPosEnd(annotationDTO.getPosEnd());

        annotationRepository.save(annotation);

        return annotation;

    }


}
