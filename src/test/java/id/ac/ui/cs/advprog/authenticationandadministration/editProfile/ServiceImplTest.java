package id.ac.ui.cs.advprog.authenticationandadministration.editProfile;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {
    @Spy
    @InjectMocks
    private ProfileServiceImpl service;

    @Mock
    private UserRepository repository;

    User userValid;
    User userUpdated;
    EditProfileResponse response;

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

        userUpdated = User.builder()
                .id(2)
                .username("test1")
                .password("passwordTestUser1")
                .role("user")
                .profilePicture("link to ahli gembos")
                .bio("gembos gembos")
                .applications(null)
                .active(true)
                .build();

        response = EditProfileResponse.builder()
                .username(userUpdated.getUsername())
                .profilePicture(userUpdated.getProfilePicture())
                .bio(userUpdated.getBio())
                .build();
    }

    @Test
    void testUpdate() {
        doReturn(userUpdated).when(service).getUserByUsername(userValid.getUsername());
        User result = service.updateProfile(userValid.getUsername(), response.getBio(), response.getProfilePicture());
        Assertions.assertThat(result).isEqualTo(null);
    }
}
