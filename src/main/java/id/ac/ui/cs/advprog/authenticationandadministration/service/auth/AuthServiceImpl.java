package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.*;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.*;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.TokenRepository;
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
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        var checkUser = userRepository.findByUsername(request.getUsername()).orElse(null);

        if(checkUser != null) {
            throw new UsernameAlreadyExistsException();
        }
        if(request.getUsername().equals("")) {
            throw new UsernameIsEmptyException();
        }
        if(request.getPassword().equals("")) {
            throw new PasswordIsEmptyException();
        }
        if(request.getPassword().length() < 8) {
            throw new PasswordMinimalException();
        }

        var user = User.builder()
                .active(true)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        tokenRepository.addToken(jwtToken, user.getId());

        return RegisterResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().toString())
                .build();
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
        tokenRepository.addToken(jwtToken, user.getId());

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public LogoutResponse logout(LogoutRequest request) {
        var user = userRepository.getUser(request.getUsername());
        var userTokens = tokenRepository.getAllByUserId(user.getId());

        if (userTokens.isEmpty())
            return LogoutResponse.builder().message("This user is not currently logged in").build();

        revokeAllUserTokens(user);
        return LogoutResponse.builder().message("Logout successful").build();
    }

    public void revokeAllUserTokens(User user) {
        tokenRepository.deleteAllByUserId(user.getId());
    }
}
