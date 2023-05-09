package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserAndReportNotMatchedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserDoesNotHaveReportException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.ReportRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.report.ReportServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {
    private ReportServiceImpl reportService;
    private UserRepository userRepository;
    private ReportRepository reportRepository;
    private UserService userService;

    User userHaveReport;
    User userDoesntHaveReport;
    Report report1;
    Report report2;
    Report report3;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        reportRepository = mock(ReportRepository.class);
        userService = mock(UserService.class);
        reportService = new ReportServiceImpl(userRepository, reportRepository, userService);

        userHaveReport = User.builder()
                        .id(1)
                        .username("test1")
                        .password("passwordTest1")
                        .role(UserRole.USER)
                        .profilePicture("link to profilePicture")
                        .bio("bio profile picture")
                        .applications(null)
                        .active(true)
                        .build();

        report1 = Report.builder()
                .id(1)
                .information("report 1 for test1")
                .user(userHaveReport)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        report2 = Report.builder()
                .id(2)
                .information("report 2 for test1")
                .user(userHaveReport)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        report3 = Report.builder()
                .id(3)
                .information("report 3")
                .user(null)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport = new ArrayList<>();
        listReport.add(report1);
        listReport.add(report2);

        userHaveReport.setReportList(listReport);

        userDoesntHaveReport = User.builder()
                            .id(2)
                            .username("test2")
                            .password("passwordTest2")
                            .role(UserRole.DEVELOPER)
                            .profilePicture("link to profilePicture")
                            .bio("bio profile picture")
                            .applications("A, B, C, D, E, F, G")
                            .active(true)
                            .build();
    }

//    @Test
//    void whenGetAllReportedAccountShouldReturnListOfReportedAccount(){
//        ArrayList<String> listUser = new ArrayList<>();
//        listUser.add("test1");
//
//        ReportedAccountResponse reportedAccountResponse = ReportedAccountResponse.builder()
//                                                        .listUser(listUser)
//                                                        .build();
//
//        when(reportService.getAllReportedAccount()).thenReturn(reportedAccountResponse);
//
//        ReportedAccountResponse response = reportService.getAllReportedAccount();
//
//        Assertions.assertEquals(reportedAccountResponse, response);
//
//        verify(reportService, atLeastOnce()).getAllReportedAccount();
//    }

//    @Test
//    void whenGetReportedAccountShouldReturnDetailOfReportedAccount(){
//        DetailReportedResponse detailReportedResponse = DetailReportedResponse.builder()
//                                                    .username(userHaveReport.getUsername())
//                                                    .totalReports(userHaveReport.getReportList().size())
//                                                    .listReports(userHaveReport.getReportList())
//                                                    .build();
//
//        when(reportService.getReportedAccount(any(String.class))).thenReturn(detailReportedResponse);
//
//        DetailReportedResponse response = reportService.getReportedAccount(userHaveReport.getUsername());
//
//        Assertions.assertEquals(detailReportedResponse, response);
//
//        verify(reportService, atLeastOnce()).getReportedAccount(userHaveReport.getUsername());
//    }
//
//    @Test
//    void whenApproveReportShouldBlockedUser(){
//        when(userService.getUserNonAdminByUsername(userHaveReport.getUsername())).thenReturn(userDoesntHaveReport);
//        String response = "Blocked User with username test1";
//        System.out.print(reportService.approveReport(userHaveReport.getUsername()));
//    }
}
