package id.ac.ui.cs.advprog.authenticationandadministration.viewProfileByUsername;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.Admin.AdminServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {

    @InjectMocks
    private AdminServiceImpl service;

    @Mock
    private UserRepository repository;

    User userValid;
    User userIsAdministrator;
    User userInactive;
    ViewProfileResponse response;

    @BeforeEach
    void setUp(){
        userValid = User.builder()
                .id(1)
                .username("test1")
                .password("passwordTestUser1")
                .role("user")
                .profilePicture("link to profile picture")
                .bio("user test1")
                .applications(null)
                .active(true)
                .build();

        response = ViewProfileResponse.builder()
                .username(userValid.getUsername())
                .role(userValid.getRole())
                .profilePicture(userValid.getProfilePicture())
                .bio(userValid.getBio())
                .applications(userValid.getApplications())
                .build();

        userIsAdministrator = User.builder()
                            .id(2)
                            .username("test2")
                            .password("passwordTestUser2")
                            .role("administrator")
                            .profilePicture("link to profile picture")
                            .bio("user is administrator")
                            .applications(null)
                            .active(true)
                            .build();

        userInactive = User.builder()
                    .id(3)
                    .username("test3")
                    .password("passwordTestUser3")
                    .role("developer")
                    .profilePicture("link to profile picture")
                    .bio("user test3")
                    .applications("aplication 1, aplication 2, aplication 3")
                    .active(false)
                    .build();
    }

    @Test
    void whenGetProfileByUsernameShouldReturnProfile(){
        when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(userValid));

        ViewProfileResponse result = service.getProfileByUsername("test1");
        verify(repository, atLeastOnce()).findByUsername(any(String.class));
        Assertions.assertEquals(response, result);
    }

    @Test
    void whenGetProfileByUsernameAndNotFoundShouldThrowException(){
        when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
            service.getProfileByUsername("random");
        });
    }

    @Test
    void whenGetProfileByUsernameAndUserIsAdministratorShouldThrowException(){
        when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(userIsAdministrator));

        Assertions.assertThrows(UserIsAdministratorException.class, () -> {
            service.getProfileByUsername("test2");
        });
    }

    @Test
    void whenGetProfileByUsernameAndUserInactiveShouldThrowException(){
        when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(userInactive));

        Assertions.assertThrows(UserHasBeenBlockedException.class, () -> {
            service.getProfileByUsername("test3");
        });
    }
}
