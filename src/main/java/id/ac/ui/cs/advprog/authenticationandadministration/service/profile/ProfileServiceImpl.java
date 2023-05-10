package id.ac.ui.cs.advprog.authenticationandadministration.service.profile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public ViewProfileResponse getProfileByUsername(String username) {
        User user = userService.getUserNonAdminByUsername(username);

        return ViewProfileResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .profilePicture(user.getProfilePicture())
                .bio(user.getBio())
                .applications(user.getApplications())
                .build();
    }

    @Override
    public User updateProfile(String username, String bio, String profilePicture){
        User user = userService.getUserNonAdminByUsername(username);
        // Check if the profile picture is not empty
        if (!profilePicture.isEmpty()) {
            try {
                // Upload the image to Cloudinary and get the public URL
                Cloudinary cloudinary = new Cloudinary();
                Map uploadResult = cloudinary.uploader().upload(profilePicture, ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");

                // Set the profile picture URL in the user object
                user.setProfilePicture(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setBio(bio);
        return userRepository.save(user);
    }
}
