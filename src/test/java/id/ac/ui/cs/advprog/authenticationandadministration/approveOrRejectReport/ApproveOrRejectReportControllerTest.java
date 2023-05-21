package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.ReportController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc
class ApproveOrRejectReportControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ReportServiceImpl reportService;

    @MockBean
    private JwtService jwtService;

    @Mock
    User user;

    User test1;
    Report report1;
    Report report2;

    @BeforeEach
    void setUp(){
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        test1 = User.builder()
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
                .user(test1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        report2 = Report.builder()
                .id(2)
                .information("report 2 for test1")
                .user(test1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport = new ArrayList<>();
        listReport.add(report1);
        listReport.add(report2);

        test1.setReportList(listReport);
    }

    @Test
    void testGetAllReportedAccount() throws Exception {
        ArrayList<String> listUser = new ArrayList<>();
        listUser.add("test1");
        listUser.add("test2");
        listUser.add("test3");

        ReportedAccountResponse reportedAccountResponse = ReportedAccountResponse.builder()
                                                        .listUser(listUser)
                                                        .build();

        when(reportService.getAllReportedAccount()).thenReturn(reportedAccountResponse);

        mvc.perform(get("/v1/report/reported-account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllReportedAccount"))
                .andExpect(jsonPath("listUser").value(listUser))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void testGetReportedAccount() throws Exception {
        DetailReportedResponse detailReportedResponse = DetailReportedResponse.builder()
                                                        .username(test1.getUsername())
                                                        .totalReports(test1.getReportList().size())
                                                        .listReports(test1.getReportList())
                                                        .build();

        when(reportService.getReportedAccount(detailReportedResponse.getUsername())).thenReturn(detailReportedResponse);

        mvc.perform(get(String.format("/v1/report/detail-account/%s", detailReportedResponse.getUsername()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getReportedAccount"))
                .andExpect(jsonPath("username").value(detailReportedResponse.getUsername()))
                .andExpect(jsonPath("totalReports").value(detailReportedResponse.getTotalReports()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void testApproveReport() throws Exception {
        mvc.perform(delete(String.format("/v1/report/approve/%s", test1.getUsername()))
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("approveReport"))
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string(String.format("Block User with username %s", test1.getUsername())))
                .andReturn();
    }

    @Test
    void testRejectReport() throws Exception {
        RejectReportResponse rejectReportResponse = RejectReportResponse.builder()
                                                .haveReport(test1.getReportList().size() - 1 > 0)
                                                .build();

        when(reportService.rejectReport(test1.getUsername(), 1)).thenReturn(rejectReportResponse);

        mvc.perform(delete(String.format("/v1/report/reject/%s/1", test1.getUsername()))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("rejectReport"))
                .andExpect(jsonPath("haveReport").value(rejectReportResponse.getHaveReport()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }
}
