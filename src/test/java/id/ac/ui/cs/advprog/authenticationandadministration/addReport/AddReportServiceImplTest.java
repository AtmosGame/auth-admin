package id.ac.ui.cs.advprog.authenticationandadministration.addReport;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.UserReportRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.InformationNullException;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddReportServiceImplTest {
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
    void whenAddReportShouldReturnReport() {
        String username = "testUser";
        String username2 = "testUser2";

        User user1 = User.builder()
                .id(1)
                .username(username)
                .password("passwordTest1")
                .role(UserRole.USER)
                .profilePicture("link to profilePicture")
                .bio("bio profile picture")
                .applications(null)
                .active(true)
                .build();

        user1.setReportList(new ArrayList<Report>());

        User user2 = User.builder()
                .id(2)
                .username(username2)
                .password("passwordTest2")
                .role(UserRole.USER)
                .profilePicture("link to profilePicture")
                .bio("bio profile picture")
                .applications(null)
                .active(true)
                .build();

        user2.setReportList(new ArrayList<Report>());
        Report report1 = Report.builder()
                .id(1)
                .information("report orang")
                .user(user1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        UserReportRequest request = UserReportRequest.builder()
                .information("report orang")
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(username2)).thenReturn(Optional.of(user2));
        when(reportRepository.save(any(Report.class))).thenReturn(report1);

        Report report = reportService.createReportUser(username, username2, request);

        Assertions.assertEquals(report1.getInformation(), report.getInformation());
    }

    @Test
    void whenAddReportWithInformationIsNullShouldThrowException() {
        UserReportRequest userReportRequest = UserReportRequest.builder()
                                            .information(null)
                                            .build();

        Assertions.assertThrows(InformationNullException.class, () -> {
            reportService.createReportUser("user", "userReport", userReportRequest);
        });
    }
}
