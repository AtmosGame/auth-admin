package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String username){
        super("User with username " + username + " does not exist");
    }
}
