package id.ac.ui.cs.advprog.authenticationandadministration.approveOrRejectReport;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.ReportController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.DetailReportedResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.RejectReportResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.ReportedAccountResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReportServiceImpl service;

    ReportedAccountResponse reportedAccountResponse;
    DetailReportedResponse detailReportedResponse;
    String approveReportResponse;
    RejectReportResponse rejectReportResponse;

    @BeforeEach
    void setUp(){
        reportedAccountResponse = ReportedAccountResponse.builder()
                                .listUser(new HashSet<>(Arrays.asList("Test1", "Test2")))
                                .build();

        detailReportedResponse = DetailReportedResponse.builder()
                                .username("Test1")
                                .totalReports(1)
                                .listReports(Arrays.asList(Report.builder()
                                                            .id(3)
                                                            .information("report 3")
                                                            .dateReport(ZonedDateTime.now(ZoneId.of("Asia/Jakarta")))
                                                            .build()))
                                .build();

        approveReportResponse = "Blocked User with username Test1";

        rejectReportResponse = RejectReportResponse.builder()
                            .haveReport(true)
                            .build();
    }

    @Test
    void testGetAllReportedAccount() throws Exception{
        when(service.getAllReportedAccount()).thenReturn(reportedAccountResponse);
        List<String> reportedAccountList = new ArrayList<>(reportedAccountResponse.getListUser());

        mvc.perform(get("/v1/report/reported-account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllReportedAccount"))
                .andExpect(jsonPath("listUser[0]").value(reportedAccountList.get(0)))
                .andExpect(jsonPath("listUser[1]").value(reportedAccountList.get(1)));
    }
}
