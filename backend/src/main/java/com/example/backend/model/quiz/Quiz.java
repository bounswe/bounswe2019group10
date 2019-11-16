package com.example.backend.model.quiz;


import com.example.backend.model.language.LevelName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @Column(name = "id")
    private int id;

    // m: multiple choice, w:writing, l:listening
    @Column(name = "quiz_type")
    private String quizType;

    @Column(name = "level")
    private int level;

    @Column(name = "language_id")
    private Integer languageId;

    @Column(name = "level_name")
    private LevelName levelName;

    public LevelName getLevelName() {
        return levelName;
    }

    public void setLevelName(LevelName levelName) {
        this.levelName = levelName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
}
