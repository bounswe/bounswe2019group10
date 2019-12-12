package com.example.backend.model.search;

public class SearchRequest {
    private String searchTerm;

    public SearchRequest(String searchTerm){
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
