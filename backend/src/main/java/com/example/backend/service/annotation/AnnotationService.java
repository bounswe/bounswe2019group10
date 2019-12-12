package com.example.backend.service.annotation;

import com.example.backend.model.annotation.Annotation;
import com.example.backend.model.annotation.AnnotationDTO;
import com.example.backend.repository.annotation.AnnotationRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AnnotationService {

    @Autowired
    AnnotationRepository annotationRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    public Map<String, Object> getAnnotation(int id){
        Annotation annotation = annotationRepository.findById(id).orElse(null);

        if (annotation == null)
            return null;


        return toAnnotationModel(annotation);


    }

    public List<Map<String, Object>> getAllAnnotations(){
        List<Map<String, Object>> list = new ArrayList<>();
        annotationRepository.findAll().forEach(annotation -> {
            list.add(toAnnotationModel(annotation));
        });
        return list;
    }

    public List<Map<String, Object>> getAllAnnotationsByWriting(int writingId){
        List<Map<String, Object>> list = new ArrayList<>();
        annotationRepository.findAllByWritingResultId(writingId).forEach(annotation -> {
            list.add(toAnnotationModel(annotation));
        });
        return list;
    }

    private Map<String, Object> toAnnotationModel(Annotation annotation){

        JSONObject anno = new JSONObject();

        anno.put("@context", "http://www.w3.org/ns/anno.jsonld");
        anno.put("id", "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/annotation/" + annotation.getId());
        anno.put("type", "Annotation");

        JSONObject creator = new JSONObject();

        creator.put("id","http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/member/" + annotation.getAnnotatorId());
        creator.put("type", "Person");
        creator.put("nickname", jwtUserDetailsService.getUsernameById(annotation.getAnnotatorId()));

        anno.put("creator", creator);
        anno.put("bodyValue", annotation.getAnnotationText());

        JSONObject target = new JSONObject();

        target.put("source",
                "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/writing/resulttext/" + annotation.getWritingResultId());

        JSONObject selector = new JSONObject();

        selector.put("type", "TextPositionSelector");
        selector.put("start", annotation.getPosStart());
        selector.put("end", annotation.getPosEnd());

        target.put("selector", selector);
        anno.put("target", target);

        return anno.toMap();

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

        return annotation;

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
