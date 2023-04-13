package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

public class ReportDoesNotExistException extends RuntimeException {
    public ReportDoesNotExistException(Integer id){
        super("Report with id " + id + " does not exist");
    }
}
