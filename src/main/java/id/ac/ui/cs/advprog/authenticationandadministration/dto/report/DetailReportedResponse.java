package id.ac.ui.cs.advprog.authenticationandadministration.dto.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DetailReportedResponse {
    private String username;
    private Integer totalReports;
    private List<Report> listReports;
}