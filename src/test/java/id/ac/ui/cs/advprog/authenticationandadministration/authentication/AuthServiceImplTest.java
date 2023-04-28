package id.ac.ui.cs.advprog.authenticationandadministration.authentication;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.InvalidPasswordException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Spy
    UserRepository userRepository;

    User userValid;

    @InjectMocks
    AuthServiceImpl authService = new AuthServiceImpl();

    @Test
    void testLoginWithUnexpectedInputShouldThrowsError(){
        userValid = User.builder()
                .id(1)
                .username("test1")
                .password("passwordTestUser1")
                .role(UserRole.valueOf("user"))
                .profilePicture("link to profile picture")
                .bio("user test1")
                .applications(null)
                .active(false)
                .build();
        AuthenticationRequest request = new AuthenticationRequest("email", "password");
        AuthenticationRequest request2 = new AuthenticationRequest("liame", "password");
        AuthenticationRequest request3 = new AuthenticationRequest("email", "password");
        userRepository.addUser("email","password","user");
        assertThrows(InvalidPasswordException.class, () -> authService.authenticate(request));
        assertThrows(UserDoesNotExistException.class, () -> authService.authenticate(request2));
        assertThrows(UserHasBeenBlockedException.class, () -> authService.authenticate(request3));
    }
}

