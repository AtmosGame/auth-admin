package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class PasswordMinimalException extends RuntimeException {
    public PasswordMinimalException() {
        super("Password minimal 8 characters");
    }
}
