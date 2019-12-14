package com.example.backend.model.reporting;

import java.util.List;

public class ReportDTO {

    private List<String> causes;

    public List<String> getCauses() {
        return causes;
    }

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }
}
