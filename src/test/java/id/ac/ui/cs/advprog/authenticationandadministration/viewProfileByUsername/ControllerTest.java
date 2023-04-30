package id.ac.ui.cs.advprog.authenticationandadministration.viewProfileByUsername;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.ProfileController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProfileController.class)
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileServiceImpl profileService;

    ViewProfileResponse response;

    @BeforeEach
    void setUp(){
        response = ViewProfileResponse.builder()
                .username("test")
                .role("user")
                .profilePicture("link")
                .bio("user test")
                .applications(null)
                .build();
    }

//    @Test
//    void testViewProfileByUsername() throws Exception {
//        when(profileService.getProfileByUsername(any(String.class))).thenReturn(response);
//
//        mvc.perform(get("/v1/profile/view-profile/test")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("viewProfileByUsername"))
//                .andExpect(jsonPath("username").value(response.getUsername()))
//                .andExpect(jsonPath("role").value(response.getRole()))
//                .andExpect(jsonPath("profilePicture").value(response.getProfilePicture()))
//                .andExpect(jsonPath("bio").value(response.getBio()))
//                .andExpect(jsonPath("applications").value(response.getApplications()));
//
//        verify(profileService, atLeastOnce()).getProfileByUsername(any(String.class));
//    }
}
