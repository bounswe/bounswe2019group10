package com.example.yallp_android.models;

public class AnnotationDTO {
    private String annotationText;
    private int annotatorId;
    private String createdAt;
    private int id;
    private int posEnd;
    private int posStart;
    private String updatedAt;
    private int writingResultId;

    public String getAnnotationText() {
        return annotationText;
    }

    public void setAnnotationText(String annotationText) {
        this.annotationText = annotationText;
    }

    public int getAnnotatorId() {
        return annotatorId;
    }

    public void setAnnotatorId(int annotatorId) {
        this.annotatorId = annotatorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosEnd() {
        return posEnd;
    }

    public void setPosEnd(int posEnd) {
        this.posEnd = posEnd;
    }

    public int getPosStart() {
        return posStart;
    }

    public void setPosStart(int posStart) {
        this.posStart = posStart;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getWritingResultId() {
        return writingResultId;
    }

    public void setWritingResultId(int writingResultId) {
        this.writingResultId = writingResultId;
    }

    public AnnotationDTO(String annotationText, int annotatorId, String createdAt, int id, int posEnd, int posStart, String updatedAt, int writingResultId) {
        this.annotationText = annotationText;
        this.annotatorId = annotatorId;
        this.createdAt = createdAt;
        this.id = id;
        this.posEnd = posEnd;
        this.posStart = posStart;
        this.updatedAt = updatedAt;
        this.writingResultId = writingResultId;
    }
}
