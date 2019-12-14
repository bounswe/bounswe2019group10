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

}
