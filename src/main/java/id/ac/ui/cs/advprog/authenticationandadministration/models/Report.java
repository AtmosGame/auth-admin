package id.ac.ui.cs.advprog.authenticationandadministration.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Generated
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_report")
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String information;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateReport;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "_user_id", nullable = false)
    private User user;
}
