package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class PasswordIsEmptyException extends RuntimeException{
    public PasswordIsEmptyException() {
        super("Password is empty");
    }
}
