package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

public class UserDoesNotHaveReportException extends RuntimeException {
    public UserDoesNotHaveReportException(String username){
        super("User with username " + username + " does not have report");
    }
}
