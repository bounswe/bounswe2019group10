package com.example.backend.model.member;

import java.util.List;

public class MemberDTO {
    private String username;
    private String name;
    private String surname;
    private String mail;
    private int id;
    private String password;
    private List<MemberLanguage> languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<MemberLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<MemberLanguage> languages) {
        this.languages = languages;
    }
}
