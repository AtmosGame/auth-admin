package id.ac.ui.cs.advprog.authenticationandadministration.models;

import java.util.ArrayList;
import java.util.List;

public class User_NonDB {
    private static int total = 0;
    private int id, totalReport = 0;
    private String username, password, role, photoProfile = "", bio = "";
    private List<Application> applications = new ArrayList<Application>();

    public User_NonDB(String username, String password, String role) {
        this.id = generateId();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int generateId() {
        return total++;
    }

    // GETTER
    public int getId() { return id; }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public String getRole() { return role; }
    public String getPhotoProfile() { return photoProfile; }
    public String getBio() { return bio; }
    public int getTotalReport() { return totalReport; }
    public List<Application> getApplications() { return applications; }

    // SETTER
    public void setPhotoProfile(String photoProfile) { this.photoProfile = photoProfile; }
    void setBio(String bio) { this.bio = bio; }
    public void setReport(int totalReport) { this.totalReport = totalReport; }

    // OTHER
    public void addApplication(Application application) { applications.add(application); }
}
