package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report;

public class UserAndReportNotMatchedException extends RuntimeException {
    public UserAndReportNotMatchedException(String username, Integer report_id){
        super("Report with id " + report_id + " is invalid for user with username " + username);
    }
}
