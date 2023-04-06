package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public void addUser(String username, String password, String role) {
        if (users.get(username) != null) return;
        users.put(username, new User(username, password, role));
        System.out.println("Registering " + username + " with role " + role + " and password " + password);
    }

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public Map<String, User> getAllUsers() {
        return users;
    }
}
