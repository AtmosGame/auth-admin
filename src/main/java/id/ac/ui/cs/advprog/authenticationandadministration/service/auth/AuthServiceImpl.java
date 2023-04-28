package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.core.auth.Util;
import id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor.Encryptor;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationRequest;
import id.ac.ui.cs.advprog.authenticationandadministration.dto.auth.AuthenticationResponse;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.InvalidPasswordException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;



//    AuthenticationManager authenticationManager = AuthenticationManager.getInstance();

//    @Override
//    public boolean login(String username, String password) {
//        User supposedUser = userRepository.getUser(username);
//
//        if(!userRepository.findByUsername(username).isEmpty()) {
//            throw new UserDoesNotExistException(username);
//        }
//
//        if(!toCipher(password).equals(supposedUser.getPassword())) { // password
//            throw new InvalidPasswordException();
//        }
//
//        if(!userRepository.findByUsername(username).get().getActive()) {
//            throw new UserHasBeenBlockedException(username);
//        }
//        else {
//            String token = Util.generateToken();
//            authenticationManager.registerNewToken(token, username);
//            return true;
//        }
//
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        if(!user.getActive()) {
            throw new UserHasBeenBlockedException(user.getUsername());
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public String register(String username, String password, String role) {
        userRepository.addUser(username, toCipher(password), role);
        return "Register Success";
    }

    private String toCipher(String password) {
        Encryptor encryptor = new Encryptor();
        String encryptPassword = encryptor.encrypt(password);
        return encryptPassword;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
