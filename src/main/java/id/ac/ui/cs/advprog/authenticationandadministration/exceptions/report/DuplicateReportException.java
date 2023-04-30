package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report;

public class DuplicateReportException extends RuntimeException{
    public DuplicateReportException() {
        super("Cannot report the same user before the admin approves the previous report");
    }
}
