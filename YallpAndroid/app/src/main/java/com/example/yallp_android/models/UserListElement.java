package com.example.yallp_android.models;

public class UserListElement {
    private String bio;

    private String mail;
  private int id;
    private String name;
    private String nativeLanguage;
    private String password;
    private String surname;
    private String username;

    public UserListElement(String bio, String mail, String nativeLanguage, String password,  int id,String name,String surname, String username){
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.bio = bio;
        this.nativeLanguage = nativeLanguage;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
