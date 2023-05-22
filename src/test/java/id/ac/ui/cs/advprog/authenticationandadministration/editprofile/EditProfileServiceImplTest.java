package id.ac.ui.cs.advprog.authenticationandadministration.editprofile;

import com.cloudinary.Cloudinary;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.profile.ProfileServiceImpl;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditProfileServiceImplTest {
    @Spy
    @InjectMocks
    private ProfileServiceImpl service;
    @Spy
    @InjectMocks
    private UserServiceImpl userService;

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
                .role(UserRole.USER)
                .profilePicture("link to profile picture")
                .bio("user test1")
                .applications(null)
                .active(true)
                .build();

        userUpdated = User.builder()
                .id(2)
                .username("test1")
                .password("passwordTestUser1")
                .role(UserRole.USER)
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

//    @Test
//    void testUpdateProfileWithProfilePicture() throws IOException {
//        // Mock data
//        String username = "testuser";
//        User user = new User(username, "password", Role.USER);
//        EditProfileRequest request = new EditProfileRequest();
//        request.setProfilePicture(mock(MultipartFile.class));
//        request.setBio("Updated bio");
//
//        Cloudinary cloudinaryMock = mock(Cloudinary.class);
//        Map uploadResultMock = mock(Map.class);
//        String imageUrl = "uploaded_image_url";
//
//        when(userService.getUserNonAdminByUsername(username)).thenReturn(user);
//        when(repository.save(user)).thenReturn(user);
//        when(cloudinaryMock.uploader().upload(any(MultipartFile.class), (Map) any(Map.class)))
//                .thenReturn(uploadResultMock);
//        when(uploadResultMock.get("url")).thenReturn(imageUrl);
//
//        // Invoke the method being tested
//        User updatedUser = service.updateProfile(username, request);
//
//        // Verify the result
//        assertEquals(request.getBio(), updatedUser.getBio());
//        assertEquals(imageUrl, updatedUser.getProfilePicture());
//
//        // Verify that the userService.getUserNonAdminByUsername method is called once
//        verify(userService, times(1)).getUserNonAdminByUsername(username);
//        // Verify that the userRepository.save method is called once
//        verify(repository, times(1)).save(user);
//        // Verify that the Cloudinary API methods are called appropriately
//        verify(cloudinaryMock, times(1)).uploader();
//
//        verify(cloudinaryMock.uploader(), times(1)).upload(request.getProfilePicture(), Collections.emptyMap());
//    }
}
