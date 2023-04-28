package id.ac.ui.cs.advprog.authenticationandadministration.models;

public enum UserPermission {

    DUMMY("medicine:delete"),;
    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
