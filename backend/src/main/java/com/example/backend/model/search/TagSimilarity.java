package com.example.backend.model.search;

public class TagSimilarity {
    private String tag;
    private double similarity;

    public TagSimilarity(String tag, double similarity){
        this.tag = tag;
        this.similarity = similarity;
    }

    public String getTag() {
        return tag;
    }

    public double getSimilarity() {
        return similarity;
    }
}
