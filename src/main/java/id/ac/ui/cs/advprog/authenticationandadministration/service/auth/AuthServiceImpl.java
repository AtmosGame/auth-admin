package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UsernameAlreadyExistsException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var checkUser = userRepository.findByUsername(request.getUsername()).orElse(null);

        if(checkUser != null) {
            throw new UsernameAlreadyExistsException();
        }

        var user = User.builder()
                .active(true)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
