package com.example.backend.domain;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "is_expert")
    private Boolean isExpert;

    @Column(name = "bio")
    private String bio;

    @Column(nullable = false)
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "mail")
    private String mail;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    public Member(Member member) {
        this.bio = member.getBio();
        this.id = member.getId();
        this.isExpert = member.isExpert();
        this.mail = member.getMail();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.name = member.getName();
        this.username = member.getUsername();
    }

    public Member(){

    }

    public Boolean getExpert() {
        return isExpert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member(Boolean isExpert, String bio, String password, String username, String mail, String role, String name) {
        this.isExpert = isExpert;
        this.bio = bio;
        this.password = password;
        this.username = username;
        this.mail = mail;
        this.role = role;
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
