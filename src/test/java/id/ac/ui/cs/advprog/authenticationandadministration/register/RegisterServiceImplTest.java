package id.ac.ui.cs.advprog.authenticationandadministration.register;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.PasswordIsEmptyException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.PasswordMinimalException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UsernameAlreadyExistsException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UsernameIsEmptyException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

class RegisterServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSuccessfulRegistration() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("john");
        registerRequest.setPassword("password");
        registerRequest.setRole(UserRole.USER);

        User existingUser = User.builder()
                .id(1)
                .username("john")
                .build();

        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("jwtToken");

        // Act
        var response = authService.register(registerRequest);

        // Assert
        Assertions.assertEquals("jwtToken", response.getToken());
        Assertions.assertEquals("john", response.getUsername());
        Assertions.assertEquals("USER", response.getRole());

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void whenUsernameAlreadyExists() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("john");
        registerRequest.setPassword("password");
        registerRequest.setRole(UserRole.USER);

        User existingUser = User.builder()
                .id(1)
                .username("john")
                .build();

        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.of(existingUser));

        // Act & Assert
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> {
            authService.register(registerRequest);
        });

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
        Mockito.verify(tokenRepository, Mockito.never()).addToken(Mockito.anyString(), Mockito.anyInt());
    }

    @Test
    void whenUsernameIsEmpty() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("");
        registerRequest.setPassword("password");
        registerRequest.setRole(UserRole.USER);

        // Act & Assert
        Assertions.assertThrows(UsernameIsEmptyException.class, () -> {
            authService.register(registerRequest);
        });

        // Verify that userRepository.save() is not called
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void whenPasswordIsEmpty() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("emariosss");
        registerRequest.setPassword("");
        registerRequest.setRole(UserRole.USER);

        // Act & Assert
        Assertions.assertThrows(PasswordIsEmptyException.class, () -> {
            authService.register(registerRequest);
        });

        // Verify that userRepository.save() is not called
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    void whenPasswordBelowEightCharacter() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("emariossss");
        registerRequest.setPassword("pass");
        registerRequest.setRole(UserRole.USER);

        // Act & Assert
        Assertions.assertThrows(PasswordMinimalException.class, () -> {
            authService.register(registerRequest);
        });

        // Verify that userRepository.save() is not called
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }
}
