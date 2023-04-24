package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.core.auth.encryptor.Encryptor;
import id.ac.ui.cs.advprog.authenticationandadministration.models.User_NonDB;
import id.ac.ui.cs.advprog.authenticationandadministration.repository.UserRepositoryNonDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepositoryNonDB userRepositoryNonDB;

    @Override
    public boolean login(String username, String password) {
        User_NonDB supposedUser = userRepositoryNonDB.getUser(username);
        return supposedUser != null &&
                toCipher(password).equals(supposedUser.getPassword());
    }

    @Override
    public void register(String username, String password, String role) {
        userRepositoryNonDB.addUser(username, toCipher(password), role);
    }

    private String toCipher(String password) {
        Encryptor encryptor = new Encryptor();
        String encryptPassword = encryptor.encrypt(password);
        return encryptPassword;
    }

    @Override
    public Map<String, User_NonDB> getAllUsersUnameKey() {
        return userRepositoryNonDB.getAllUsers();
    }

    @Override
    public Map<Integer, User_NonDB> getAllUsersUidKey() {
        Map<String, User_NonDB> users = userRepositoryNonDB.getAllUsers();
        Map<Integer, User_NonDB> usersUidKey = new HashMap<>();
        for (Map.Entry<String, User_NonDB> entry : users.entrySet()) {
            usersUidKey.put(entry.getValue().getId(), entry.getValue());
        }
        return usersUidKey;
    }
}