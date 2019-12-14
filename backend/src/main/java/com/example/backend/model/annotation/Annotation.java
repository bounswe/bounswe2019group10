package com.example.backend.model.annotation;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "annotation")
public class Annotation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "annotation_id_generator")
    @SequenceGenerator(name="annotation_id_generator", sequenceName = "annotation_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "annotator_id")
    private int annotatorId;

    @Column(name = "writing_result_id")
    private int writingResultId;

    @Column(name = "annotation_text")
    private String annotationText;

    @Column(name = "pos_start")
    private int posStart;

    @Column(name = "pos_end")
    private int posEnd;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Annotation(){

    }

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

    public int getPosStart() {
        return posStart;
    }

    public void setPosStart(int posStart) {
        this.posStart = posStart;
    }

    public int getPosEnd() {
        return posEnd;
    }

    public void setPosEnd(int posEnd) {
        this.posEnd = posEnd;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
