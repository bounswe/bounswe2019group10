package com.example.yallp_android.models;

public class WritingDTO {
    private int id;
    private int languageId;
    private String taskText;
    private String writingName;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLanguageId(){
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
