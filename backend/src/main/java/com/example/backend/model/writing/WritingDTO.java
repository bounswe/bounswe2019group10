package com.example.backend.model.writing;

import com.example.backend.model.quiz.QuestionDTO;

import java.util.List;

public class WritingDTO {

    private int id;

    private int languageId;

    private String taskText;

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
}
