package id.ac.ui.cs.advprog.authenticationandadministration.dto.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import lombok.*;

import java.util.List;

@Data
@Generated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailReportedResponse {
    private String username;
    private Integer totalReports;
    private List<Report> listReports;
}
