package com.example.yallp_android.models;

public class WritingSuggestion {
    private int id;
    private int languageId;
    private String taskText;
    private String writingName;

    public WritingSuggestion(int id, int languageId, String taskText, String writingName) {
        this.id = id;
        this.languageId = languageId;
        this.taskText = taskText;
        this.writingName = writingName;
    }

    public int getId() {
        return id;
    }

    public int getLanguageId() {
        return languageId;
    }

    public String getTaskText() {
        return taskText;
    }

    public String getWritingName() {
        return writingName;
    }
}
