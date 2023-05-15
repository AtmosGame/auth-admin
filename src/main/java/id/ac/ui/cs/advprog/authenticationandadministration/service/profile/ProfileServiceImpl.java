package id.ac.ui.cs.advprog.authenticationandadministration.service.profile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileRequest;
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
    public User updateProfile(String username, EditProfileRequest request){
        User user = userService.getUserNonAdminByUsername(username);
        if (!request.getProfilePicture().isEmpty()) {
            try {
                // Upload the image to Cloudinary and get the public URL
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dipygqcrv", // insert here you cloud name
                "api_key", "359898466846676", // insert here your api code
                "api_secret", "SNw4bm4azWO0gljlvjKQ5S2g5YQ"));
                Map uploadResult = cloudinary.uploader().upload(request.getProfilePicture(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");

                // Set the profile picture URL in the user object
                user.setProfilePicture(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        user.setProfilePicture(request.getProfilePicture());

        user.setBio(request.getBio());;
        return userRepository.save(user);
    }
}
