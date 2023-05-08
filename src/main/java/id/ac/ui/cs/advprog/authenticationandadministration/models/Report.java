package id.ac.ui.cs.advprog.authenticationandadministration.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_report")
public class Report {
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

//    public String toString(){
//        return String.format("'id':%d", id);
//    }
}
