package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("User with the same username already exist");
    }
}
