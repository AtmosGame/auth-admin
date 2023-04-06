package id.ac.ui.cs.advprog.authenticationandadministration.models;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    admin, developer, user;

    public static List<String> getNames(){
        var ret = new ArrayList<String>();
        for (Role role: Role.values()) {
            ret.add(role.name());
        }
        return ret;
    }
}
