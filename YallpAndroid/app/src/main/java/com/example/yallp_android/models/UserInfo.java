package com.example.yallp_android.models;

public class UserInfo {

    private int userId;
    private String userBio;
    private String userPass;
    private String userUsername;
    private String userMail;
    private String userName;
    private String userSurname;
    private String userExpert;
    private boolean userEnabled;

    public UserInfo(int userId, String userBio, String userPass, String userUsername, String userMail, String userName, String userSurname, String userExpert, boolean userEnabled) {
        this.userId = userId;
        this.userBio = userBio;
        this.userPass = userPass;
        this.userUsername = userUsername;
        this.userMail = userMail;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userExpert = userExpert;
        this.userEnabled = userEnabled;
    }


    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
}
