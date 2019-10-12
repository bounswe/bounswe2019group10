package com.example.backend.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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



    public Member() {
        this.password = null;
        this.username = null;
        this.mail = null;
        this.bio = null;
        this.isExpert = null;
        this.role = null;
    }

    public Member(String name, String password) {
        this.password = password;
        this.username = name;
        this.mail = null;
        this.bio = null;
        this.isExpert = null;
        this.role = "USER";
    }

    public Member(String name, String password, String email) {
        this.password = password;
        this.username = name;
        this.mail = email;
        this.bio = null;
        this.isExpert = null;
        this.role = "USER";
    }

    public Member(String name, String password, String mail, String bio, Boolean isExpert, String role) {
        this.username = name;
        this.password = password;
        this.mail = mail;
        this.bio = bio;
        this.isExpert = isExpert;
        this.role = role;
    }

    public Member(Member member){
        this.username = member.username;
        this.password = member.password;
        this.mail = member.mail;
        this.bio = member.bio;
        this.isExpert = member.isExpert;
        this.role = member.role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public Long getId() {
        return id;
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

    public void setUsername(String name) {
        this.username = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
