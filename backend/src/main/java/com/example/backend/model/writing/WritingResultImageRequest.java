package com.example.backend.model.writing;

public class WritingResultImageRequest {

    private int writingId;

    private String evaluatorUsername;

    private String imageURL;

    public int getWritingId() {
        return writingId;
    }

    public void setWritingId(int writingId) {
        this.writingId = writingId;
    }

    public String getEvaluatorUsername() {
        return evaluatorUsername;
    }

    public void setEvaluatorUsername(String evaluatorUsername) {
        this.evaluatorUsername = evaluatorUsername;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
