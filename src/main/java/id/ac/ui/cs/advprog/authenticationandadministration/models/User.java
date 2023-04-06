package id.ac.ui.cs.advprog.authenticationandadministration.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique=true)
    private String username;

    private String password;
    private String role;
    private String profilePicture;
    private String bio;
    private String applications;
    private Integer totalReport;
    private Boolean active;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Report> reportList;
}
