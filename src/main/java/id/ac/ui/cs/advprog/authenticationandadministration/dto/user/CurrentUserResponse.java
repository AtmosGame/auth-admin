package id.ac.ui.cs.advprog.authenticationandadministration.dto.user;

import id.ac.ui.cs.advprog.authenticationandadministration.models.auth.UserRole;
import lombok.*;

@Data
@Generated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserResponse {
    private Integer id;
    private String username;
    private UserRole role;
    private String profilePicture;
    private String bio;
    private String applications;
    private Boolean active;
    private Object reportList;
}
