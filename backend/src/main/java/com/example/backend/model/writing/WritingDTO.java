package com.example.backend.model.writing;

import com.example.backend.model.quiz.QuestionDTO;

import java.util.List;

public class WritingDTO {

    private int id;

    private int languageId;

    private String taskText;

    private List<String> evaluatorUsernames;

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

    public List<String> getEvaluatorUsernames() {
        return evaluatorUsernames;
    }

    public void setEvaluatorUsernames(List<String> evaluatorUsernames) {
        this.evaluatorUsernames = evaluatorUsernames;
    }
}
