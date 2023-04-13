package id.ac.ui.cs.advprog.authenticationandadministration.service.Report;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public interface ReportService {
    ReportedAccountResponse getAllReportedAccount();
    DetailReportedResponse getReportedAccount(String username);
    RejectReportResponse rejectResponse(String username, Integer report_id);
    String approveReport(String username);
}
