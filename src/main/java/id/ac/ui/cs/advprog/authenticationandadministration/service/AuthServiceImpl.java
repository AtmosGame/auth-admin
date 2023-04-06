package id.ac.ui.cs.advprog.authenticationandadministration.service;

import id.ac.ui.cs.advprog.authenticationandadministration.core.encryptor.Encryptor;
import id.ac.ui.cs.advprog.authenticationandadministration.core.User;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {
        User supposedUser = userRepository.getUser(username);
        return supposedUser != null &&
                toCipher(password).equals(supposedUser.getPassword());
    }

    @Override
    public void register(String username, String password, String role) {
        userRepository.addUser(username, toCipher(password), role);
    }

    private String toCipher(String password) {
        Encryptor encryptor = new Encryptor();
        String encryptPassword = encryptor.encrypt(password);
        return encryptPassword;
    }

    @Override
    public Map<String, User> getAllUsersUnameKey() {
        return userRepository.getAllUsers();
    }

    @Override
    public Map<Integer, User> getAllUsersUidKey() {
        Map<String, User> users = userRepository.getAllUsers();
        Map<Integer, User> usersUidKey = new HashMap<>();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            usersUidKey.put(entry.getValue().getId(), entry.getValue());
        }
        return usersUidKey;
    }
}
