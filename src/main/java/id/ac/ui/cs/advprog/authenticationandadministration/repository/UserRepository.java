package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User_NonDB;

import java.util.Map;

public interface UserRepository {
    void addUser(String username, String password, String role);
    User_NonDB getUser(String username);
    Map<String, User_NonDB> getAllUsers();
}
