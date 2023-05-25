package id.ac.ui.cs.advprog.authenticationandadministration.logout;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.LogoutRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.LogoutResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.JWTToken;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogoutServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogoutUserNotLoggedIn() {
        // Arrange
        LogoutRequest request = new LogoutRequest("john");
        User user = User.builder().id(1).username("john").build();

        when(userRepository.getUser("john")).thenReturn(user);
        when(tokenRepository.getAllByUserId(1)).thenReturn(Collections.emptyList());

        // Act
        LogoutResponse response = authService.logout(request);

        // Assert
        assertEquals("This user is not currently logged in", response.getMessage());

        verify(userRepository, times(1)).getUser("john");
        verify(tokenRepository, times(1)).getAllByUserId(1);
        verifyNoMoreInteractions(userRepository, tokenRepository);
    }

    @Test
    void testLogoutUserLoggedIn() {
        // Arrange
        LogoutRequest request = new LogoutRequest("john");
        User user = User.builder().id(1).username("john").build();
        JWTToken token = JWTToken.builder()
                .id(1)
                .token("token")
                .user(user)
                .build();

        when(userRepository.getUser("john")).thenReturn(user);
        when(tokenRepository.getAllByUserId(1)).thenReturn(List.of(token));

        // Act
        LogoutResponse response = authService.logout(request);

        // Assert
        assertEquals("Logout successful", response.getMessage());

        verify(userRepository, times(1)).getUser("john");
        verify(tokenRepository, times(1)).getAllByUserId(1);
        verify(tokenRepository, times(1)).deleteAllByUserId(1);
        verifyNoMoreInteractions(userRepository, tokenRepository);
    }

    @Test
    void testRevokeAllUserTokens() {
        // Arrange
        User user = User.builder().id(1).username("john").build();

        // Act
        authService.revokeAllUserTokens(user);

        // Assert
        verify(tokenRepository, times(1)).deleteAllByUserId(1);
        verifyNoMoreInteractions(tokenRepository);
    }
}
