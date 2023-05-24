package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.*;
import id.ac.ui.cs.advprog.authenticationandadministration.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report-user/{username}/{usernameReported}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<String> createReportUser(
            @PathVariable String username,
            @PathVariable String usernameReported,
            @RequestBody UserReportRequest request
    ) {
        String response = reportService.createReportUser(username, usernameReported, request).getInformation();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reported-account")
    @PreAuthorize("hasAuthority('report:read')")
    public ResponseEntity<ReportedAccountResponse> getAllReportedAccount() {
        ReportedAccountResponse allReportedAccount = reportService.getAllReportedAccount();
        return new ResponseEntity<>(allReportedAccount, HttpStatus.OK);
    }

    @GetMapping("/detail-account/{username}")
    @PreAuthorize("hasAuthority('report:read')")
    public ResponseEntity<DetailReportedResponse> getReportedAccount(@PathVariable String username) {
        DetailReportedResponse detailReported = reportService.getReportedAccount(username);
        return new ResponseEntity<>(detailReported, HttpStatus.OK);
    }

    @DeleteMapping("/approve/{username}")
    @PreAuthorize("hasAuthority('report:delete')")
    public ResponseEntity<String> approveReport(@PathVariable String username) {
        reportService.approveReport(username);
        String approveReportResponse = "Block User with username " + username;
        return new ResponseEntity<>(approveReportResponse, HttpStatus.OK);
    }

    @DeleteMapping("/reject/{username}/{reportId}")
    @PreAuthorize("hasAuthority('report:delete')")
    public ResponseEntity<RejectReportResponse> rejectReport(
            @PathVariable String username,
            @PathVariable Integer reportId
    ) {
        RejectReportResponse rejectReportResponse = reportService.rejectReport(username, reportId);
        return new ResponseEntity<>(rejectReportResponse, HttpStatus.OK);
    }
}
