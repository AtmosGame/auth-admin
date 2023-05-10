package id.ac.ui.cs.advprog.authenticationandadministration.models.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    private Date expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void setExpirationDate() {
        Date now = new Date();
        long thirtyMinutesInMillis = 30 * 60 * 1000; // 30 minutes in milliseconds
        this.expired = new Date(now.getTime() + thirtyMinutesInMillis);
    }
}
