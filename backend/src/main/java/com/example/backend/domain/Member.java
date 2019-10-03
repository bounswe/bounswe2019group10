package com.example.backend.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "is_expert")
    private boolean isExpert;

    @Column(name = "bio")
    private String bio;

    @Column(nullable = false)
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    public Member(boolean isExpert, String bio, String password, String name, String mail) {
        this.isExpert = isExpert;
        this.bio = bio;
        this.password = password;
        this.name = name;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Member() {
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

    public boolean isExpert() {
        return isExpert;
    }

    public String getBio() {
        return bio;
    }

    public void setExpert(boolean expert) {
        isExpert = expert;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
