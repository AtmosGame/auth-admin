package id.ac.ui.cs.advprog.authenticationandadministration.user;

import id.ac.ui.cs.advprog.authenticationandadministration.controller.UserController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserRepository userRepository;

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
    void testGetCurrentUserProfile() throws Exception {
        CurrentUserResponse response = CurrentUserResponse.builder()
                                    .id(1)
                                    .username("test1")
                                    .role(UserRole.USER)
                                    .profilePicture("link to profil picture test1")
                                    .bio("bio test1")
                                    .applications(null)
                                    .active(true)
                                    .reportList(new ArrayList<>())
                                    .build();

        when(userService.getCurrentUser(response.getUsername())).thenReturn(response);

        mvc.perform(get("/v1/user/current")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getCurrentUserProfile"))
                .andReturn();
    }

    @Test
    void testGetUserNonAdmin() throws Exception {
        User user = User.builder()
                        .id(1)
                        .username("test1")
                        .password("passwordTest1")
                        .role(UserRole.USER)
                        .profilePicture("link to profil picture test1")
                        .bio("bio test1")
                        .applications(null)
                        .active(true)
                        .reportList(new ArrayList<>())
                        .build();

        when(userService.getUserNonAdminByUsername(user.getUsername())).thenReturn(user);

        mvc.perform(get(String.format("/v1/user/get-user-non-admin/%s", user.getUsername()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getUserNonAdmin"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("username").value(user.getUsername()))
                .andExpect(jsonPath("password").value(user.getPassword()))
                .andExpect(jsonPath("role").value(user.getRole().name()))
                .andExpect(jsonPath("profilePicture").value(user.getProfilePicture()))
                .andExpect(jsonPath("bio").value(user.getBio()))
                .andExpect(jsonPath("applications").value(user.getApplications()))
                .andExpect(jsonPath("active").value(user.getActive()))
                .andExpect(jsonPath("reportList").value(user.getReportList()))
                .andReturn();

        verify(userService, atLeastOnce()).getUserNonAdminByUsername(user.getUsername());
    }

    @Test
    void testGetUser() throws Exception {
        User user = User.builder()
                .id(1)
                .username("test1")
                .password("passwordTest1")
                .role(UserRole.USER)
                .profilePicture("link to profil picture test1")
                .bio("bio test1")
                .applications(null)
                .active(true)
                .reportList(new ArrayList<>())
                .build();

        when(userService.getUserByUsername(user.getUsername())).thenReturn(user);

        mvc.perform(get(String.format("/v1/user/get-user/%s", user.getUsername()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getUser"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("username").value(user.getUsername()))
                .andExpect(jsonPath("password").value(user.getPassword()))
                .andExpect(jsonPath("role").value(user.getRole().name()))
                .andExpect(jsonPath("profilePicture").value(user.getProfilePicture()))
                .andExpect(jsonPath("bio").value(user.getBio()))
                .andExpect(jsonPath("applications").value(user.getApplications()))
                .andExpect(jsonPath("active").value(user.getActive()))
                .andExpect(jsonPath("reportList").value(user.getReportList()))
                .andReturn();

        verify(userService, atLeastOnce()).getUserByUsername(user.getUsername());
    }






//    @Test
//    public void testSearchUsers() throws Exception {
//        // Mock request and response
//        SearchUserRequest request = new SearchUserRequest();
//        request.setUsername("john");
//
//        User user1 = new User();
//        user1.setId(1);
//        user1.setUsername("john.doe");
//        user1.setRole(UserRole.USER);
//
//        User user2 = new User();
//        user2.setId(2);
//        user2.setUsername("john.smith");
//        user2.setRole(UserRole.ADMIN);
//
//        List<User> users = Arrays.asList(user1, user2);
//
//        when(userService.searchUsers(any(SearchUserRequest.class))).thenReturn(users);
//
//        // Build the request
//        MockHttpServletRequestBuilder requestBuilder = post("/v1/user/search-user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"username\":\"john\"}");
//
//        // Perform the request and assert the response
//        mvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(handler().methodName("searchUsers"))
//                .andExpect(jsonPath("$[0].id").value(user1.getId()))
//                .andExpect(jsonPath("$[0].username").value(user1.getUsername()))
//                .andExpect(jsonPath("$[0].role").value(user1.getRole().name()))
//                .andExpect(jsonPath("$[1].id").value(user2.getId()))
//                .andExpect(jsonPath("$[1].username").value(user2.getUsername()))
//                .andExpect(jsonPath("$[1].role").value(user2.getRole().name()))
//                .andReturn();
//    }
}
