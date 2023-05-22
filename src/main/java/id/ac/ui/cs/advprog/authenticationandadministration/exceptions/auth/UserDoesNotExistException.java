package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String username) {
        super("User with username " + username + " does not exist");
    }
}
