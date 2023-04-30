package id.ac.ui.cs.advprog.authenticationandadministration.core.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;

import java.util.*;

public class ReportManager {
    private static ReportManager uniqueReportManager;
    private Map<User, String> listReportedAccount = new TreeMap<User, String>(new ReportComparator());

    private ReportManager(List<User> listUser){
        for (User user: listUser){
            if (!user.getRole().name().equals("ADMIN") && user.getActive() && user.getReportList().size() > 0){
                listReportedAccount.put(user, user.getUsername());
            }
        }
    }

    public static synchronized ReportManager getInstance(List<User> listUser){
        if (uniqueReportManager == null){
            uniqueReportManager = new ReportManager(listUser);
        }

        return uniqueReportManager;
    }

    public Collection<String> getListReportedAccount() {
        return listReportedAccount.values();
    }

    public void approveReport(User user){
        listReportedAccount.remove(user);
    }

    public void rejectReport(User oldUser, User newUser){
        listReportedAccount.remove(oldUser);
        listReportedAccount.put(newUser, newUser.getUsername());
    }
}
