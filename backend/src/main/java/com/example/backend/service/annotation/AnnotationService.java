package com.example.backend.service.annotation;

import com.example.backend.model.annotation.Annotation;
import com.example.backend.model.annotation.AnnotationDTO;
import com.example.backend.repository.annotation.AnnotationRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Service
public class AnnotationService {

    @Autowired
    AnnotationRepository annotationRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public String getAnnotation(int id){
        Annotation annotation = annotationRepository.findById(id).orElse(null);

        if (annotation == null)
            return null;


        return toAnnotationModel(annotation);


    }

    public List<String> getAllAnnotations(){
        List<String> list = new ArrayList<>();
        annotationRepository.findAll().forEach(annotation -> {
            list.add(toAnnotationModel(annotation));
        });
        return list;
    }

    public List<String> getAllAnnotationsByWriting(int writingId){
        List<String> list = new ArrayList<>();
        annotationRepository.findAllByWritingResultId(writingId).forEach(annotation -> {
            list.add(toAnnotationModel(annotation));
        });
        return list;
    }

    private String toAnnotationModel(Annotation annotation){
        TreeMap<String, String> map = new TreeMap<>();

        map.put("@context", "http://www.w3.org/ns/anno.jsonld");
        map.put("id", "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/annotation/" + annotation.getId());
        map.put("type", "Annotation");
        map.put("creator", "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/member" + annotation.getAnnotatorId());
        map.put("bodyValue", annotation.getAnnotationText());
        map.put("target", toTargetValue(annotation));

        ObjectMapper obj = new ObjectMapper();

        try {
            return obj.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    private String toTargetValue(Annotation annotation) {

        class Target{

            class Selector{
                String type;
                int start;
                int end;
                Selector(String type, int start, int end){
                    this.type = type;
                    this.start = start;
                    this.end = end;
                }
            }

            Selector selector;

            Target(String type, int start, int end){
                this.selector = new Selector(type, start, end);
            }

        }

        Target target = new Target("TextPositionSelector", annotation.getPosStart(), annotation.getPosEnd());

        ObjectMapper obj = new ObjectMapper();

        try {
            return obj.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            return "";
        }

    }


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
