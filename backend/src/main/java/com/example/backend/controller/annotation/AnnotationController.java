package com.example.backend.controller.annotation;

import com.example.backend.model.annotation.Annotation;
import com.example.backend.model.annotation.AnnotationDTO;
import com.example.backend.service.annotation.AnnotationService;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annotation")
@CrossOrigin
public class AnnotationController {

    @Autowired
    AnnotationService annotationService;

    @GetMapping("{id}")
    @ApiOperation(value = "Get annotation")
    public ResponseEntity<JSONObject> getAnnotation(@PathVariable int id){
        return ResponseEntity.ok(annotationService.getAnnotation(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all annotations")
    public ResponseEntity<List<JSONObject>> getAllAnnotations(){
        return ResponseEntity.ok(annotationService.getAllAnnotations());
    }

    @GetMapping("all/{writingId}")
    @ApiOperation(value = "Get annotation")
    public ResponseEntity<List<JSONObject>> getAllAnnotationsByWriting(@PathVariable int writingId){
        return ResponseEntity.ok(annotationService.getAllAnnotationsByWriting(writingId));
    }

    @GetMapping("/writing/{writingResultId}")
    @ApiOperation(value = "Get annotations of a writing")
    public ResponseEntity<List<Annotation>> getAnnotations(@PathVariable int writingResultId){
        return ResponseEntity.ok(annotationService.findAllByWriting(writingResultId));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create annotation for a writing")
    public ResponseEntity<Annotation> createAnnotation(@RequestBody AnnotationDTO annotationDTO){
        return ResponseEntity.ok(annotationService.createAnnotation(annotationDTO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "delete annotation")
    public ResponseEntity<String> deleteAnnotation(@RequestParam(value = "id") int id){
        return ResponseEntity.ok(annotationService.deleteAnnotation(id));
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update annotation that you wrote")
    public ResponseEntity<Annotation> updateAnnotation(@RequestBody AnnotationDTO annotationDTO){
        return ResponseEntity.ok(annotationService.updateAnnotation(annotationDTO));
    }

}
