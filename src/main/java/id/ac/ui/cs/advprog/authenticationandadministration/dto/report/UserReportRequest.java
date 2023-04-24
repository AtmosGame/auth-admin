package id.ac.ui.cs.advprog.authenticationandadministration.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserReportRequest {
private String username;
private String information;
}
