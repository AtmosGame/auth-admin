package id.ac.ui.cs.advprog.authenticationandadministration.repository;

public interface UserRepository {
    void addUser(String username, String password);
    User getUser(String username);
}
