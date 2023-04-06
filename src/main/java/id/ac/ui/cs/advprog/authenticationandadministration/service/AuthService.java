package id.ac.ui.cs.advprog.authenticationandadministration.service;

public interface AuthService {
    boolean login(String username, String password);
    void register(String username, String password, String role);
}


