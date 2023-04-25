package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor.Encryptor;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        User supposedUser = userRepository.getUser(username);
        return supposedUser != null &&
                toCipher(password).equals(supposedUser.getPassword());
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
