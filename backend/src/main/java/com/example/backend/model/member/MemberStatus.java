package com.example.backend.model.member;

import com.example.backend.model.language.LevelName;

import javax.persistence.*;

@Entity
@Table(name = "member_status")
public class MemberStatus {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "number_of_questions")
    private int numberOfQuestions;

    @Column(name = "member_id")
    private int memberId;

    @Column(name = "lang_id")
    private int langId;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_name")
    private LevelName levelName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public LevelName getLevelName() {
        return levelName;
    }

    public void setLevelName(LevelName levelName) {
        this.levelName = levelName;
    }
}
