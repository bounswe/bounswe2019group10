package com.example.backend.model.search;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag_similarity")
public class TagSimilarity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "search_term")
    private String search_term;

    @Column(name = "tag")
    private String tag;

    @Column(name = "similarity")
    private double similarity;

    public TagSimilarity(String search_term, String tag, double similarity){
        this.search_term = search_term;
        this.tag = tag;
        this.similarity = similarity;
    }

    public int getId(){
        return id;
    }

    public String getSearch_term() {
        return search_term;
    }

    public String getTag() {
        return tag;
    }

    public double getSimilarity() {
        return similarity;
    }
}
