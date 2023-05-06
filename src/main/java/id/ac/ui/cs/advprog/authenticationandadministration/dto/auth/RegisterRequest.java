package id.ac.ui.cs.advprog.authenticationandadministration.dto.auth;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private UserRole role;
}
