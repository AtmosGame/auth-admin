package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid token");
    }
}
