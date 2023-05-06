package id.ac.ui.cs.advprog.authenticationandadministration.models.auth;

public enum UserPermission {
    USER_READ("user:read");
    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
