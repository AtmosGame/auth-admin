package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.UserReportRequest;
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
    public ResponseEntity<String> createReportUser(@PathVariable String username, @PathVariable String usernameReported, @RequestBody UserReportRequest request){
        String response = reportService.createReportUser(username, usernameReported, request).getInformation();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/reported-account")
    @PreAuthorize("hasAuthority('report:read')")
    public ResponseEntity<ReportedAccountResponse> getAllReportedAccount(){
        ReportedAccountResponse response = null;
        response = reportService.getAllReportedAccount();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detail-account/{username}")
    @PreAuthorize("hasAuthority('report:read')")
    public ResponseEntity<DetailReportedResponse> getReportedAccount(@PathVariable String username){
        DetailReportedResponse detailReported = reportService.getReportedAccount(username);
        return new ResponseEntity<>(detailReported, HttpStatus.OK);
    }

    @DeleteMapping("/approve/{username}")
    @PreAuthorize("hasAuthority('report:delete')")
    public ResponseEntity<String> approveReport(@PathVariable String username){
        String response = reportService.approveReport(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/reject/{username}/{report_id}")
    @PreAuthorize("hasAuthority('report:delete')")
    public ResponseEntity<RejectReportResponse> rejectReport(@PathVariable String username, @PathVariable Integer report_id){
        return new ResponseEntity<>(reportService.rejectReport(username, report_id), HttpStatus.OK);
    }
}
