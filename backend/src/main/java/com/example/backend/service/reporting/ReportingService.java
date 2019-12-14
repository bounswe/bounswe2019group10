package com.example.backend.service.reporting;


import com.example.backend.model.reporting.Report;
import com.example.backend.model.reporting.ReportCause;
import com.example.backend.model.reporting.ReportRequest;
import com.example.backend.repository.reporting.ReportCauseRepository;
import com.example.backend.repository.reporting.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportCauseRepository reportCauseRepository;

    public ReportRequest addNewReport(ReportRequest reportRequest, String username){
        Report report = new Report();
        report.setCause(reportRequest.getCause());
        report.setExplanation(reportRequest.getOptionalExplanation());
        report.setReportingUsername(username);
        report.setReportedUsername(reportRequest.getReported_username());
        reportRepository.save(report);
        return reportRequest;
    }

    public List<ReportCause> getReportStructure(){
        return reportCauseRepository.findAll();
    }


}
