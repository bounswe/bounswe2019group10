package com.example.yallp_android.models;

public class ImageAnnotationDTO {
    private String annotationText;
    private int h;
    private String imageUrl;
    private int w;
    private int x;
    private int y;

    public String getAnnotationText() {
        return annotationText;
    }

    public void setAnnotationText(String annotationText) {
        this.annotationText = annotationText;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
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

    public ImageAnnotationDTO(String annotationText, int h, String imageUrl, int w, int x, int y) {
        this.annotationText = annotationText;
        this.h = h;
        this.imageUrl = imageUrl;
        this.w = w;
        this.x = x;
        this.y = y;
    }
}
