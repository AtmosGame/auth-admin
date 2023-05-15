package id.ac.ui.cs.advprog.authenticationandadministration.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

@Data
@Generated
@Builder
@AllArgsConstructor
public class RegisterResponse {
    private String token;
    private String username;
    private String role;
}
