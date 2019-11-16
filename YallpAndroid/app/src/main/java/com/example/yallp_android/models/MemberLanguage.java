package com.example.yallp_android.models;

public class MemberLanguage {

    private int id;
    private int memberId;
    private Language language;
    private int languageLevel;

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int _memberId) {
        memberId = _memberId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language _language) {
        language = _language;
    }

    public int getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(int _languageLevel) {
        languageLevel = _languageLevel;
    }

    public MemberLanguage(int _id, int _memberId, Language _language, int _languageLevel) {
        id = _id;
        memberId = _memberId;
        language = _language;
        languageLevel = _languageLevel;
    }
}

