package com.example.yallp_android.models;

public class Creator {
    private String nickname;
    private String id;
    private String type;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Creator(String nickname, String id, String type) {
        this.nickname = nickname;
        this.id = id;
        this.type = type;
    }
}
