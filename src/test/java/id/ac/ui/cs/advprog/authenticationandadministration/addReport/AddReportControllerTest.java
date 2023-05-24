package id.ac.ui.cs.advprog.authenticationandadministration.addReport;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authenticationandadministration.controller.ReportController;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.service.report.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.report.UserReportRequest;
import org.springframework.test.web.servlet.MockMvc;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc
class AddReportControllerTest {

    @MockBean
    private ReportServiceImpl reportService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    private JwtService jwtService;

    @Mock
    User user;

    @BeforeEach
    void setUp(){
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testCreateReportUser() throws Exception {
        UserReportRequest request = UserReportRequest.builder()
                .information("reason")
                .build();
        when(reportService.createReportUser("user1", "user2", request)).thenReturn(new Report());
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post(String.format("/v1/report/report-user/%s/%s", "user1", "user2"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
