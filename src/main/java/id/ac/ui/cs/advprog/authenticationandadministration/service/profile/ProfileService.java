package id.ac.ui.cs.advprog.authenticationandadministration.service.profile;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    ViewProfileResponse getProfileByUsername(String username);
    User updateProfile(String username, String bio, String profilePicture);
}
