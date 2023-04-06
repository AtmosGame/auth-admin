package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

public class UserIsAdministratorException extends RuntimeException {
    public UserIsAdministratorException(String username){
        super("User with username " + username + " is administrator");
    }
}
