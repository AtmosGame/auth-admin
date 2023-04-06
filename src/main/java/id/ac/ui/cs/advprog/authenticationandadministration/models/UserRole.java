package id.ac.ui.cs.advprog.authenticationandadministration.models;

import java.util.ArrayList;
import java.util.List;

public enum UserRole {
    admin, developer, user;

    public static List<String> getNames(){
        var ret = new ArrayList<String>();
        for (UserRole userRole : UserRole.values()) {
            ret.add(userRole.name());
        }
        return ret;
    }
}
