package id.ac.ui.cs.advprog.authenticationandadministration.login;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.AuthController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.LogoutResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc
class LoginControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthServiceImpl service;

    @MockBean
    private JwtService jwtService;

    Object bodyContent;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        bodyContent = new Object();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postLoginRoleAdmin() throws Exception {
        // Given
        when(service.logout(any())).thenReturn(new LogoutResponse("Logout successful"));
        // Login first
        mvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .content("{\"username\":\"atmos\",\"password\":\"pass1234\"}")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
