package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserDoesNotHaveReportException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.ReportRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.report.ReportServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApproveReportServiceImplTest {
    private ReportServiceImpl reportService;
    private UserRepository userRepository;
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        reportRepository = mock(ReportRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        UserService userService = new UserServiceImpl(userRepository, tokenRepository);
        reportService = new ReportServiceImpl(userRepository, reportRepository, userService);
    }

    @Test
    void whenApproveReportShouldBlockUser(){
        User user = User.builder()
                .id(1)
                .username("test1")
                .password("passwordTest1")
                .role(UserRole.USER)
                .profilePicture("link to profilePicture")
                .bio("bio profile picture")
                .applications(null)
                .active(true)
                .build();

        Report report1 = Report.builder()
                .id(1)
                .information("report 1 for test1")
                .user(user)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport = new ArrayList<>();
        listReport.add(report1);

        user.setReportList(listReport);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        String response = reportService.approveReport(user.getUsername());

        Assertions.assertEquals(String.format("Blocked User with username %s", user.getUsername()), response);
        verify(userRepository, times(1)).blockedUserByUsername(user.getUsername());
        verify(reportRepository, times(1)).deleteAll(listReport);
    }

    @Test
    void whenApproveReportWithUserIsNotFoundShouldThrowException(){
        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
            reportService.approveReport(any(String.class));
        });
    }

    @Test
    void whenApproveReportWithUserIsAdministratorShouldThrowException(){
        User user = User.builder()
                .id(1)
                .username("testuser")
                .password("passwordTestUser")
                .role(UserRole.ADMIN)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications(null)
                .active(true)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserIsAdministratorException.class, () -> {
            reportService.approveReport(user.getUsername());
        });
    }

    @Test
    void whenApproveReportWithUserHaveBeenBlockedShouldThrowException(){
        User user = User.builder()
                .id(1)
                .username("testuser")
                .password("passwordTestUser")
                .role(UserRole.USER)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications(null)
                .active(false)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserHasBeenBlockedException.class, () -> {
            reportService.approveReport(user.getUsername());
        });
    }

    @Test
    void whenApproveReportWithUserDoesntHaveReportShouldThrowException(){
        User user = User.builder()
                .id(1)
                .username("testuser")
                .password("passwordTestUser")
                .role(UserRole.DEVELOPER)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications("A, B, C, D")
                .active(true)
                .reportList(new ArrayList<>())
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserDoesNotHaveReportException.class, () -> {
            reportService.approveReport(user.getUsername());
        });
    }
}
