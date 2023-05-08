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
import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc
public class ControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ReportServiceImpl reportService;

    @MockBean
    private JwtService jwtService;

    @Mock
    User user;

//    RejectReportResponse rejectReportResponse;

    @BeforeEach
    void setUp(){
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

//        approveReportResponse = "Blocked User with username Test1";
//
//        rejectReportResponse = RejectReportResponse.builder()
//                            .haveReport(true)
//                            .build();
    }

    @Test
    void testGetAllReportedAccount() throws Exception {
        ArrayList<String> listUser = new ArrayList<String>();
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
        User test1 = User.builder()
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
                .information("report 1 untuk test1")
                .user(test1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        Report report2 = Report.builder()
                .id(2)
                .information("report 2 untuk test1")
                .user(test1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport = new ArrayList<Report>();
        listReport.add(report1);
        listReport.add(report2);

        test1.setReportList(listReport);


        DetailReportedResponse detailReportedResponse = DetailReportedResponse.builder()
                                                        .username(test1.getUsername())
                                                        .totalReports(listReport.size())
                                                        .listReports(listReport)
                                                        .build();

        when(reportService.getReportedAccount(any(String.class))).thenReturn(detailReportedResponse);

//        System.out.println(new JSONArray(listReport));
//        System.out.println(listReport);

        mvc.perform(get("/v1/report/detail-account/test1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getReportedAccount"))
                .andExpect(jsonPath("username").value(detailReportedResponse.getUsername()))
                .andExpect(jsonPath("totalReports").value(detailReportedResponse.getTotalReports()))
//                .andExpect(jsonPath("listReports").value(new ArrayList<>()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void testApproveReport() throws Exception {
        User test1 = User.builder()
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
                .information("report 1 untuk test1")
                .user(test1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        Report report2 = Report.builder()
                .id(2)
                .information("report 2 untuk test1")
                .user(test1)
                .dateReport(new Timestamp(System.currentTimeMillis()))
                .build();

        ArrayList<Report> listReport = new ArrayList<Report>();
        listReport.add(report1);
        listReport.add(report2);

        test1.setReportList(listReport);

        String approveReportResponse = "Blocked User with username test1";

        when(reportService.approveReport(any(String.class))).thenReturn(approveReportResponse);

        mvc.perform(delete("/v1/report/approve/test1")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("approveReport"))
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("Blocked User with username test1"))
                .andReturn();
    }
}
