package com.example.backend.model.annotation;

public class AnnotationDTO {

    private int id;

    private int annotatorId;

    private int writingResultId;

    private String annotationText;

    private Integer posStart;

    private Integer posEnd;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnotatorId() {
        return annotatorId;
    }

    public void setAnnotatorId(int annotatorId) {
        this.annotatorId = annotatorId;
    }

    public int getWritingResultId() {
        return writingResultId;
    }

    public void setWritingResultId(int writingResultId) {
        this.writingResultId = writingResultId;
    }

    public String getAnnotationText() {
        return annotationText;
    }

    public void setAnnotationText(String annotationText) {
        this.annotationText = annotationText;
    }

    public Integer getPosStart() {
        return posStart;
    }

    public void setPosStart(Integer posStart) {
        this.posStart = posStart;
    }

    public Integer getPosEnd() {
        return posEnd;
    }

    public void setPosEnd(Integer posEnd) {
        this.posEnd = posEnd;
    }
}
