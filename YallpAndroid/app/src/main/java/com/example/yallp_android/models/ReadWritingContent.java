package com.example.yallp_android.models;

public class ReadWritingContent {
    private int id;
    private int languageId;
    private String taskText;
    private String writingName;

    public ReadWritingContent(int id, int languageId, String taskText, String writingName) {
        this.id = id;
        this.languageId = languageId;
        this.taskText = taskText;
        this.writingName = writingName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getWritingName() {
        return writingName;
    }

    public void setWritingName(String writingName) {
        this.writingName = writingName;
    }
}
