package id.ac.ui.cs.advprog.authenticationandadministration.user;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.CurrentUserResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.user.SearchUserRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.InvalidTokenException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.Token;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCurrentUserValidUsername() {
        // Arrange
        String username = "john";

        User user = User.builder()
                .id(1)
                .username("john")
                .role(UserRole.USER)
                .profilePicture("profile.jpg")
                .bio("I am John")
                .active(true)
                .reportList(Collections.emptyList())
                .build();

        Token token = Token.builder()
                .id(1)
                .token("token")
                .user(user)
                .build();

        Mockito.when(userRepository.getUser("john")).thenReturn(user);
        Mockito.when(tokenRepository.getAllByUserId(1)).thenReturn(Collections.singletonList(token));

        // Act
        CurrentUserResponse response = userService.getCurrentUser(username);

        // Assert
        assertEquals(1, response.getId());
        assertEquals("john", response.getUsername());
        assertEquals(UserRole.USER, response.getRole());
        assertEquals("profile.jpg", response.getProfilePicture());
        assertEquals("I am John", response.getBio());
        assertTrue(response.getActive());
        assertEquals(Collections.emptyList(), response.getReportList());

        Mockito.verify(userRepository, Mockito.times(1)).getUser("john");
        Mockito.verify(tokenRepository, Mockito.times(1)).getAllByUserId(1);
    }

    @Test
    public void testCurrentUserInvalidToken() {
        // Arrange
        String username = "john";

        User user = User.builder()
                .id(1)
                .username("john")
                .role(UserRole.USER)
                .build();

        Mockito.when(userRepository.getUser("john")).thenReturn(user);
        Mockito.when(tokenRepository.getAllByUserId(1)).thenReturn(Collections.emptyList());

        // Act & Assert
        Assertions.assertThrows(InvalidTokenException.class, () -> {
            userService.getCurrentUser(username);
        });

        Mockito.verify(userRepository, Mockito.times(1)).getUser("john");
        Mockito.verify(tokenRepository, Mockito.times(1)).getAllByUserId(1);
    }

    @Test
    public void testSearchUsersEmptyUsername() {
        // Arrange
        SearchUserRequest request = new SearchUserRequest();

        // Act
        List<User> users = userService.searchUsers(request);

        // Assert
        assertEquals(Collections.emptyList(), users);
    }

    @Test
    public void testSearchUsersNonEmptyUsername() {
        // Arrange
        SearchUserRequest request = new SearchUserRequest();
        request.setUsername("john");

        User user1 = User.builder()
                .id(1)
                .username("john")
                .build();

        User user2 = User.builder()
                .id(2)
                .username("johanna")
                .build();

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);

        Mockito.when(userRepository.findByUsernameContainingIgnoreCase("john")).thenReturn(expectedUsers);

        // Act
        List<User> users = userService.searchUsers(request);

        // Assert
        assertEquals(expectedUsers, users);
    }

    @Test
    public void testSearchUsersWithNoResult() {
        SearchUserRequest request = new SearchUserRequest();
        request.setUsername("nonexistentuser");

        List<User> result = userService.searchUsers(request);

        assertTrue(result.isEmpty());
    }

}