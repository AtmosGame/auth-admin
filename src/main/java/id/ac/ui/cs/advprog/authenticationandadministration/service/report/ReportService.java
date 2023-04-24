package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.UserReportRequest;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    ReportedAccountResponse getAllReportedAccount();
    DetailReportedResponse getReportedAccount(String username);
    RejectReportResponse rejectResponse(String username, Integer report_id);
    String approveReport(String username);
    UserReportRequest createReportUser(String username, String usernameReported,String information);
}
