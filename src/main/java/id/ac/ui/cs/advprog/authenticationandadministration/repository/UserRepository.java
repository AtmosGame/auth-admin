package id.ac.ui.cs.advprog.authenticationandadministration.repository;

import id.ac.ui.cs.advprog.authenticationandadministration.core.User;

public interface UserRepository {
    void addUser(String username, String password);
    User getUser(String username);
}
