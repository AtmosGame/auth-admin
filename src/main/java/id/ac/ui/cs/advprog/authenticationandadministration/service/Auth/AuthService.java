package id.ac.ui.cs.advprog.authenticationandadministration.service.Auth;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User_NonDB;

import java.util.Map;

public interface AuthService {
    boolean login(String username, String password);
    void register(String username, String password, String role);
    Map<String, User_NonDB> getAllUsersUnameKey();
    Map<Integer, User_NonDB> getAllUsersUidKey();
}


