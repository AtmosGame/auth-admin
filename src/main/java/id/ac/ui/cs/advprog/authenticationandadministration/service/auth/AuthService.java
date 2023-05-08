package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse register(RegisterRequest request);
}


