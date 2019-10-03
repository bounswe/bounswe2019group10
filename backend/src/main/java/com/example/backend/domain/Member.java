package com.example.backend.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private int id;
    private boolean isExpert;
    private String bio;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    public Member() {
    }

    public Member(String password, String name) {
        this.password = password;
        this.name = name;
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
