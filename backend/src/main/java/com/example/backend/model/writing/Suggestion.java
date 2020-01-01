package com.example.backend.model.writing;


import javax.persistence.*;

@Entity
@Table(name = "suggestion")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "suggestion_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "language_id")
    private Integer languageId;

    @Column(name = "task_text")
    private String taskText;

    @Column(name = "writing_name")
    private String writingName;

    @Column(name = "suggestor_username")
    private String suggestorUsername;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
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

    public String getSuggestorUsername() {
        return suggestorUsername;
    }

    public void setSuggestorUsername(String suggestorUsername) {
        this.suggestorUsername = suggestorUsername;
    }
}
