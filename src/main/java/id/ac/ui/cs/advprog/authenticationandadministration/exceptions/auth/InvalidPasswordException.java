package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String username) {
        super("Invalid password for user with username " + username);
    }
}
