package id.ac.ui.cs.advprog.authenticationandadministration.core.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;

import java.util.Comparator;

public class ReportComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        if (user1.getReportList().size() > user2.getReportList().size()) {
            return -1;
        }else if (user1.getReportList().size() < user2.getReportList().size()){
            return 1;
        }else {
            return user1.getUsername().compareTo(user2.getUsername());
        }
    }
}
