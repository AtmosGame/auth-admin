package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import java.util.List;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String register(String username, String password, String role);
    List<User> getAllUsers();
}


