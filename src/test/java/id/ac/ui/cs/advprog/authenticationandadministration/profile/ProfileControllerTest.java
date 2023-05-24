package id.ac.ui.cs.advprog.authenticationandadministration.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authenticationandadministration.controller.ProfileController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProfileController.class)
@AutoConfigureMockMvc
class ProfileControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ProfileServiceImpl profileService;

    @MockBean
    private JwtService jwtService;

    @Mock
    User user;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testViewProfileByUsername() throws Exception {
        ViewProfileResponse viewProfileResponse = ViewProfileResponse.builder()
                                                .username("test")
                                                .role(UserRole.USER.name())
                                                .profilePicture("link")
                                                .bio("user test")
                                                .applications(null)
                                                .build();

        when(profileService.getProfileByUsername(viewProfileResponse.getUsername())).thenReturn(viewProfileResponse);

        mvc.perform(get(String.format("/v1/profile/view-profile/%s", viewProfileResponse.getUsername()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("viewProfileByUsername"))
                .andExpect(jsonPath("username").value(viewProfileResponse.getUsername()))
                .andExpect(jsonPath("role").value(viewProfileResponse.getRole()))
                .andExpect(jsonPath("profilePicture").value(viewProfileResponse.getProfilePicture()))
                .andExpect(jsonPath("bio").value(viewProfileResponse.getBio()))
                .andExpect(jsonPath("applications").value(viewProfileResponse.getApplications()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(profileService, atLeastOnce()).getProfileByUsername(any(String.class));
    }

    @Test
    void testEditProfile() throws Exception {
        EditProfileRequest editProfileRequest = EditProfileRequest.builder()
                .profilePicture("new link profile picture")
                .bio("new bio")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(editProfileRequest);

        mvc.perform(post("/v1/profile/update-profile/testUser")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("updateProfile"))
                .andReturn();
    }
}
