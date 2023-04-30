package id.ac.ui.cs.advprog.authenticationandadministration.service.profile;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import id.ac.ui.cs.advprog.authenticationandadministration.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final AuthService authService;

    @Override
    public ViewProfileResponse getProfileByUsername(String username) {
        User user = authService.getUserByUsername(username);
        authService.userValidationNonAdmin(user);

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
        User user = authService.getUserByUsername(username);
        authService.userValidationNonAdmin(user);

        user.setBio(bio);
        user.setProfilePicture(profilePicture);
        return userRepository.save(user);
    }
}
