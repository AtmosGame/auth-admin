package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException() {
        super("Role is invalid");
    }
}
