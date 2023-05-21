package id.ac.ui.cs.advprog.authenticationandadministration.models.auth;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.ac.ui.cs.advprog.authenticationandadministration.models.Report;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String profilePicture;

    private String bio;

    @Nullable
    private String applications;

    private Boolean active;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Report> reportList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role.equals(UserRole.ADMIN)) {
            return UserRole.ADMIN.getGrantedAuthority();
        } else if (role.equals(UserRole.DEVELOPER)) {
            return UserRole.DEVELOPER.getGrantedAuthority();
        } else {
            return UserRole.USER.getGrantedAuthority();
        }
    }

    // TODO: @Mario coba pastiin lagi kira-kira ini bisa dihapus ga ya? Karena jadi kurang enak diliatnya (Ini karena lu implements UserDetails)
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
