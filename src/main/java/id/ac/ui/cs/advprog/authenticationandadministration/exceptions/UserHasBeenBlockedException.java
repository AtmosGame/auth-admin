package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

public class UserHasBeenBlockedException extends RuntimeException {
    public UserHasBeenBlockedException(String username){
        super("User with username " + username + " has been blocked");
    }
}
