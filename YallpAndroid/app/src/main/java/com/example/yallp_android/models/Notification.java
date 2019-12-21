package com.example.yallp_android.models;

import java.io.Serializable;

public class Notification implements Serializable {
    private int id;
    private int memberId;
    private String notificationType;
    private boolean read;
    private String text;

    public Notification(int id, int memberId, String notificationType, boolean read, String text) {
        this.id = id;
        this.memberId = memberId;
        this.notificationType = notificationType;
        this.read = read;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getMemberI() {
        return memberId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public boolean isRead() {
        return read;
    }

    public String getText() {
        return text;
    }
}
