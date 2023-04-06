package id.ac.ui.cs.advprog.authenticationandadministration.service;

import id.ac.ui.cs.advprog.authenticationandadministration.core.User;

import java.util.Map;

public interface AuthService {
    boolean login(String username, String password);
    void register(String username, String password, String role);
    Map<String, User> getAllUsers();
}


