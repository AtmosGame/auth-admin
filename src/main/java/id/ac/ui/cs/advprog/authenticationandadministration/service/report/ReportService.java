package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    ReportedAccountResponse getAllReportedAccount();
    DetailReportedResponse getReportedAccount(String username);
    String approveReport(String username);
    RejectReportResponse rejectReport(String username, Integer report_id);
}
