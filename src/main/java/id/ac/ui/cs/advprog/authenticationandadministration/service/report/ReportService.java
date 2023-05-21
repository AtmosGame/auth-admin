package id.ac.ui.cs.advprog.authenticationandadministration.service.report;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.UserReportRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface ReportService {
    ReportedAccountResponse getAllReportedAccount();
    DetailReportedResponse getReportedAccount(String username);
    CompletableFuture<Void> approveReport(String username);
    RejectReportResponse rejectReport(String username, Integer reportId);
    UserReportRequest createReportUser(String username, String usernameReported, UserReportRequest information);
}
