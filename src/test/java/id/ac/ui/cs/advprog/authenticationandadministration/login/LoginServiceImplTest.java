package id.ac.ui.cs.advprog.authenticationandadministration.login;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateSuccessfulAuthentication() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("john", "password");
        User user = User.builder().id(1).username("john").build();
        String jwtToken = "generated_token";

        when(userRepository.findByUsername("john")).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        // Act
        AuthenticationResponse response = authService.authenticate(request);

        // Assert
        assertEquals(jwtToken, response.getToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByUsername("john");
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).addToken(jwtToken, 1);
        verifyNoMoreInteractions(authenticationManager, userRepository, jwtService, tokenRepository);
    }

//    @Test
//    void testLoginWithInvalidPasswordShouldThrowException() {
//
//    }
}
