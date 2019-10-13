package com.example.backend.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "member")
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "member_id_seq", allocationSize=50)
    @Column(name = "id")
    private int id;

    @Column(name = "is_expert")
    private Boolean isExpert;

    @Column(name = "bio")
    private String bio;

    @Column(name = "password", nullable = false)
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


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public int getId() {
        return id;
    }

    public void setId(int id){this.id = id;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return false;
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
