package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.ReportDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserAndReportNotMatchedException;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApproveOrRejectReportServiceImplTest {
    private ReportServiceImpl reportService;
    private UserRepository userRepository;
    private ReportRepository reportRepository;
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        reportRepository = mock(ReportRepository.class);
        UserService userService = new UserServiceImpl(userRepository, tokenRepository);
        reportService = new ReportServiceImpl(userRepository, reportRepository, userService);
    }

    @Test
    void whenGetAllReportedAccountShouldReturnListOfReportedAccount(){
        ArrayList<String> listUser = new ArrayList<>();
        listUser.add("test1");
        listUser.add("test2");
        listUser.add("test3");

        when(userRepository.findAllHaveReportedUser()).thenReturn(listUser);

        ReportedAccountResponse response = reportService.getAllReportedAccount();

        Assertions.assertEquals(listUser, response.getListUser());
    }

    @Test
    void whenGetReportedAccountShouldReturnDetailOfReportedAccount(){
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
            reportService.getReportedAccount(any(String.class));
        });
    }

    @Test
    void whenGetReportedAccountWithUserIsAdministratorShouldThrowException(){
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
            reportService.getReportedAccount(user.getUsername());
        });
    }

    @Test
    void whenGetReportedAccountWithUserHaveBeenBlockedShouldThrowException(){
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
            reportService.getReportedAccount(user.getUsername());
        });
    }

    @Test
    void whenGetReportedAccountWithUserDoesntHaveReportShouldThrowException(){
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
            reportService.getReportedAccount(user.getUsername());
        });
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

    @Test
    void whenRejectReportShouldReturnHaveReport() {
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
        when(reportRepository.findById(report1.getId())).thenReturn(Optional.of(report1));

        RejectReportResponse response = reportService.rejectReport(user.getUsername(), report1.getId());

        Assertions.assertEquals(true, response.getHaveReport());
        verify(reportRepository, times(1)).deleteById(report1.getId());
    }

    @Test
    void whenRejectReportShouldReturnDoesntHaveReport() {
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
        when(reportRepository.findById(report1.getId())).thenReturn(Optional.of(report1));

        RejectReportResponse response = reportService.rejectReport(user.getUsername(), report1.getId());

        Assertions.assertEquals(false, response.getHaveReport());
        verify(reportRepository, times(1)).deleteById(report1.getId());
    }

    @Test
    void whenRejectReportWithUserIsNotFoundShouldThrowException(){
        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
            reportService.rejectReport(any(String.class), 1);
        });
    }

    @Test
    void whenRejectReportWithUserIsAdministratorShouldThrowException(){
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
            reportService.rejectReport(user.getUsername(), any(Integer.class));
        });
    }

    @Test
    void whenRejectReportWithUserHaveBeenBlockedShouldThrowException(){
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
            reportService.rejectReport(user.getUsername(), any(Integer.class));
        });
    }

    @Test
    void whenRejectReportWithUserDoesntHaveReportShouldThrowException(){
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
            reportService.rejectReport(user.getUsername(), any(Integer.class));
        });
    }

    @Test
    void whenRejectReportWithReportDoesntMatchWithUserShouldThrowException(){
        User user1 = User.builder()
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
                .user(user1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport1 = new ArrayList<>();
        listReport1.add(report1);

        user1.setReportList(listReport1);

        User user2 = User.builder()
                .id(2)
                .username("test2")
                .password("passwordTest2")
                .role(UserRole.DEVELOPER)
                .profilePicture("link to profilePicture")
                .bio("bio profile picture")
                .applications(null)
                .active(true)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .information("report 2 for test2")
                .user(user2)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport2 = new ArrayList<>();
        listReport2.add(report2);

        user2.setReportList(listReport2);

        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));
        when(reportRepository.findById(report2.getId())).thenReturn(Optional.of(report2));

        Assertions.assertThrows(UserAndReportNotMatchedException.class, () -> {
            reportService.rejectReport(user1.getUsername(), report2.getId());
        });
    }

    @Test
    void whenRejectReportWithReportIsNotFoundShouldThrowException(){
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

        Assertions.assertThrows(ReportDoesNotExistException.class, () -> {
            reportService.rejectReport(user.getUsername(), 2);
        });
    }
}
