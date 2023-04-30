package id.ac.ui.cs.advprog.authenticationandadministration.dto.user;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CurrentUserResponse {
    private Integer id;
    private String username;
    private UserRole role;
    private String profilePicture;
    private Boolean active;
}
