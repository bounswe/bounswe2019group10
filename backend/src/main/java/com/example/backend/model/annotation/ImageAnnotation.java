package com.example.backend.model.annotation;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "image_annotation")
public class ImageAnnotation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_annotation_id_generator")
    @SequenceGenerator(name="image_annotation_id_generator", sequenceName = "image_annotation_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "annotator_id")
    private int annotatorId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "annotation_text")
    private String annotationText;

    @Column(name = "x")
    private int x;

    @Column(name = "y")
    private int y;

    @Column(name = "w")
    private int w;

    @Column(name = "h")
    private int h;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public ImageAnnotation(){

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAnnotationText() {
        return annotationText;
    }

    public void setAnnotationText(String annotationText) {
        this.annotationText = annotationText;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
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
