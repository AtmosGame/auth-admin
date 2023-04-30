package id.ac.ui.cs.advprog.authenticationandadministration.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {
    private String username;
    private String role;
}
