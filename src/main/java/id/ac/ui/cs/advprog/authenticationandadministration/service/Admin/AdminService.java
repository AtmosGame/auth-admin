package id.ac.ui.cs.advprog.authenticationandadministration.service.Admin;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.ViewProfileResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    ViewProfileResponse getProfileByUsername(String username);
    void userValidationNonAdmin(User user);
    User getUserByUsername(String username);
}
