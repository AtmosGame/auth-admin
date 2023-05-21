package id.ac.ui.cs.advprog.authenticationandadministration.service.profile;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.EditProfileRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.profile.ViewProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    ViewProfileResponse getProfileByUsername(String username);
    void updateProfile(String username, EditProfileRequest request);
}
