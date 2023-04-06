package id.ac.ui.cs.advprog.authenticationandadministration.core;

public class User {
    private static int total = 0;
    private final int id;
    private final String username;
    private final String password;
    private final String role;
    public User(String username, String password, String role) {
        this.id = generateId();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int generateId() {
        return total++;
    }

    public int getId() { return id; }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() { return role; }
}
