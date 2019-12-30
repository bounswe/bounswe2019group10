package com.example.backend.repository.reporting;

import com.example.backend.model.reporting.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
