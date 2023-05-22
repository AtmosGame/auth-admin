package id.ac.ui.cs.advprog.authenticationandadministration.dto.profile;

import lombok.*;

@Data
@Generated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewProfileResponse {
    private String username;
    private String role;
    private String profilePicture;
    private String bio;
    private String applications;
}
