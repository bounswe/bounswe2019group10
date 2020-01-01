package com.example.yallp_android.models;

public class UserInfo {

    private String bio;
    private boolean enabled;
    private boolean expert;
    private int id;
    private String mail;
    private MemberLanguage[] memberLanguages;
    private String password;
    private String username;
    private String role;
    private String name;
    private String surname;
    private String profileImageUrl;
    private String nativeLanguage;

    public UserInfo(int id, String bio, String password, String username, String mail, String role, String name, String surname, boolean expert, boolean enabled) {
        this.id = id;
        this.bio = bio;
        this.password = password;
        this.username = username;
        this.mail = mail;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.expert = expert;
        this.enabled = enabled;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getExpert() {
        return expert;
    }

    public void setExpert(boolean expert) {
        this.expert = expert;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public MemberLanguage[] getMemberLanguages() {
        return memberLanguages;
    }

    public void setMemberLanguages(MemberLanguage[] MemberLanguages) {
        this.memberLanguages = MemberLanguages;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}

