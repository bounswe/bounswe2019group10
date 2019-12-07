package com.example.backend.controller.annotation;

import com.example.backend.model.annotation.Annotation;
import com.example.backend.model.annotation.AnnotationDTO;
import com.example.backend.service.annotation.AnnotationService;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("/{writingResultId}")
    @ApiOperation(value = "Get annotations of a writing")
    public ResponseEntity<List<Annotation>> getComments(@PathVariable int writingResultId){
        return ResponseEntity.ok(annotationService.findAllByWriting(writingResultId));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create annotation for a writing")
    public ResponseEntity<Annotation> getComments(@RequestBody AnnotationDTO annotationDTO){
        return ResponseEntity.ok(annotationService.createAnnotation(annotationDTO));
    }

}
