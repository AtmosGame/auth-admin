package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.*;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    RegisterResponse register(RegisterRequest request);
    LogoutResponse logout(LogoutRequest request);
}


