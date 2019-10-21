package com.example.yallp_android.models;

public class MemberLanguage {

    private int id;
    private int memberId;
    private Language language;
    private int languageLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(int languageLevel) {
        this.languageLevel = languageLevel;
    }

    public MemberLanguage(int id, int memberId, Language language, int languageLevel) {
        this.id = id;
        this.memberId = memberId;
        this.language = language;
        this.languageLevel = languageLevel;
    }
}

