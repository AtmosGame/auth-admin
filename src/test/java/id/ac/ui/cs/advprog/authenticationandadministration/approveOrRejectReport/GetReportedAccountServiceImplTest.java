package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetReportedAccountServiceImplTest {
    private ReportServiceImpl reportService;
    private UserRepository userRepository;

    private final String username = "testUser";

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        ReportRepository reportRepository = mock(ReportRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        UserService userService = new UserServiceImpl(userRepository, tokenRepository);
        reportService = new ReportServiceImpl(userRepository, reportRepository, userService);
    }

    @Test
    void whenGetReportedAccountShouldReturnDetailOfReportedAccount(){
        User user = User.builder()
                .id(1)
                .username(username)
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

        Report report2 = Report.builder()
                .id(2)
                .information("report 2 for test1")
                .user(user)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport = new ArrayList<>();
        listReport.add(report1);
        listReport.add(report2);

        user.setReportList(listReport);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        DetailReportedResponse response = reportService.getReportedAccount(user.getUsername());

        Assertions.assertEquals(user.getUsername(), response.getUsername());
        Assertions.assertEquals(user.getReportList().size(), response.getTotalReports());
        Assertions.assertEquals(user.getReportList(), response.getListReports());
    }

    @Test
    void whenGetReportedAccountWithUserIsNotFoundShouldThrowException(){
        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
            reportService.getReportedAccount(username);
        });
    }

    @Test
    void whenGetReportedAccountWithUserIsAdministratorShouldThrowException(){
        User user = User.builder()
                .id(1)
                .username(username)
                .password("passwordTestUser")
                .role(UserRole.ADMIN)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications(null)
                .active(true)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserIsAdministratorException.class, () -> {
            reportService.getReportedAccount(username);
        });
    }

    @Test
    void whenGetReportedAccountWithUserHaveBeenBlockedShouldThrowException(){
        User user = User.builder()
                .id(1)
                .username(username)
                .password("passwordTestUser")
                .role(UserRole.USER)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications(null)
                .active(false)
                .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserHasBeenBlockedException.class, () -> {
            reportService.getReportedAccount(username);
        });
    }

    @Test
    void whenGetReportedAccountWithUserDoesntHaveReportShouldThrowException(){
        User user = User.builder()
                .id(1)
                .username(username)
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
            reportService.getReportedAccount(username);
        });
    }
}
