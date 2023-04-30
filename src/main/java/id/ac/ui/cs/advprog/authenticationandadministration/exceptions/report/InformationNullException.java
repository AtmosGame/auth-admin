package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report;

public class InformationNullException extends RuntimeException{
    public InformationNullException(){
        super("Information cannot be empty");
    }
}
