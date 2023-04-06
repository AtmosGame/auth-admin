package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;

import java.util.Map;

public interface UserRepository {
    void addUser(String username, String password, String role);
    User getUser(String username);
    Map<String, User> getAllUsers();
}
