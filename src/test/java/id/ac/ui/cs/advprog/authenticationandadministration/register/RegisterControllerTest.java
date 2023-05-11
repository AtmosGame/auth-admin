package id.ac.ui.cs.advprog.authenticationandadministration.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authenticationandadministration.controller.AuthController;
import id.ac.ui.cs.advprog.authenticationandadministration.controller.ProfileController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc
public class RegisterControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AuthServiceImpl service;
    @MockBean
    private JwtService jwtService;
    @Mock
    User user;
    Object bodyContent;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        user = User.builder()
                .username("test")
                .password("test")
                .role(UserRole.USER)
                .build();

        bodyContent = new Object() {
            public final Integer id = 1;
        };
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postRegisterRoleAdmin() throws Exception {
        // Prepare mock user data
        RegisterRequest request = RegisterRequest.builder()
                .username("test_admin")
                .password("password")
                .role(UserRole.ADMIN)
                .build();

        RegisterResponse response = RegisterResponse.builder()
                .token("jwt-token")
                .username("test_admin")
                .role("ADMIN")
                .build();

        // Mock the service method
        when(service.register(any(RegisterRequest.class))).thenReturn(response);

        // Convert request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test_admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postRegisterRoleDeveloper() throws Exception {
        // Prepare mock user data
        RegisterRequest request = RegisterRequest.builder()
                .username("test_developer")
                .password("password")
                .role(UserRole.DEVELOPER)
                .build();

        RegisterResponse response = RegisterResponse.builder()
                .token("jwt-token")
                .username("test_developer")
                .role("DEVELOPER")
                .build();

        // Mock the service method
        when(service.register(any(RegisterRequest.class))).thenReturn(response);

        // Convert request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test_developer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("DEVELOPER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postRegisterRoleUser() throws Exception {
        // Prepare mock user data
        RegisterRequest request = RegisterRequest.builder()
                .username("test_user")
                .password("password")
                .role(UserRole.USER)
                .build();

        RegisterResponse response = RegisterResponse.builder()
                .token("jwt-token")
                .username("test_user")
                .role("USER")
                .build();

        // Mock the service method
        when(service.register(any(RegisterRequest.class))).thenReturn(response);

        // Convert request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test_user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void postRegisterUserAlreadyExists() throws Exception {
        // Prepare mock user data
        RegisterRequest request = RegisterRequest.builder()
                .username("atmos")
                .password("password")
                .role(UserRole.ADMIN)
                .build();

        RegisterResponse response = RegisterResponse.builder()
                .token("jwt-token")
                .username("atmos")
                .role("ADMIN")
                .build();

        // Mock the service method
        when(service.register(any(RegisterRequest.class))).thenReturn(response);

        // Convert request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("atmos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ADMIN"));
        // Perform the POST request
        mvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("atmos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ADMIN"));
    }
}
