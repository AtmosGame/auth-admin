package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
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

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AllReportedAccountServiceImplTest {
    private ReportServiceImpl reportService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        ReportRepository reportRepository = mock(ReportRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
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
}
