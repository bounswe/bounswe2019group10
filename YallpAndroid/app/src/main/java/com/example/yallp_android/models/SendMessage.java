package com.example.yallp_android.models;

public class SendMessage {
    private String message;
    private String targetUsername;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

    public SendMessage(String message, String targetUsername) {
        this.message = message;
        this.targetUsername = targetUsername;
    }
}
