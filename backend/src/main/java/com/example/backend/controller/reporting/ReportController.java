package com.example.backend.controller.reporting;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.model.reporting.ReportCause;
import com.example.backend.model.reporting.ReportRequest;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.reporting.ReportingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

    @Autowired
    ReportingService reportingService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @GetMapping("/causes")
    @ApiOperation(value = "Get all possible causes of a report.")
    public ResponseEntity<List<ReportCause>> getAllUnsubscribedLanguages() {
        return ResponseEntity.ok(reportingService.getReportStructure());
    }

    @PostMapping("/send")
    @ApiOperation(value = "Report a user")
    public ResponseEntity<ReportRequest> sendMessage(@RequestBody ReportRequest reportRequest){
        String memberUsername = jwtUserDetailsService.getUsername();
        return ResponseEntity.ok(reportingService.addNewReport(reportRequest, memberUsername));
    }


}
