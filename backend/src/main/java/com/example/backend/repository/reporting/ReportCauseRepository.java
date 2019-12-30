package com.example.backend.repository.reporting;

import com.example.backend.model.reporting.ReportCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCauseRepository extends JpaRepository<ReportCause, Integer> {
}
