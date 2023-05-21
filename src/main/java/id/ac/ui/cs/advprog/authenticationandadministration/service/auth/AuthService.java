package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    RegisterResponse register(RegisterRequest request);
    LogoutResponse logout(LogoutRequest request);
}
