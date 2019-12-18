package com.example.backend.controller.annotation;

import com.example.backend.model.annotation.Annotation;
import com.example.backend.model.annotation.AnnotationDTO;
import com.example.backend.model.annotation.ImageAnnotation;
import com.example.backend.model.annotation.ImageAnnotationDTO;
import com.example.backend.service.annotation.AnnotationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/annotation")
@CrossOrigin
public class AnnotationController {

    @Autowired
    AnnotationService annotationService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get annotation")
    public ResponseEntity<Map<String, Object>> getAnnotation(@PathVariable int id) {
        return ResponseEntity.ok(annotationService.getAnnotation(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all annotations")
    public ResponseEntity<List<Map<String, Object>>> getAllAnnotations() {
        return ResponseEntity.ok(annotationService.getAllAnnotations());
    }

    @GetMapping("/all/{writingId}")
    @ApiOperation(value = "Get all annotations of a writing text")
    public ResponseEntity<List<Map<String, Object>>> getAllAnnotationsByWriting(@PathVariable int writingId) {
        return ResponseEntity.ok(annotationService.getAllAnnotationsByWriting(writingId));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create annotation for a writing")
    public ResponseEntity<Annotation> createAnnotation(@RequestBody AnnotationDTO annotationDTO) {
        return ResponseEntity.ok(annotationService.createAnnotation(annotationDTO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "delete annotation")
    public ResponseEntity<String> deleteAnnotation(@RequestParam(value = "id") int id) {
        return ResponseEntity.ok(annotationService.deleteAnnotation(id));
    }

    @PostMapping("/update")
    @ApiOperation(value = "Update annotation that you wrote")
    public ResponseEntity<Annotation> updateAnnotation(@RequestBody AnnotationDTO annotationDTO) {
        return ResponseEntity.ok(annotationService.updateAnnotation(annotationDTO));
    }


    ///////////////////////////// IMAGE ANNOTATION ////////////////////////

    @GetMapping("/image/{id}")
    @ApiOperation(value = "Get image annotation")
    public ResponseEntity<Map<String, Object>> getImageAnnotation(@PathVariable int id) {
        return ResponseEntity.ok(annotationService.getAnnotation(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get Image annotations for an image")
    public ResponseEntity<List<Map<String, Object>>> getAllImageAnnotationsByImageUrl(@RequestParam(value = "url") String imageUrl) {
        return ResponseEntity.ok(annotationService.getAllImageAnnotationsByImageUrl(imageUrl));
    }


    @PostMapping("/image/create")
    @ApiOperation(value = "Create image annotation for a writing result")
    public ResponseEntity<ImageAnnotation> createImageAnnotation(@RequestBody ImageAnnotationDTO imageAnnotationDTO) {
        return ResponseEntity.ok(annotationService.createImageAnnotation(imageAnnotationDTO));
    }

}
