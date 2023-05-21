package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report;

public class UserAndReportNotMatchedException extends RuntimeException {
    public UserAndReportNotMatchedException(String username, Integer reportId) {
        super("Report with id " + reportId + " is invalid for user with username " + username);
    }
}
