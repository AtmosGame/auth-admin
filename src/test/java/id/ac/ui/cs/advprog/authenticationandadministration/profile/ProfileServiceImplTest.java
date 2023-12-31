package id.ac.ui.cs.advprog.authenticationandadministration.profile;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    private ProfileService profileService;
    private UserRepository userRepository;

    private final String username = "testUser";

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        TokenRepository tokenRepository = mock(TokenRepository.class);
        UserService userService = new UserServiceImpl(userRepository, tokenRepository);
        profileService = new ProfileServiceImpl(userRepository, userService);
    }

    @Test
    void whenGetProfileByUsernameShouldReturnProfile(){
        User user = User.builder()
                    .id(1)
                    .username(username)
                    .password("passwordTestUser")
                    .role(UserRole.USER)
                    .profilePicture("test.jpg")
                    .bio("test bio")
                    .applications(null)
                    .active(true)
                    .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        ViewProfileResponse responseUser = profileService.getProfileByUsername(user.getUsername());

        Assertions.assertEquals(user.getUsername(), responseUser.getUsername());
        Assertions.assertEquals(user.getRole().name(), responseUser.getRole());
        Assertions.assertEquals(user.getProfilePicture(), responseUser.getProfilePicture());
        Assertions.assertEquals(user.getBio(), responseUser.getBio());
        Assertions.assertEquals(user.getApplications(), responseUser.getApplications());

        User developer = User.builder()
                .id(2)
                .username("testdeveloper")
                .password("passwordTestDeveloper")
                .role(UserRole.DEVELOPER)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications("A, B, C, D")
                .active(true)
                .build();

        when(userRepository.findByUsername(developer.getUsername())).thenReturn(Optional.of(developer));

        ViewProfileResponse responseDeveloper = profileService.getProfileByUsername(developer.getUsername());

        Assertions.assertEquals(developer.getUsername(), responseDeveloper.getUsername());
        Assertions.assertEquals(developer.getRole().name(), responseDeveloper.getRole());
        Assertions.assertEquals(developer.getProfilePicture(), responseDeveloper.getProfilePicture());
        Assertions.assertEquals(developer.getBio(), responseDeveloper.getBio());
        Assertions.assertEquals(developer.getApplications(), responseDeveloper.getApplications());
    }

    @Test
    void whenGetProfileByUsernameWithUserIsNotFoundShouldThrowException(){
        Assertions.assertThrows(UserDoesNotExistException.class, () -> {
            profileService.getProfileByUsername(username);
        });
    }

    @Test
    void whenGetProfileByUsernameWithUserIsAdministratorShouldThrowException(){
        User user = User.builder()
                    .id(1)
                    .username(username)
                    .password("passwordTestUser")
                    .role(UserRole.ADMIN)
                    .profilePicture("test.jpg")
                    .bio("test bio")
                    .applications(null)
                    .active(true)
                    .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserIsAdministratorException.class, () -> {
            profileService.getProfileByUsername(username);
        });
    }

    @Test
    void whenGetProfileByUsernameWithUserHaveBeenBlockedShouldThrowException(){
        User user = User.builder()
                    .id(1)
                    .username(username)
                    .password("passwordTestUser")
                    .role(UserRole.USER)
                    .profilePicture("test.jpg")
                    .bio("test bio")
                    .applications(null)
                    .active(false)
                    .build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserHasBeenBlockedException.class, () -> {
            profileService.getProfileByUsername(username);
        });
    }

    @Test
    void testUpdateProfileWithoutProfilePicture(){
        User user = User.builder()
                .id(1)
                .username("testUser")
                .password("passwordTestUser")
                .role(UserRole.USER)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications(null)
                .active(true)
                .build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        EditProfileRequest request = EditProfileRequest.builder()
                .profilePicture("")
                .bio("new bio")
                .build();

        profileService.updateProfile(user.getUsername(), request);

        verify(userRepository).save(user);
    }

    @Test
    void testUpdateProfileWithProfilePicture(){
        User user = User.builder()
                .id(1)
                .username("testUser")
                .password("passwordTestUser")
                .role(UserRole.USER)
                .profilePicture("test.jpg")
                .bio("test bio")
                .applications(null)
                .active(true)
                .build();

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        EditProfileRequest request = EditProfileRequest.builder()
                .profilePicture("link.jpg")
                .bio("new bio")
                .build();

        profileService.updateProfile(user.getUsername(), request);
        verify(userRepository).save(user);
    }
}
