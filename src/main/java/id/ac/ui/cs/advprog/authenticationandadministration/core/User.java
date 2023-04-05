package id.ac.ui.cs.advprog.authenticationandadministration.core;

public class User {
    private final String username;
    private final String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
