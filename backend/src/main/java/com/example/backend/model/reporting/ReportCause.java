package com.example.backend.model.reporting;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reporting_cause")
public class ReportCause {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "cause")
    private String cause;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
