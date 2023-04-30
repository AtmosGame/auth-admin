package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;

import java.util.List;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);
    List<User> getAllUsers();
    void userValidationNonAdmin(User user);
    User getUserByUsername(String username);
}


