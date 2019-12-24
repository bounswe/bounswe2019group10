package com.example.yallp_android.models;

public class WritingSuggestionResponse extends WritingSuggestion {
    private String suggestorUsername;

    public WritingSuggestionResponse(int id, int languageId, String taskText, String writingName, String suggestorUsername) {
        super(id, languageId, taskText, writingName);
        this.suggestorUsername = suggestorUsername;
    }

    public String getSuggestorUsername() {
        return suggestorUsername;
    }
}
