
package com.example.backend.model.reporting;

public class ReportRequest {

    private String cause;

    private String reported_username;

    private String optionalExplanation;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getReported_username() {
        return reported_username;
    }

    public void setReported_username(String reported_username) {
        this.reported_username = reported_username;
    }

    public String getOptionalExplanation() {
        return optionalExplanation;
    }

    public void setOptionalExplanation(String optionalExplanation) {
        this.optionalExplanation = optionalExplanation;
    }
}
