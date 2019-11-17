package com.example.backend.model.search;

import javax.persistence.*;

@Entity
@Table(name = "tag_similarity")
public class TagSimilarity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_similarity_id_generator")
    @SequenceGenerator(name = "tag_similarity_id_generator", sequenceName = "tag_similarity_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "search_term")
    private String searchTerm;

    @Column(name = "tag")
    private String tag;

    @Column(name = "similarity")
    private double similarity;

    public TagSimilarity() {
    }

    public TagSimilarity(String searchTerm, String tag, double similarity) {
        this.searchTerm = searchTerm;
        this.tag = tag;
        this.similarity = similarity;
    }

    public int getId() {
        return id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getTag() {
        return tag;
    }

    public double getSimilarity() {
        return similarity;
    }

    public double getComparator() {
        return 1 - similarity;
    }
}
