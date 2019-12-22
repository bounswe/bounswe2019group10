package com.example.yallp_android.models;

public class UserReportCause {
    private String cause;
    private int id;

    public UserReportCause(String cause, int id) {
        this.cause = cause;
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public int getId() {
        return id;
    }
}
