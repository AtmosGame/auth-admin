package id.ac.ui.cs.advprog.authenticationandadministration.core.report;

import id.ac.ui.cs.advprog.authenticationandadministration.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReportManager {
    private static ReportManager uniqueReportManager;
    private HashMap<String, Integer> listReportedAccount = new HashMap<String, Integer>();

    private ReportManager(List<User> listUser){
        for (User user: listUser){
            if (!user.getRole().equals("administrator") && user.getActive() && user.getReportList().size() > 0){
                listReportedAccount.put(user.getUsername(), user.getReportList().size());
            }
        }
    }

    public static synchronized ReportManager getInstance(List<User> listUser){
        if (uniqueReportManager == null){
            uniqueReportManager = new ReportManager(listUser);
        }

        return uniqueReportManager;
    }

    public Set<String> getListReportedAccount() {
        return listReportedAccount.keySet();
    }

    public Boolean rejectReport(String username){
        if (listReportedAccount.get(username) - 1 <= 0){
            listReportedAccount.remove(username);
            return false;
        }else {
            listReportedAccount.replace(username, listReportedAccount.get(username) - 1);
            return true;
        }
    }

    public void approveReport(String username){
        listReportedAccount.remove(username);
    }
}
