package com.example.yallp_android.models;

public class UserReportSubmit {
    private String cause;
    private String optionalExplanation;
    private String reported_username;

    public UserReportSubmit(String cause, String optionalExplanation, String reported_username) {
        this.cause = cause;
        this.optionalExplanation = optionalExplanation;
        this.reported_username = reported_username;
    }

    public String getCause() {
        return cause;
    }

    public String getOptionalExplanation() {
        return optionalExplanation;
    }

    public String getReported_username() {
        return reported_username;
    }
}
