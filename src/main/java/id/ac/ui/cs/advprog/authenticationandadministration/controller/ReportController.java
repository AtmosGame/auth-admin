package id.ac.ui.cs.advprog.authenticationandadministration.controller;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    // By @Eugenius Mario
    // Note: @Rifqi Farel ini masih ada test case yang fail,
    // tolong diperbaiki bagian createReportUser kan di fix/approve-reject-report
    // lu ilangin jadi testnya gagal
//    @PostMapping("/report-user/{username}/{usernameReported}")
//    public ResponseEntity<String> createReportUser(@PathVariable String username, @PathVariable String usernameReported, @RequestBody String information){
//        String response = reportService.createReportUser(username, usernameReported, information).getInformation();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/reported-account")
    public ResponseEntity<ReportedAccountResponse> getAllReportedAccount(){
        return new ResponseEntity<>(reportService.getAllReportedAccount(), HttpStatus.OK);
    }

    @GetMapping("/detail-account/{username}")
    public ResponseEntity<DetailReportedResponse> getReportedAccount(@PathVariable String username){
        DetailReportedResponse detailReported = reportService.getReportedAccount(username);
        return new ResponseEntity<>(detailReported, HttpStatus.OK);
    }

    @DeleteMapping("/approve/{username}")
    public ResponseEntity<String> approveReport(@PathVariable String username){
        String response = reportService.approveReport(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/reject/{username}/{report_id}")
    public ResponseEntity<RejectReportResponse> rejectReport(@PathVariable String username, @PathVariable Integer report_id){
        return new ResponseEntity<>(reportService.rejectResponse(username, report_id), HttpStatus.OK);
    }
}
