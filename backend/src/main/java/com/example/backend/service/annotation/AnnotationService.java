package com.example.backend.service.annotation;

import com.example.backend.model.annotation.*;
import com.example.backend.repository.annotation.AnnotationRepository;
import com.example.backend.repository.annotation.ImageAnnotationRepository;
import com.example.backend.service.member.JwtUserDetailsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AnnotationService {

    @Autowired
    AnnotationRepository annotationRepository;

    @Autowired
    ImageAnnotationRepository imageAnnotationRepository;

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

        anno.put("created", annotation.getCreatedAt().toString().replace(" ", "T").substring(0,19)+"Z");
        anno.put("modified", annotation.getUpdatedAt().toString().replace(" ", "T").substring(0,19)+"Z");

        return anno.toMap();

    }

    ///////////////////////////////// Image //////////////////

    public Map<String, Object> getImageAnnotation(int id){
        ImageAnnotation imageAnnotation = imageAnnotationRepository.findById(id).orElse(null);

        if (imageAnnotation == null)
            return null;

        return toImageAnnotationModel(imageAnnotation);
    }

    public List<Map<String, Object>> getAllImageAnnotationsByImageUrl(String imageUrl){
        List<Map<String, Object>> list = new ArrayList<>();
        imageAnnotationRepository.findAllByImageUrl(imageUrl).forEach(imageAnnotation -> {
            list.add(toImageAnnotationModel(imageAnnotation));
        });
        return list;
    }


    private Map<String, Object> toImageAnnotationModel(ImageAnnotation imageAnnotation){

        JSONObject anno = new JSONObject();

        anno.put("@context", "http://www.w3.org/ns/anno.jsonld");
        anno.put("id", "http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/annotation/image/" + imageAnnotation.getId());
        anno.put("type", "Annotation");

        JSONObject creator = new JSONObject();

        creator.put("id","http://cmpe451group10-env.mw3xz6vhgv.eu-central-1.elasticbeanstalk.com/member/" + imageAnnotation.getAnnotatorId());
        creator.put("type", "Person");
        creator.put("nickname", jwtUserDetailsService.getUsernameById(imageAnnotation.getAnnotatorId()));

        anno.put("creator", creator);
        anno.put("bodyValue", imageAnnotation.getAnnotationText());

        JSONObject target = new JSONObject();

        target.put("source", imageAnnotation.getImageUrl());

        JSONObject selector = new JSONObject();

        selector.put("type", "FragmentSelector");
        selector.put("conformsTo", "http://www.w3.org/TR/media-frags/");
        selector.put("value", "xywh="+imageAnnotation.getX()+","+ imageAnnotation.getY() + "," + imageAnnotation.getW()+ "," + imageAnnotation.getH());

        target.put("selector", selector);
        anno.put("target", target);

        anno.put("created", imageAnnotation.getCreatedAt().toString().replace(" ", "T").substring(0,19)+"Z");
        anno.put("modified", imageAnnotation.getUpdatedAt().toString().replace(" ", "T").substring(0,19)+"Z");

        return anno.toMap();

    }

    public ImageAnnotation createImageAnnotation(ImageAnnotationDTO imageAnnotationDTO){

        ImageAnnotation imageAnnotation = new ImageAnnotation();

        imageAnnotation.setImageUrl(imageAnnotationDTO.getImageUrl());
        imageAnnotation.setAnnotatorId(jwtUserDetailsService.getUserId());
        imageAnnotation.setAnnotationText(imageAnnotationDTO.getAnnotationText());
        imageAnnotation.setX(imageAnnotationDTO.getX());
        imageAnnotation.setY(imageAnnotationDTO.getY());
        imageAnnotation.setW(imageAnnotationDTO.getW());
        imageAnnotation.setH(imageAnnotationDTO.getH());
        LocalDateTime localDateTime = LocalDateTime.now();
        imageAnnotation.setCreatedAt(Timestamp.valueOf(localDateTime));
        imageAnnotation.setUpdatedAt(Timestamp.valueOf(localDateTime));
        imageAnnotationRepository.save(imageAnnotation);

        return imageAnnotation;

    }


/*
    public List<Annotation> findAllByWriting(int writingResultId){
        return annotationRepository.findAllByWritingResultId(writingResultId);
    }
*/
    public Annotation createAnnotation(AnnotationDTO annotationDTO){

        Annotation annotation = new Annotation();

        annotation.setWritingResultId(annotationDTO.getWritingResultId());
        annotation.setAnnotatorId(jwtUserDetailsService.getUserId());
        annotation.setAnnotationText(annotationDTO.getAnnotationText());
        annotation.setPosStart(annotationDTO.getPosStart());
        annotation.setPosEnd(annotationDTO.getPosEnd());
        LocalDateTime localDateTime = LocalDateTime.now();
        annotation.setCreatedAt(Timestamp.valueOf(localDateTime));
        annotation.setUpdatedAt(Timestamp.valueOf(localDateTime));
        annotationRepository.save(annotation);

        return annotation;

    }

    public AnnotationResponseMessage deleteAnnotation(int id){

        AnnotationResponseMessage message = new AnnotationResponseMessage();

        Annotation annotation = annotationRepository.findById(id).orElse(null);
        if (annotation == null){
            message.setMessage("No such Annotation exists!");
            return message;
        }

        if(annotation.getAnnotatorId() != jwtUserDetailsService.getUserId()){
            message.setMessage("This annotation belongs to someone else!");
            return message;
        }

        annotationRepository.delete(annotation);

        message.setMessage("Annotation is deleted successfully!");
        return message;

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

        LocalDateTime localDateTime = LocalDateTime.now();
        annotation.setUpdatedAt(Timestamp.valueOf(localDateTime));

        annotationRepository.save(annotation);

        return annotation;

    }


}
