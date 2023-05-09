package id.ac.ui.cs.advprog.authenticationandadministration.viewProfileByUsername;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    @Mock
    private ProfileServiceImpl profileService;

    @Mock
    private UserRepository repository;

    @Mock
    private UserService userService;

    User userValid;
    User userIsAdministrator;
    User userInactive;

    @BeforeEach
    void setUp(){
        userValid = User.builder()
                .id(1)
                .username("userValid")
                .password("passwordTestUserValid")
                .role(UserRole.USER)
                .profilePicture("link to profile picture")
                .bio("Bio test user valid")
                .applications(null)
                .active(true)
                .build();

        userIsAdministrator = User.builder()
                            .id(2)
                            .username("test2")
                            .password("passwordTestUser2")
                            .role(UserRole.ADMIN)
                            .profilePicture("link to profile picture")
                            .bio("user is administrator")
                            .applications(null)
                            .active(true)
                            .build();

        userInactive = User.builder()
                    .id(3)
                    .username("test3")
                    .password("passwordTestUser3")
                    .role(UserRole.DEVELOPER)
                    .profilePicture("link to profile picture")
                    .bio("user test3")
                    .applications("aplication 1, aplication 2, aplication 3")
                    .active(false)
                    .build();
    }

    @Test
    void whenGetProfileByUsernameShouldReturnProfile(){
        ViewProfileResponse viewProfileResponse = ViewProfileResponse.builder()
                                                .username("userValid")
                                                .role(UserRole.USER.name())
                                                .profilePicture("link to profile picture")
                                                .bio("Bio test user valid")
                                                .applications(null)
                                                .build();

        when(profileService.getProfileByUsername(userValid.getUsername())).thenReturn(viewProfileResponse);

        ViewProfileResponse response = profileService.getProfileByUsername(userValid.getUsername());

        Assertions.assertEquals(viewProfileResponse, response);

        verify(profileService, atLeastOnce()).getProfileByUsername(userValid.getUsername());
    }
}
