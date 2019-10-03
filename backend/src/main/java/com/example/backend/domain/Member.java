package com.example.backend.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_expert")
    private Boolean isExpert;

    @Column(name = "bio")
    private String bio;

    @Column(nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    public Member() {
        this.password = null;
        this.name = null;
        this.mail = null;
        this.bio = null;
        this.isExpert = null;
    }

    public Member(String name, String password) {
        this.password = password;
        this.name = name;
        this.mail = null;
        this.bio = null;
        this.isExpert = null;
    }

    public Member(String name, String password, String mail, String bio, Boolean isExpert) {
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.bio = bio;
        this.isExpert = isExpert;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isExpert() {
        return isExpert;
    }

    public String getBio() {
        return bio;
    }

    public void setExpert(Boolean expert) {
        isExpert = expert;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
