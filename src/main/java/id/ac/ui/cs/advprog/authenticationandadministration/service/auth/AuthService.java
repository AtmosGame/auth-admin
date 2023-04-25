package id.ac.ui.cs.advprog.authenticationandadministration.service.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;
import java.util.List;

public interface AuthService {
    boolean login(String username, String password);
    String register(String username, String password, String role);
    List<User> getAllUsers();
}


