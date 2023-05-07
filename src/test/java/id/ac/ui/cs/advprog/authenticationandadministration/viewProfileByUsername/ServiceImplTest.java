package id.ac.ui.cs.advprog.authenticationandadministration.viewProfileByUsername;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
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
public class ServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private UserRepository repository;

    @Mock
    private AuthService authService;

    @Mock
    User userValid;

    User userIsAdministrator;
    User userInactive;
    ViewProfileResponse response;

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

//        userIsAdministrator = User.builder()
//                            .id(2)
//                            .username("test2")
//                            .password("passwordTestUser2")
//                            .role(UserRole.ADMIN)
//                            .profilePicture("link to profile picture")
//                            .bio("user is administrator")
//                            .applications(null)
//                            .active(true)
//                            .build();
//
//        userInactive = User.builder()
//                    .id(3)
//                    .username("test3")
//                    .password("passwordTestUser3")
//                    .role(UserRole.DEVELOPER)
//                    .profilePicture("link to profile picture")
//                    .bio("user test3")
//                    .applications("aplication 1, aplication 2, aplication 3")
//                    .active(false)
//                    .build();
    }

    @Test
    void whenGetProfileByUsernameShouldReturnProfile(){
        ViewProfileResponse viewProfileResponse = ViewProfileResponse.builder()
                                                .username("test")
                                                .role(UserRole.USER.name())
                                                .profilePicture("link")
                                                .bio("user test")
                                                .applications(null)
                                                .build();

        when(repository.findByUsername(userValid.getUsername())).thenReturn(Optional.of(userValid));
        when(profileService.getProfileByUsername(userValid.getUsername())).thenReturn(viewProfileResponse);

        ViewProfileResponse result = profileService.getProfileByUsername("test1");
        verify(repository, atLeastOnce()).findByUsername(userValid.getUsername());
        Assertions.assertEquals(response, result);
    }

//    @Test
//    void whenGetProfileByUsernameAndNotFoundShouldThrowException(){
//        when(repository.findByUsername(any(String.class))).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
//            service.getProfileByUsername("random");
//        });
//    }
//
//    @Test
//    void whenGetProfileByUsernameAndUserIsAdministratorShouldThrowException(){
//        when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(userIsAdministrator));
//
//        Assertions.assertThrows(UserIsAdministratorException.class, () -> {
//            service.getProfileByUsername("test2");
//        });
//    }
//
//    @Test
//    void whenGetProfileByUsernameAndUserInactiveShouldThrowException(){
//        when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(userInactive));
//
//        Assertions.assertThrows(UserHasBeenBlockedException.class, () -> {
//            service.getProfileByUsername("test3");
//        });
//    }
}
