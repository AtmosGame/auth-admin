package id.ac.ui.cs.advprog.authenticationandadministration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authenticationandadministration.controller.UserController;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserServiceImpl userService;

    @Mock
    private UserController userController;

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
        MockitoAnnotations.openMocks(this);
        userController = new UserController();
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

    @Test
    void testSearchUsers() throws Exception {
        // Arrange

        List<User> users = new ArrayList<>();

        User user1 = User.builder()
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

        User user2 = User.builder()
                .id(2)
                .username("test2")
                .password("passwordTest2")
                .role(UserRole.USER)
                .profilePicture("link to profil picture test1")
                .bio("bio test1")
                .applications(null)
                .active(true)
                .reportList(new ArrayList<>())
                .build();

        users.add(user1);
        users.add(user2);

        SearchUserRequest request = new SearchUserRequest();

        request.setUsername("test1");

        when(userService.searchUsers(any(SearchUserRequest.class))).thenReturn(users);


        // Assert
        ObjectMapper objectMapper = new ObjectMapper();
        String mapper = objectMapper.writeValueAsString(request);

        mvc.perform(MockMvcRequestBuilders.post("/v1/user/search-user")
                        .content(mapper)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("searchUsers"))
                .andReturn();


        // Verify that the userService's searchUsers method was called with the correct request
        verify(userService, times(1)).searchUsers(request);
    }
}
