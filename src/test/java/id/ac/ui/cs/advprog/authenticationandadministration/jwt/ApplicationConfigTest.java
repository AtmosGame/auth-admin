package id.ac.ui.cs.advprog.authenticationandadministration.jwt;

import id.ac.ui.cs.advprog.authenticationandadministration.core.config.ApplicationConfig;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserDetailsService() {
        User user = User.builder()
                .username("john")
                .password("password")
                .build();
        userRepository.save(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
        // Arrange
        String username = "john";
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(User.builder()
                .username(username)
                .password("password")
                .build()));
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);

        // Act
        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
    }

    @Test
    public void testAuthenticationProvider() {
        // Arrange
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);

        // Act
        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();

        // Assert
        assertNotNull(authenticationProvider);
    }

    @Test
    public void testPasswordEncoder() {
        // Arrange
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);

        // Act
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();

        // Assert
        assertNotNull(passwordEncoder);
    }
}
